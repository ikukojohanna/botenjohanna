package botenjohanna.components

trait DynamicObject {

  var position: Vector
  var velocity: Vector = Vector.Null
  def width: Double
  def height: Double

  def left = position.x - width / 2.0

  def right = position.x + width / 2.0f

  def top = position.y - height / 2.0f

  def bottom = position.y + height / 2.0f

  def isDisjunct(other: DynamicObject): Boolean = this.top > other.bottom ||
    other.top > this.bottom ||
    this.left > other.right ||
    other.left > this.right;

  def isOverlapping(other: DynamicObject): Boolean = !isDisjunct(other)

  def isOnGround(ground: Seq[RoundedRect]): Boolean = {
    val currentBottom = bottom
    val nextBottom = currentBottom + velocity.y;

    for (r <- ground) {
      val isLeft = position.x < r.position.x + r.innerRadius
      val isRight = position.x > r.position.x + r.width - r.innerRadius

      if (!isLeft && !isRight) {
        if (currentBottom <= r.position.y && nextBottom > r.position.y) {
          // in here, the character is colliding with the top edge of the current block
          return true
        }
      }
    }
    return false
  }

}