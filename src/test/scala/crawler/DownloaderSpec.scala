package crawler

import org.jsoup.Jsoup
import org.scalatest.FunSuite
import scala.concurrent.Await
import scala.concurrent.duration._
import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global

class DownloaderSpec extends FunSuite {
  test("Can get html body WITHOUT using Futures") {
    val doc = Jsoup.connect("http://monzo.com/").get
    println(doc.body().html().slice(0, 20))
  }

  test("Can get html body using Futures") {
    val futureDoc = Downloader.getHtml("http://monzo.com")
    val doc = Await.result(futureDoc, 5 seconds)
    println(doc.body().html().slice(0, 20))
  }

  test("I can parse links ") {
    val futureLinks: Future[Set[String]] = Downloader
      .getHtml("http://monzo.com")
      .map{ doc => Downloader.findLinks(doc)}

    val links = Await.result(futureLinks, 5 seconds)
    assert(38 == links.size)
  }

  test("I can build a pipeline that automate the previous process") {
    val futureLinks = Downloader.parsePipeline("http://monzo.com")

    val links = Await.result(futureLinks, 5 seconds)
    assert(38 == links.size)
  }
}
