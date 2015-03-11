package botenjohanna.components

import scala.util.Random
import org.scalajs.dom.raw.HTMLImageElement
import org.scalajs.dom.raw.CanvasRenderingContext2D

class Cloud(image: HTMLImageElement, position: Vector, vX: Double, width: Double, height: Double)(implicit ctx: CanvasRenderingContext2D) {
  var x = position.x
  var y = position.y

  def draw = {
    ctx.drawImage(image, x, y, width, height)
  }

  def animate() = {
    x += vX;

    if (x > ctx.canvas.width) x = -width;
  }
}

object Cloud {
  val Width = 512
  val Height = 256

  def random(image: HTMLImageElement)(implicit ctx: CanvasRenderingContext2D) = {
    val randomScale = 0.5 + Random.nextDouble() * 1.5
    val randomScaleX = 0.75 + Random.nextDouble() * 0.5
    val randomScaleY = 0.75 + Random.nextDouble() * 0.5

    val w = Width * randomScale * randomScaleX
    val h = Height * randomScale * randomScaleY

    val x = -w + Random.nextDouble * (ctx.canvas.width + w)
    val y = -h + Random.nextDouble * (ctx.canvas.height + h)

    val vX = 2 + Random.nextDouble * 2

    new Cloud(image, Vector(x, y), vX, w, h)
  }
}