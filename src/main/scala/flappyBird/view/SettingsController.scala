package flappyBird.view
import scalafx.scene.control.Slider
import scalafxml.core.macros.sfxml
import flappyBird.MainApp
import javafx.beans.value.{ChangeListener, ObservableValue}
import scalafx.event.ActionEvent

import scala.beans.BeanProperty

@sfxml
class SettingsController(@BeanProperty var musicSlider: Slider) extends MainController {

  //Method to redirect to Welcome window
  def home(event: ActionEvent) : Unit = {
    navigateTo("Welcome")
  }

  //Get media player from MainApp
  private val mediaPlayer = MainApp.getMediaPlayer

  //Initialize the value of the slider based on the volume of media player
  musicSlider.value = mediaPlayer.getVolume * 100

  //Adjust music volume based on slider's input value
  override def initialize() {
    musicSlider.valueProperty().addListener(new ChangeListener[Number] {
      override def changed(observable: ObservableValue[_ <: Number], oldValue: Number, newValue: Number): Unit = {
        mediaPlayer.setVolume(newValue.doubleValue() / 100)
      }
    })
  }
  initialize()
}