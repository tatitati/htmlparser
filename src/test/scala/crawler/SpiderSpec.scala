package crawler

import org.scalatest.FunSuite
import scala.concurrent.{Await, Future}
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Await
import scala.concurrent.duration._

class SpiderSpec extends FunSuite {

  test("I can add to the map") {
    val givenMap = Map(
      "a" -> Set("a1", "a2")
    )

    val mapUpdated = Spider.buildMap("b", Set("b1", "b2"), givenMap)
    assert(Map(
      "a" -> Set("a1", "a2"),
      "b" -> Set("b1", "b2")
    ) == mapUpdated)
  }

  test("can analyze") {
    val result = Spider.analyze("http://monzo.com")
    val map = Await.result(result, 15 seconds)
    println(map)
  }
}
