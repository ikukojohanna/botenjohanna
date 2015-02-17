package processing

import scalajs.js

class PVector(var x: Float, var y: Float, var z: Float) extends js.Object {
  
  def this(x: Float, y: Float) = this(x, y, 0)

}