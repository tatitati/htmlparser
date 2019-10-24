package crawler

import org.scalatest.FunSuite
import sttp.client.quick._
import scalaj.http._

class DownloaderSpec extends FunSuite {
  test("Can download url"){
//    val result = basicRequest.get(uri"https://google.com").send()
//    println(result.body)


    val response: HttpResponse[String] = Http("https://google.com").asString
    val body = response.body
    println(body)
  }
}
