package botenjohanna.components

import org.scalajs.dom.raw.CanvasRenderingContext2D

case class RoundedRect(position: Vector, width: Double, height: Double, innerRadius: Double, fill: String)(implicit ctx: CanvasRenderingContext2D) {

  def draw() = {
    val x = position.x
    val y = position.y
    val r = innerRadius

    ctx.beginPath()

    /* Path drawing order 
       * 1  8   7
       *    --
       * 2 |  | 6
       *    --
       * 3  4   5
       */
    ctx.moveTo(x + r, y)
    ctx.quadraticCurveTo(x, y, x, y + r) //1
    ctx.lineTo(x, y + height - r) //2
    ctx.quadraticCurveTo(x, y + height, x + r, y + height) //3
    ctx.lineTo(x + width - r, y + height) //4
    ctx.quadraticCurveTo(x + width, y + height, x + width, y + height - r) //5
    ctx.lineTo(x + width, y + r)
    ctx.quadraticCurveTo(x + width, y, x + width - r, y)
    ctx.lineTo(x + r, y)

    ctx.fillStyle = fill
    ctx.fill()
  }
}

object RoundedRect {
  def apply(x: Int, width: Double, height: Double, fill: String)(implicit ctx: CanvasRenderingContext2D): RoundedRect =
    RoundedRect(Vector(x, ctx.canvas.height - height), width, height, 20, fill)
}