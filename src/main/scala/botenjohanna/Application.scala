package botenjohanna

import processing.Processing
import processing.PConstants._
import processing.PVector
import scala.collection.mutable.ArrayBuffer

class Application(p: Processing) {

  case class Style(
    fillColor: Int,
    strokeColor: Int,
    strokeSize: Int,
    enableFill: Boolean,
    enableStroke: Boolean) {

    def this(fillColor: Int) = this(fillColor, 0, 0, true, false)

    def set() {
      if (enableFill) p.fill(fillColor);
      else p.noFill();

      if (enableStroke) {
        p.stroke(strokeColor);
        p.strokeWeight(strokeSize);
      } else {
        p.noStroke();
      }
    }
  }

  case class RoundedRect(
    position: PVector,
    size: PVector,
    innerRadius: Float,
    style: Style) {

    def drawRect() = {
      style.set()
      p.ellipseMode(RADIUS);
      p.arc(position.x + innerRadius, position.y + innerRadius, innerRadius, innerRadius, PI, PI + HALF_PI);
      p.arc(position.x + size.x - innerRadius, position.y + innerRadius, innerRadius, innerRadius, PI + HALF_PI, TWO_PI);
      p.arc(position.x + size.x - innerRadius, position.y + size.y - innerRadius, innerRadius, innerRadius, 0, HALF_PI);
      p.arc(position.x + innerRadius, position.y + size.y - innerRadius, innerRadius, innerRadius, HALF_PI, PI);

      p.rectMode(CORNER);
      p.rect(position.x + innerRadius, position.y, size.x - innerRadius * 2, innerRadius);
      p.rect(position.x + innerRadius, position.y + size.y - innerRadius, size.x - innerRadius * 2, innerRadius);
      p.rect(position.x, position.y + innerRadius, innerRadius, size.y - innerRadius * 2);
      p.rect(position.x + size.x - innerRadius, position.y + innerRadius, innerRadius, size.y - innerRadius * 2);
      p.rect(position.x + innerRadius, position.y + innerRadius, size.x - innerRadius * 2, size.y - innerRadius * 2)
    }
  }

  class Scene {
    val blocks = new ArrayBuffer[RoundedRect]

    val blockStyle1 = new Style(p.color(150, 200, 250));
    val blockStyle2 = new Style(p.color(200, 200, 250));

    def addBlock(blockStyle: Style, x: Float, blockWidth: Float, blockHeight: Float) {
      blocks += new RoundedRect(
        new PVector(x, p.height - blockHeight),
        new PVector(blockWidth, blockHeight),
        20.0f,
        blockStyle);
    }
    
    def drawScene() = {
      for (b <- blocks) {
        b.drawRect();
      }
    }

    addBlock(blockStyle1, 0, 200, 200);
    addBlock(blockStyle2, 200, 250, 150);
    addBlock(blockStyle1, 450, 350, 250);
    addBlock(blockStyle2, 700, 200, 200);
    addBlock(blockStyle1, 900, 350, 250);
    addBlock(blockStyle2, 1100, 350, 200);

  }

  val scene = new Scene
  val image = p.loadImage("assets/images/cloud.png")
  
  def setup() = {
    p.size(1500, 800);
  }
  
  def draw() = {
    scene.drawScene()
    p.image(image, 100, 100);
  }

}