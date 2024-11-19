package flappyBird.view
import flappyBird.model.GameModel
import scalafx.event.ActionEvent
import scalafx.scene.control.Label
import scalafxml.core.macros.sfxml

@sfxml
class ScoresController (var currentScoreLabel: Label, var topScoreLabel: Label) extends MainController {

  initialize()

  override def initialize(): Unit = {
    updateScores()
  }

  //Update the labels in the Scores window based on the values from the GameModel
  def updateScores(): Unit = {
    currentScoreLabel.text = GameModel.getCurrentScore.toString
    topScoreLabel.text = GameModel.getTopScore.toString
  }

  //Method to redirect to welcome window
  def home(event: ActionEvent) : Unit = {
    navigateTo("Welcome")
  }
}

