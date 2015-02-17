package botenjohanna

import processing._
import scala.scalajs.js.annotation.JSExport
import scala.scalajs.js.Any.fromFunction0

/** Utility class to launch an application */
@JSExport("Launcher")
class Launcher {
  
  @JSExport
  def launch(processing: Processing): Unit = {
    val app = new Application(processing)
    processing.setup = () => app.setup()
    processing.draw = () => app.draw()
  }
  
}