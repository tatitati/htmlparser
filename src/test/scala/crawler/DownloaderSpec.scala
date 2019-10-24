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
//    val result = basicRequest.get(uri"https://google.com").send()
//    println(result.body)

    val futureBody = Future{
      val response: HttpResponse[String] = Http("https://elpais.com").asString
      response.body
    }

    val body = Await.result(futureBody, 5 seconds)
    println(body)
  }
}
