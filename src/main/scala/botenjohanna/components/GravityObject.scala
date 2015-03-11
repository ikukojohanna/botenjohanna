package botenjohanna.components

trait GravityObject {

  def width: Double

  def height: Double

  def isDisjunct(other: GravityObject): Boolean = this.top > other.bottom ||
    other.top > this.bottom ||
    this.left > other.right ||
    other.left > this.right;

  def isOverlapping(other: GravityObject): Boolean = !isDisjunct(other)

  def left = position.x - width / 2.0

  def right = position.x + width / 2.0f

  def top = position.y - height / 2.0f

  def bottom = position.y + height / 2.0f

  def reactToKeyboard(): Unit = {}

  def reactToSurface(): Unit = {}

  def reactToFall(): Unit = {}

  var position: Vector
  var velocity: Vector = Vector.Null
  var isOnGround: Boolean = false

  def animateGravity(blocks: Seq[RoundedRect], gravity: Vector) = {
    velocity += gravity

    reactToKeyboard();

    val currentBottom = bottom
    val nextBottom = currentBottom + velocity.y;

    isOnGround = false;

    for (r <- blocks) {
      val isLeft = position.x < r.position.x + r.innerRadius
      val isRight = position.x > r.position.x + r.width - r.innerRadius

      if (!isLeft && !isRight) {
        if (currentBottom <= r.position.y && nextBottom > r.position.y) {
          // in here, the character is colliding with the top edge of the current block
          isOnGround = true;
          reactToSurface();
        }
      }
    }

    position += velocity;

    reactToFall();
  }

}