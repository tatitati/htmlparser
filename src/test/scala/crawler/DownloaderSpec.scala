package crawler

import org.scalatest.FunSuite
import sttp.client.quick._
import scalaj.http._
import scala.concurrent.Await
import scala.concurrent.duration._
import scala.concurrent.Future
import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global

class DownloaderSpec extends FunSuite {
  test("Can download url"){
    val futureHtml: Future[HttpResponse[String]] = Future{
      Http("https://elpais.com").asString
    }

    val html = Await.result(futureHtml, 5 seconds)
    println(html.body.slice(0, 30))
  }
}
