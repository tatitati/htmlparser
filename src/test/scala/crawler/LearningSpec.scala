package crawler

import org.scalatest.FunSuite
import scala.concurrent.{Await, Future}
import scala.concurrent.duration._
import scala.concurrent.ExecutionContext.Implicits.global
import scala.util.{Failure, Success}

class LearningSpec extends FunSuite {

  val r = scala.util.Random
  val a: List[Int] = (1 to 20).toList
  val b: List[Future[Int]] = a.map{n =>
    val c: Future[Int] = Future{
      val waitTime: Int = (1000 to 10000 by 1000)(r.nextInt(10))
      println(Thread.currentThread.getName() + " | working with: " + n + " waiting: " + waitTime)

      Thread.sleep(waitTime) // random thread bussy time
      n * 100
    }

    c onComplete{
      case Success(value) => value
      case Failure(_) => 0
    }

    c
  }

  test("I can emulate a series of futures/threads with random bussy times"){
    val resultSequential: List[Int] = b.map(f =>
      Await.result(f, 100 seconds)
    )

    println(resultSequential)
  }
}
