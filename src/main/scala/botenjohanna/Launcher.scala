package botenjohanna

import scala.scalajs.js.Any.fromFunction0
import scala.scalajs.js.Any.fromFunction1
import scala.scalajs.js.annotation.JSExport

import org.scalajs.dom
import org.scalajs.dom.raw.CanvasRenderingContext2D
import org.scalajs.dom.raw.HTMLAudioElement
import org.scalajs.dom.raw.HTMLCanvasElement
import org.scalajs.dom.raw.HTMLImageElement
import org.scalajs.dom.raw.KeyboardEvent

/** Utility class to launch an application */
@JSExport("Launcher")
object Launcher {

  @JSExport
  def main(
    canvas: HTMLCanvasElement,
    cloudImage: HTMLImageElement,
    coinImage: HTMLImageElement,
    backgroundMusic: HTMLAudioElement): Unit = {

    val ctx = canvas.getContext("2d").asInstanceOf[CanvasRenderingContext2D]
    val assets = Assets(cloudImage, coinImage, backgroundMusic)
    val app = new Game(assets)(ctx)
    app.setup()
    dom.setInterval(() => app.loop(), 1000.0 / 60) // target 60 FPS
    dom.onkeydown = (e: KeyboardEvent) => app.keyDown(e)
    dom.onkeyup = (e: KeyboardEvent) => app.keyUp(e)
  }

}