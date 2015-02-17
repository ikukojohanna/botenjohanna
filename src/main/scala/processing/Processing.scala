package processing

import scalajs.js

trait Processing extends js.Object {
  
  var setup: js.Function0[Unit] = js.native
  var draw: js.Function0[Unit] = js.native
  
  def height: Float = js.native
  
  def color(r: Float, g: Float, b: Float): Int = js.native 
  
  def fill(color: Int): Unit = js.native
  def noFill(): Unit = js.native
  def stroke(color: Int): Unit = js.native
  def strokeWeight(weight: Int): Unit = js.native
  def noStroke(): Unit = js.native
  
  def ellipseMode(radius: Int): Unit = js.native
  def rectMode(mode: Int): Unit = js.native
  def arc(x: Float, y: Float, width: Float, height: Float, start: Float, stop: Float): Unit = js.native
  def rect(x: Float, y: Float, width: Float, height: Float): Unit = js.native
  def rect(x: Float, y: Float, width: Float, height: Float, radius: Float): Unit = js.native
  def rect(x: Float, y: Float, width: Float, height: Float, tlradius: Float, trradius: Float, brradius: Float, blradius: Float): Unit = js.native
  
  def loadImage(location: String): PImage = js.native
  def image(img: PImage, x: Float, y: Float): Unit = js.native
  def image(img: PImage, x: Float, y: Float, width: Float, height: Float): Unit = js.native
  
  def size(width: Int, height: Int): Unit = js.native
  def background(red: Int, green: Int, blue: Int): Unit = js.native
  def stroke(red: Int, green: Int, blue: Int): Unit = js.native
  def println(message: String): Unit = js.native
  
}