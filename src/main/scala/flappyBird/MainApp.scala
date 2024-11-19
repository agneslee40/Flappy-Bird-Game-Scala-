package flappyBird
import flappyBird.view.PlayController
import scalafx.application.JFXApp
import scalafx.application.JFXApp.PrimaryStage
import scalafx.scene.Scene
import scalafx.Includes._
import scalafxml.core.{FXMLLoader, NoDependencyResolver}
import javafx.{scene => jfxs}
import scalafx.scene.image.Image
import scalafx.scene.media.{Media, MediaPlayer}


object MainApp extends JFXApp {

  //Background music file path
  val musicFilePath = getClass.getResource("/music/flappyBirdbgMusic.mp3").toString
  val backgroundMusic = new Media(musicFilePath)
  val mediaPlayer = new MediaPlayer(backgroundMusic)

  //set initial volume for background music
  mediaPlayer.setVolume(0.4)

  // Play and loop background music once app is opened
  mediaPlayer.setCycleCount(MediaPlayer.Indefinite)
  mediaPlayer.play()
  def getMediaPlayer: MediaPlayer = mediaPlayer

  //Load root layout
  val rootResource = getClass.getResource("view/RootLayout.fxml")
  val loader = new FXMLLoader(rootResource, NoDependencyResolver)
  loader.load();
  val roots = loader.getRoot[jfxs.layout.BorderPane]
  stage = new PrimaryStage {
    title = "FlappyBird"
    icons += new Image(getClass.getResourceAsStream("/images/flappybird.png"))
    scene = new Scene {
      root = roots
    }
  }

  //show flappyBird overview window
  def showFlappyBirdPlay() = {
    val resource = getClass.getResource("view/flappyBirdPlay.fxml")
    val loader = new FXMLLoader(resource, NoDependencyResolver)
    loader.load();
    val playPane = loader.getRoot[jfxs.layout.AnchorPane]
    playPane.setId("playPane")
    roots.setCenter(playPane)
    playPane.requestFocus()
  }

  private var playPane: jfxs.layout.AnchorPane = _
  private var playController: PlayController = _

  def getPlayPane: javafx.scene.layout.AnchorPane = playPane
  def getPlayController: PlayController = playController

  //show welcome window
  def showWelcome() = {
    val resource = getClass.getResource("view/Welcome.fxml")
    val loader = new FXMLLoader(resource, NoDependencyResolver)
    loader.load();
    val roots = loader.getRoot[jfxs.layout.AnchorPane]
    this.roots.setCenter(roots)
  }

  //show help window
  def showHelp() = {
    val resource = getClass.getResource("view/Help.fxml")
    val loader = new FXMLLoader(resource, NoDependencyResolver)
    loader.load();
    val roots = loader.getRoot[jfxs.layout.AnchorPane]
    this.roots.setCenter(roots)
  }

  //show scores window
  def showScores() = {
    val resource = getClass.getResource("view/Scores.fxml")
    val loader = new FXMLLoader(resource, NoDependencyResolver)
    loader.load();
    val roots = loader.getRoot[jfxs.layout.AnchorPane]
    this.roots.setCenter(roots)
  }

  //show settings window
  def showSettings() = {
    val resource = getClass.getResource("view/Settings.fxml")
    val loader = new FXMLLoader(resource, NoDependencyResolver)
    loader.load();
    val roots = loader.getRoot[jfxs.layout.AnchorPane]
    this.roots.setCenter(roots)
  }

  //show game over screen
  def showGameOverScreen() = {
    val resource = getClass.getResource("view/gameOver.fxml")
    val loader = new FXMLLoader(resource, NoDependencyResolver)
    loader.load();
    val roots = loader.getRoot[jfxs.layout.AnchorPane]
    this.roots.setCenter(roots)

  }

  //Display welcome window when app first runs
  showWelcome()
}
