package crawler

import org.jsoup.Jsoup
import org.scalatest.FunSuite
import scalaj.http._
import scala.concurrent.Await
import scala.concurrent.duration._
import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global

class DownloaderSpec extends FunSuite {
  test("I can get the the body of a url"){
    val doc = Jsoup.connect("http://elpais.com/").get
    println(doc.body().html().slice(0, 200))
  }

  test("I can use futures to do it async") {
    val futureDoc = Future {
      Jsoup.connect("http://elpais.com/").get
    }
    val doc = Await.result(futureDoc, 5 seconds)
    println(doc.body().html().slice(0, 200))
  }
}
