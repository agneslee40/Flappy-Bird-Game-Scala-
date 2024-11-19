package flappyBird.model
import scalafx.scene.image.ImageView

class Bird(imageView: ImageView) extends GameObject(imageView) {
  private var velocity = 0.0
  private val gravity = 0.02
  private val flyDistance = 60.0  //distance for bird to fly when player hits spacebar

  //Method to let bird fly up
  def flyUp(): Unit = {
    if (imageView.translateY.value <= flyDistance) {

      //Stop bird from flying beyond the screen
      move(0, -imageView.translateY.value)
      velocity = 0

    } else {

      //Let bird fly up with flying distance
      move(0, -flyDistance)
      velocity = 0
    }
  }

  //Update the bird's position
  override def update(): Unit = {

    //Allow the bird to fly downwards when player does not hit spacebar
    velocity += 1
    move(0, gravity * velocity)
  }
}
