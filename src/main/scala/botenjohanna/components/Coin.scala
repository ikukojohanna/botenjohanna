package botenjohanna.components

import scala.util.Random

import org.scalajs.dom.raw.CanvasRenderingContext2D
import org.scalajs.dom.raw.HTMLImageElement

class Coin(var position: Vector, image: HTMLImageElement)(implicit ctx: CanvasRenderingContext2D) extends DynamicObject {
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
}

object Coin {

  def spawn(image: HTMLImageElement, blocks: Seq[RoundedRect])(implicit ctx: CanvasRenderingContext2D) = {
    val block = blocks(Random.nextInt(blocks.size))
    val xMin = block.position.x + block.innerRadius
    val xMax = block.position.x + block.width - block.innerRadius
    val position = Vector(xMin + Random.nextDouble * (xMax - xMin), 0)
    new Coin(position, image)
  }

}