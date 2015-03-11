package botenjohanna

import scala.scalajs.js.annotation.JSExport
import org.scalajs.dom
import org.scalajs.dom.raw.KeyboardEvent

/** Utility class to launch an application */
@JSExport("Launcher")
object Launcher {
  
  @JSExport 
  def main(canvas: dom.raw.HTMLCanvasElement): Unit = {
    val ctx = canvas.getContext("2d").asInstanceOf[dom.CanvasRenderingContext2D]
    val app = new Game(ctx)
    app.setup()
    dom.setInterval(() => app.loop(), 1000.0/60) // target 60 FPS
    dom.onkeydown = (e: KeyboardEvent) => app.keyDown(e)
    dom.onkeyup = (e: KeyboardEvent) => app.keyUp(e)
  }
  
}