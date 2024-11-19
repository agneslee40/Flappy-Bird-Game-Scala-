package flappyBird.view
import flappyBird.MainApp

abstract class MainController {
  def initialize(): Unit

  //Direct the player to the corresponding window when button is clicked
  def navigateTo(window: String): Unit = {
    window match {
      case "Welcome" => MainApp.showWelcome()
      case "Play" => MainApp.showFlappyBirdPlay()
      case "Scores" => MainApp.showScores()
      case "Settings" => MainApp.showSettings()
      case "Help" => MainApp.showHelp()
      case "GameOver" => MainApp.showGameOverScreen()
      case _ => throw new IllegalArgumentException("Unknown window")
    }
  }
}
