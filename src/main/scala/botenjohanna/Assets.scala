package botenjohanna

import scala.scalajs.js

import org.scalajs.dom.raw.HTMLAudioElement
import org.scalajs.dom.raw.HTMLImageElement

trait Assets extends js.Object {
  def cloudImage: HTMLImageElement = js.native
  def coinImage: HTMLImageElement = js.native
  def backgroundMusic: HTMLAudioElement = js.native
  def jump: HTMLImageElement = js.native
  def side: HTMLImageElement = js.native
  def walks: js.Array[HTMLImageElement] = js.native
}