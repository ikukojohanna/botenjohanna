package botenjohanna

import processing.Processing
import processing.PConstants._
import processing.PVector
import scala.collection.mutable.ArrayBuffer
import processing.PImage

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

  class Cloud(cloudImage: PImage) {

    //toFloat is necessary as random does not follow the 
    //specification and sometimes returns values outside of float limits 
    val randomScale = p.random(0.5f, 2.0f).toFloat
    val randomScaleX = p.random(0.75f, 1.25f).toFloat
    val randomScaleY = p.random(0.75f, 1.25f).toFloat

    val w = cloudImage.width * randomScale * randomScaleX
    val h = cloudImage.height * randomScale * randomScaleY

    var x = p.random(-w, p.width).toFloat
    var y = p.random(-h, p.height).toFloat

    val vX = p.random(2, 4).toFloat

    def drawCloud() = {
      p.noTint()
      p.imageMode(CORNER)
      p.image(cloudImage, x, y, w, h)
    }

    def animateCloud() = {
      x += vX;

      if (x > p.width) x = -w;
    }
  }

  trait GravityObject {
    def position: PVector
    def velocity: PVector
    def size: PVector
    var isOnGround: Boolean = false

    def isDisjunct(other: GravityObject): Boolean = {
      this.getTopY() > other.getBottomY() ||
        other.getTopY() > this.getBottomY() ||
        this.getLeftX() > other.getRightX() ||
        other.getLeftX() > this.getRightX();
    }

    def isOverlapping(other: GravityObject): Boolean = {
      !isDisjunct(other);
    }

    def getLeftX(): Float = {
      return position.x - size.x / 2.0f;
    }

    def getRightX(): Float = {
      return position.x + size.x / 2.0f;
    }

    def reactToKeyboard(): Unit = {}

    def reactToSurface(): Unit = {}

    def reactToFall(): Unit = {}

    def animateGravityObject(blocks: Seq[RoundedRect]) = {

      // part of superclass
      velocity.add(new PVector(0, GravityConstant));

      reactToKeyboard();

      val currentBottomY = getBottomY();
      val nextBottomY = currentBottomY + velocity.y;

      isOnGround = false;

      for (r <- blocks) {
        val isLeft = position.x < r.position.x + r.innerRadius
        val isRight = position.x > r.position.x + r.size.x - r.innerRadius

        if (!isLeft && !isRight) {
          if (currentBottomY == r.position.y ||
            (currentBottomY <= r.position.y && nextBottomY > r.position.y)) {

            // in here, the character is colliding with the top edge of the current block
            isOnGround = true;

            reactToSurface();
          }
        }
      }

      position.add(velocity);

      reactToFall();
    }

    def getTopY(): Float = {
      position.y - size.y / 2.0f;
    }

    def getBottomY(): Float = {
      position.y + size.y / 2.0f;
    }
  }

  class Coin(val position: PVector) extends GravityObject {
    val imageCoin = p.loadImage("assets/images/coin_gold.png")
    val velocity = new PVector(0, 0)
    val phaseOffset = p.random(0, TWO_PI).toFloat
    val size = new PVector(imageCoin.width, imageCoin.height);

    def drawCoin() = {
      p.pushMatrix();

      {
        p.translate(position.x, position.y);

        val sineValue = TWO_PI * p.millis() / 1000.0f;
        val scaleFactor = math.sin(sineValue + phaseOffset).toFloat;

        p.scale(scaleFactor, 1);

        p.noTint();
        p.imageMode(CORNER);
        p.image(
          imageCoin,
          -imageCoin.width / 2.0f,
          -imageCoin.height / 2.0f,
          imageCoin.width,
          imageCoin.height);
      }
      p.popMatrix();
    }

    override def reactToSurface() = {
      velocity.y = 0;
    }
  }

  class Scene {
    val clouds = new ArrayBuffer[Cloud]
    val blocks = new ArrayBuffer[RoundedRect]
    val coins = new ArrayBuffer[Coin]

    val cloudImage = p.loadImage("assets/images/cloud.png")

    for (i <- 0 until 5) {
      clouds += new Cloud(cloudImage)
    }

    val blockStyle1 = new Style(p.color(150, 200, 250));
    val blockStyle2 = new Style(p.color(200, 200, 250));

    addBlock(blockStyle1, 0, 200, 200);
    addBlock(blockStyle2, 200, 250, 150);
    addBlock(blockStyle1, 450, 350, 250);
    addBlock(blockStyle2, 700, 200, 200);
    addBlock(blockStyle1, 900, 350, 250);
    addBlock(blockStyle2, 1100, 350, 200);

    spawnCoins(5)

    def addBlock(blockStyle: Style, x: Float, blockWidth: Float, blockHeight: Float) {
      blocks += new RoundedRect(
        new PVector(x, p.height - blockHeight),
        new PVector(blockWidth, blockHeight),
        20.0f,
        blockStyle);
    }

    def spawnCoins(numberOfCoins: Int) = {
      for (i <- 0 until numberOfCoins) {
        val randomBlockIndex = p.random(0, blocks.size).toInt
        val randomBlock = blocks(randomBlockIndex);

        val randomX = p.random(
          randomBlock.position.x + randomBlock.innerRadius,
          randomBlock.position.x + randomBlock.size.x - randomBlock.innerRadius).toFloat;

        coins += new Coin(new PVector(randomX, 0.0f))
      }
    }

    def drawScene() = {
      for (c <- clouds) {
        c.drawCloud()
      }
      for (b <- blocks) {
        b.drawRect()
      }
      for (c <- coins) {
        c.drawCoin()
      }
    }

    def animateScene() = {
      for (c <- clouds) {
        c.animateCloud()
      }
      for (c <- coins) {
        c.animateGravityObject(blocks)
      }
    }

  }

  val GravityConstant = 0.50f
  val JumpVelocity = 15.00f
  val WalkAcceleration = 0.50f
  val MaxVelocity = 5.00f
  val WalkFriction = 0.80f

  var scene: Scene = _

  def setup() = {
    p.size(1500, 800);
    scene = new Scene
  }

  def draw() = {
    p.background(192, 255, 255);

    scene.animateScene()
    scene.drawScene()

  }

}