package botenjohanna

import scala.collection.mutable.HashSet
import scala.scalajs.js.Any.fromString
import scala.scalajs.js.Any.wrapArray
import scala.util.Random

import org.scalajs.dom.raw.CanvasRenderingContext2D
import org.scalajs.dom.raw.KeyboardEvent

import botenjohanna.components.Character
import botenjohanna.components.Cloud
import botenjohanna.components.Coin
import botenjohanna.components.RoundedRect
import botenjohanna.components.Vector

class Game(assets: Assets)(implicit ctx: CanvasRenderingContext2D) {

  val blockStyle1 = "#96c8fa"
  val blockStyle2 = "#c8c8fa"

  lazy val blocks = Array(
    RoundedRect(0, 200, 200, blockStyle1),
    RoundedRect(200, 250, 150, blockStyle2),
    RoundedRect(450, 350, 250, blockStyle1),
    RoundedRect(700, 200, 200, blockStyle2),
    RoundedRect(900, 350, 250, blockStyle1),
    RoundedRect(1100, 350, 200, blockStyle2))

  lazy val clouds = for (i <- 0 until 5) yield {
    Cloud.random(assets.cloudImage)
  }

  lazy val coins = new HashSet[Coin]

  lazy val peter = new Character(assets.side, assets.jump, assets.walks)

  val Gravity = Vector(0, 0.50f)

  val keys = new HashSet[Char]

  def setup(): Unit = {
    ctx.canvas.width = 1400
    ctx.canvas.height = 800
    assets.backgroundMusic.loop = true
    reset()
  }

  def loop(): Unit = {
    draw()
    animate()
  }

  def keyDown(e: KeyboardEvent): Unit = {
    keys += e.keyCode.toChar.toLower
  }

  def keyUp(e: KeyboardEvent): Unit = {
    keys -= e.keyCode.toChar.toLower
  }

  def reset() = {
    peter.reset()
    coins.clear()
    spawnCoins()
    assets.backgroundMusic.currentTime = 0
    assets.backgroundMusic.play()
  }

  def spawnCoins() = {
    for (i <- 0 until (5 + Random.nextInt(5))) {
      coins += Coin.spawn(assets.coinImage, blocks)
    }
  }

  def draw() = {
    ctx.fillStyle = "#c0ffff"
    ctx.fillRect(0, 0, ctx.canvas.width, ctx.canvas.height)

    for (c <- clouds) c.draw
    for (b <- blocks) b.draw
    for (c <- coins) c.draw
    peter.draw()
  }

  def animate() = {
    for (c <- clouds) {
      c.animate()
    }
    for (c <- coins) {
      c.animate()
      c.position += c.velocity
      c.velocity += Gravity
      if (c isOnGround blocks)
        c.velocity = Vector.Null
    }

    peter.animate()
    peter.onGround = false
    peter.position += peter.velocity
    peter.velocity += Gravity
    if (peter isOnGround blocks) {
      peter.onGround = true
      peter.velocity = Vector(peter.velocity.x, 0)
    }
    for (c <- coins) {
      if (peter.isOverlapping(c)) {
        coins -= c
        peter.score += 1
      }
    }
    if (coins.isEmpty) {
      spawnCoins()
    }
    if (peter.position.y > ctx.canvas.height) {
      reset()
    }
    peter.reactToKeyboard(keys.toSet)
  }

}