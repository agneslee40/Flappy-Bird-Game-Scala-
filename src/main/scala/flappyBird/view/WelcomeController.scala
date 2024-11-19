package flappyBird.view
import scalafx.event.ActionEvent
import scalafxml.core.macros.sfxml

@sfxml
class WelcomeController extends MainController {
  //Utilise navigateTo function from MainController to redirect to corresponding window

  def startPlay(event: ActionEvent) : Unit = {
    navigateTo("Play")
  }

  def home(event: ActionEvent) : Unit = {
    navigateTo("Welcome")
  }

  def scores(event: ActionEvent) : Unit = {
    navigateTo("Scores")
  }

  def settings(event: ActionEvent) : Unit = {
    navigateTo("Settings")
  }

  def help(event: ActionEvent) : Unit = {
    navigateTo("Help")
  }

  override def initialize(): Unit = {
  }

}