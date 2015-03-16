package botenjohanna.components

import scala.scalajs.js.Any.fromString

import org.scalajs.dom.raw.CanvasRenderingContext2D
import org.scalajs.dom.raw.{HTMLImageElement => Image}

object Character {
  val TicksPerFrame = 2
  val MaxVelocity = 5.0
  val WalkAcceleration = 0.5
  val JumpVelocity = 15.0
  val WalkFriction = 0.8
  val ScoreFont = "35pt Silom"
  val ScoreStyle = "#6f6ab3"
}

class Character(
  imageStand: Image,
  imageJump: Image,
  imagesWalk: Seq[Image])(implicit ctx: CanvasRenderingContext2D)
  extends DynamicObject {
  import Character._

  var position: Vector = Vector(ctx.canvas.width / 2, 0)

  private var ticks = 0
  private var walkFrame = 0
  private var looksRight = false
  var onGround = false
  var score = 0

  private def image = if (!onGround) {
    imageJump
  } else if (math.abs(velocity.x) < 0.5) {
    imageStand
  } else {
    imagesWalk(walkFrame)
  }

  def width = image.width
  def height = image.height

  def animate() = {
    ticks += 1
    if (ticks % Character.TicksPerFrame == 0) {
      walkFrame = (walkFrame + 1) % imagesWalk.length
    }
  }

  def draw() = {
    ctx.save()
    ctx.translate(position.x, position.y)

    ctx.font = ScoreFont;
    ctx.textAlign = "center"
    ctx.fillStyle = ScoreStyle
    ctx.fillText(score.toString(), 0, -130);

    if (!looksRight) ctx.scale(-1, 1);
    ctx.drawImage(image, -image.width / 2, -image.height / 2, image.width, image.height)
    ctx.restore()
  }

  def reactToKeyboard(keys: Set[Char]) = {
    if (keys contains 'o') {
      velocity = Vector(
        math.max(-MaxVelocity, velocity.x - WalkAcceleration),
        velocity.y)
      looksRight = false;
    } else if (keys contains 'p') {
      velocity = Vector(
        math.min(MaxVelocity, velocity.x + WalkAcceleration),
        velocity.y)
      looksRight = true;
    } else if (onGround) {
      velocity = Vector(
        velocity.x * WalkFriction,
        velocity.y)
    }

    if (onGround && keys.contains(' ')) {
      velocity = Vector(velocity.x, -JumpVelocity)
    }
  }

  def reset() = {
    velocity = Vector.Null
    position = Vector(ctx.canvas.width / 2.0f, -ctx.canvas.height / 2.0f);
    score = 0
  }
}
