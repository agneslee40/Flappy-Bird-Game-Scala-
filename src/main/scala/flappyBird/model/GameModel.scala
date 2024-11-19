package flappyBird.model
import scala.io.Source
import java.io.{File, InputStream, PrintWriter}

//Handles game scoring system
object GameModel {
  private var currentScore = 0
  private var topScore = 0
  private val scoresFile = "src/main/resources/scores.txt"

  //Load scores from scores.txt when initialized
  loadScores()

  //Store the scores (current and top scores) accordingly
  def setScores(score: Int, top: Int): Unit = {
    currentScore = score

    //Update top score if new score is higher
    if (top >  topScore){
      topScore = top
    }
    saveScores()
  }

  def getCurrentScore: Int = currentScore
  def getTopScore: Int = topScore

  //Save current and top scores into scores.txt
  private def saveScores(): Unit = {
    val file = new File(scoresFile)
    try {
      val writer = new PrintWriter(file)
      writer.println(s"$currentScore")
      writer.println(s"$topScore")
      writer.close()
    } catch {
      case e: Exception =>
        println(s"Error saving scores: ${e.getMessage}")
    }
  }


  private def loadScores(): Unit = {
    try {
      val inputStream: InputStream = getClass.getClassLoader.getResourceAsStream("scores.txt")
      if (inputStream != null) {
        try {
          val bufferedSource = Source.fromInputStream(inputStream)
          val scores = bufferedSource.getLines().map(_.toInt).toList
          if (scores.nonEmpty) {
            currentScore = scores.headOption.getOrElse(0)
            topScore = scores.lift(1).getOrElse(0)
          }
          bufferedSource.close()
        } catch {
          case e: Exception =>
            println(s"Error reading the file: ${e.getMessage}")
        }
      } else {
        println("Resource not found: scores.txt")
      }
    } catch {
      case e: Exception =>
        println(s"Error loading scores: ${e.getMessage}")
    }
  }
}