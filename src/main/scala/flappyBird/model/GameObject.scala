package flappyBird.model
import scalafx.scene.image.ImageView
import scalafx.scene.shape.Shape

abstract class GameObject(var imageView: ImageView) {

  //Move game object with x and y values (dx = value to move along x-axis; dy = value to move along y-axis)
  def move(dx: Double, dy: Double): Unit = {
    imageView.translateX.value += dx
    imageView.translateY.value += dy
  }

  //Get the boundary of the game object (to be used in checking collision)
  def getBounds = imageView.getBoundsInParent

  def update(): Unit
}
