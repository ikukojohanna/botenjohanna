package botenjohanna

case class Vector(x: Double, y: Double) {
  def +(other: Vector) = Vector(this.x + other.x, this.y + other.y)
  def -(other: Vector) = Vector(this.x - other.x, this.y - other.y)
  def *(n: Double) = Vector(x * n, y * n)
}