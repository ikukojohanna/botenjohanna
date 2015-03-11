package botenjohanna

import org.scalajs.dom.CanvasRenderingContext2D
import org.scalajs.dom.raw.CanvasRenderingContext2D
import org.scalajs.dom.raw.HTMLCanvasElement
import org.scalajs.dom.raw.KeyboardEvent

class Game(ctx: CanvasRenderingContext2D) {

  case class RoundedRect(position: Vector, width: Double, height: Double, innerRadius: Double, fill: String) {

    def draw() = {
      val x = position.x
      val y = position.y
      val r = innerRadius

      ctx.beginPath()

      /* Path drawing order 
       * 1  8   7
       *    --
       * 2 |  | 6
       *    --
       * 3  4   5
       */
      ctx.moveTo(x + r, y)
      ctx.quadraticCurveTo(x, y, x, y + r) //1
      ctx.lineTo(x, y + height - r) //2
      ctx.quadraticCurveTo(x, y + height, x + r, y + height) //3
      ctx.lineTo(x + width - r, y + height) //4
      ctx.quadraticCurveTo(x + width, y + height, x + width, y + height - r) //5
      ctx.lineTo(x + width, y + r)
      ctx.quadraticCurveTo(x + width, y, x + width - r, y)
      ctx.lineTo(x + r, y)

      ctx.fillStyle = fill
      ctx.fill()
    }
  }

  object RoundedRect {
    def apply(x: Int, width: Double, height: Double, fill: String): RoundedRect = RoundedRect(Vector(x, ctx.canvas.height - height), width, height, 20, fill)
  }

  val c1 = "#96c8fa"
  val c2 = "#c8c8fa"
  lazy val blocks = List(
    RoundedRect(0, 200, 200, c1),
    RoundedRect(200, 250, 150, c2),
    RoundedRect(450, 350, 250, c1),
    RoundedRect(700, 200, 200, c2),
    RoundedRect(900, 350, 250, c1),
    RoundedRect(1100, 350, 200, c2))

  def setup(): Unit = {
    ctx.canvas.width = 1400
    ctx.canvas.height = 1000
  }

  def loop(): Unit = {
    draw()
  }

  def keyDown(e: KeyboardEvent): Unit = {

  }

  def keyUp(e: KeyboardEvent): Unit = {

  }

  def draw() = {
    ctx.fillStyle = "#c0ffff"
    ctx.fillRect(0, 0, ctx.canvas.width, ctx.canvas.height)

    for (b <- blocks) b.draw
  }

  /*
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
  }*/

  /*
  object scene {
    //val clouds = new ArrayBuffer[Cloud]
    
   // val coins = new ArrayBuffer[Coin]

    /*
    val cloudImage = p.loadImage("assets/images/cloud.png")

    for (i <- 0 until 5) {
      clouds += new Cloud(cloudImage)
    }*/

    val blockStyle1 = new Style(p.color(150, 200, 250));
    val blockStyle2 = new Style(p.color(200, 200, 250));

    addBlock(blockStyle1, 0, 200, 200);
    addBlock(blockStyle2, 200, 250, 150);
    addBlock(blockStyle1, 450, 350, 250);
    addBlock(blockStyle2, 700, 200, 200);
    addBlock(blockStyle1, 900, 350, 250);
    addBlock(blockStyle2, 1100, 350, 200);

    spawnCoins(5)

    /*
    def addBlock(blockStyle: Style, x: Float, blockWidth: Float, blockHeight: Float) {
      blocks += new RoundedRect(
        new PVector(x, p.height - blockHeight),
        new PVector(blockWidth, blockHeight),
        20.0f,
        blockStyle);
    }*/
/*
    def spawnCoins(numberOfCoins: Int) = {
      for (i <- 0 until numberOfCoins) {
        val randomBlockIndex = p.random(0, blocks.size).toInt
        val randomBlock = blocks(randomBlockIndex);

        val randomX = p.random(
          randomBlock.position.x + randomBlock.innerRadius,
          randomBlock.position.x + randomBlock.size.x - randomBlock.innerRadius).toFloat;

        coins += new Coin(new PVector(randomX, 0.0f))
      }
    }*/

    def drawScene() = {
      for (b <- blocks) {
        b.drawRect()
      }
    }

  }

  val GravityConstant = 0.50f
  val JumpVelocity = 15.00f
  val WalkAcceleration = 0.50f
  val MaxVelocity = 5.00f
  val WalkFriction = 0.80f

  def setup() = {
    p.size(1500, 800);
    scene = new Scene
  }

  def draw() = {
    p.background(192, 255, 255);

    scene.animateScene()
    scene.drawScene()

  }
  * */

}