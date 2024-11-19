package flappyBird.view
import flappyBird.model.{GameModel, Bird}
import scalafxml.core.macros.sfxml
import scalafx.animation.AnimationTimer
import scalafx.scene.input.{KeyCode, KeyEvent}
import scalafx.scene.image.ImageView
import scalafx.scene.layout.AnchorPane
import scalafx.application.Platform
import scalafx.scene.shape.Rectangle
import scala.util.Random
import scalafx.collections.ObservableBuffer
import scalafx.scene.control.Label

@sfxml
class PlayController(val plane: AnchorPane,
                     val bird: ImageView,
                     val score: Label,
                     var gameOverController: GameOverController) extends MainController {

  //Variables for game according to physics
  private val gravity = 0.02
  private var velocity = 0.0
  private val flyDistance = 60.0

  private var lastObstacleTime = 0L
  private val obstacleInterval = 2000
  private var scoresCount = 0
  private var topScore = 0
  var currentScore: Int = _
  private val restart = AnimationTimer { _ => update() }
  private var isPaused = false
  private val birdObject = new Bird(bird)
  private val greenPipe = ObservableBuffer[Rectangle]()

  initialize()

  //Fly the bird up when spacebar is hit, and pause the game if ESC is hit
  def hitSpacebar(event: KeyEvent): Unit = {
    //When game is paused, hitting spacebar will not fly the bird up
    if (event.code == KeyCode.Space && !isPaused) {
      flyUp()
    }
    if (event.code == KeyCode.Escape){
      togglePause()
    }
  }

  //Changing Y position of bird to let it fly
  def flyUp(): Unit = {
    val birdY = bird.layoutY.value + bird.y.value
    if (birdY <= flyDistance) {
      changeY(-(birdY))
      velocity = 0
    } else {
      changeY(-flyDistance)
      velocity = 0
    }
  }

  //Method to change Y position of bird
  def changeY(positionChange: Double): Unit = {
    bird.y.value = bird.y.value + positionChange
  }

  //Check if bird falls below the screen
  def reachBottom(): Boolean = {
    val birdY = bird.layoutY.value + bird.y.value
    birdY >= plane.height.value
  }

  //Check if bird collide with green pipes
  def checkCollision(): Boolean = {
    greenPipe.exists(_.getBoundsInParent.intersects(birdObject.getBounds))
  }

  //Initialize the game by setting appropriate dimensions for plane
  override def initialize(): Unit = {
    plane.minHeight = 800
    plane.maxHeight = 800
    Platform.runLater(() => {
      plane.prefWidth = 500
      plane.prefHeight = 800
      setupGame()
    })
  }

  //Reset the game state and start animation timer
  private def setupGame(): Unit = {
    Platform.runLater(() => {
      plane.prefHeight = 800
      bird.y() = 0
      resetGameState()
      restart.start()
    })
  }

  //Reset game variables like velocity, ..., and clear green pipes
  private def resetGameState(): Unit = {
    velocity = 0
    scoresCount = 0
    score.setText(scoresCount.toString)
    val nodesToRemove: Seq[javafx.scene.Node] = greenPipe.map(_.delegate)
    plane.children.removeAll(nodesToRemove: _*)
    greenPipe.clear()
  }

  //To make the bird fly and allow moving green pipes
  def update(): Unit = {
    if (!isPaused){
      velocity += 1
      changeY(gravity * velocity)

      val currentTime = System.currentTimeMillis()
      if (currentTime - lastObstacleTime > obstacleInterval) {
        createGreenPipe()
        lastObstacleTime = currentTime
      }

      moveGreenPipe()

      if (reachBottom() || checkCollision()) {
        gameOver()
      }
    }
  }

  private def createGreenPipe(): Unit = {
    val width = 25
    val planeWidth = 500
    val planeHeight = 800
    val xPos = planeWidth - 100
    val space = 190
    val recTopHeight = Random.nextInt((planeHeight - space - 100).toInt) + 50
    val recBottomHeight = planeHeight - space - recTopHeight

    val rectangleTop = new Rectangle {
      x = xPos
      y = 0
      width = 25
      height = recTopHeight
      fill = scalafx.scene.paint.Color.Green
    }

    val rectangleBottom = new Rectangle {
      x = xPos
      y = recTopHeight + space
      width = 25
      height = recBottomHeight
      fill = scalafx.scene.paint.Color.Green
    }
    greenPipe += rectangleTop
    greenPipe += rectangleBottom
    plane.children.addAll(rectangleTop, rectangleBottom)
  }

  //Move green pipe to the left and delete it when they move outside of the screen
  private def moveGreenPipe(): Unit = {
    val toRemove = collection.mutable.ListBuffer[Rectangle]()

    greenPipe.foreach { rectangle =>
      moveRectangle(rectangle, -0.75)
      if (rectangle.x.value <= -rectangle.width.value) {
        toRemove += rectangle
      }
    }

    if (toRemove.nonEmpty && toRemove.head.x.value < bird.layoutX.value) {
      scoresCount += 1
      score.setText(scoresCount.toString)
    }
    greenPipe --= toRemove
    toRemove.foreach(plane.children.remove)
  }

  //Move a single rectangle (green pipe) along the x-axis
  private def moveRectangle(rectangle: Rectangle, deltaX: Double): Unit = {
    rectangle.x = rectangle.x.value + deltaX
  }

  private def updateScore(): Unit = {
    if (greenPipe.nonEmpty && isPoint(greenPipe.head, bird)){
      scoresCount += 1
      score.setText(scoresCount.toString)
    }
  }

  //Check if point should be awarded (condition: fly through green pipes' gap successfully)
  private def isPoint(obstacle: Rectangle, bird: ImageView): Boolean = {
    val birdPositionX = bird.layoutX.value + bird.x.value
    obstacle.x.value <= birdPositionX && obstacle.x.value + obstacle.width.value>= birdPositionX
  }

  def restartGame(): Unit = {
    initialize()
    restart.start()
  }

  //Call the game over screen and display results
  def gameOver():Unit = {
    if (scoresCount > GameModel.getTopScore) {
      GameModel.setScores(scoresCount, scoresCount)
    } else {
      GameModel.setScores(scoresCount, topScore)
    }
    navigateTo("GameOver")
    restart.stop()
  }

  //Pause or play the game according to the pause status
  def togglePause(): Unit = {
    isPaused = !isPaused
    if (isPaused) {
      restart.stop()
    } else {
      restart.start()
    }
  }

}
