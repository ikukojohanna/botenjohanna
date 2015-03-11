package botenjohanna.components

import scala.util.Random

import org.scalajs.dom.raw.CanvasRenderingContext2D
import org.scalajs.dom.raw.HTMLImageElement

class Coin(var position: Vector, image: HTMLImageElement)(implicit ctx: CanvasRenderingContext2D) extends GravityObject {
  val width = 34.0
  val height = 34.0

  var rotation = 0.0
  val phaseOffset = Random.nextDouble * 2 * math.Pi

  def animate() = {
    rotation += math.Pi / 30 // one revolution per second at 60 fps
  }

  def draw() = {
    val scaleFactor = math.sin(rotation + phaseOffset).toFloat;
    ctx.save()
    ctx.translate(position.x, position.y)
    ctx.scale(scaleFactor, 1)
    ctx.drawImage(image, -width / 2, -height / 2)
    ctx.restore()
  }

  override def reactToSurface() = {
    velocity = Vector.Null
  }
}