package flappyBird.view
import flappyBird.MainApp
import flappyBird.model.GameModel
import scalafx.scene.layout.AnchorPane
import scalafx.scene.text.Text
import scalafxml.core.macros.sfxml

@sfxml
class GameOverController(val gameOverPane: AnchorPane,
                         var currentScoreText: Text,
                         var topScoreText: Text) extends MainController {

  initialize()
  override def initialize(): Unit = {
    setScores()
  }

  //Set current and top score in the game over screen
  def setScores(): Unit = {
    currentScoreText.text = s"Current Score: ${GameModel.getCurrentScore}"
    topScoreText.text = s"Top Score: ${GameModel.getTopScore}"
  }

  //If the player clicked replay button, restart the game again
  def handleReplay(): Unit = {
    val playPane = MainApp.getPlayPane
    val playController = MainApp.getPlayController
    if (playPane != null && playController != null) {
      playController.restartGame()
    } else {
      navigateTo("Play")
    }
  }

  //If player clicked Exit button, direct back to welcome window
  def handleQuit(): Unit = {
    navigateTo("Welcome")
  }
}