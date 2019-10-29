package crawler

import crawler.Downloader.{MapUrls, SetUrls}
import org.jsoup.Jsoup
import org.scalatest.FunSuite
import scala.concurrent.Await
import scala.concurrent.duration._
import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global
import scala.util.{Failure, Success}

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

  test("I can download the body html and parse links ") {
    val futureLinks: Future[SetUrls] = Downloader
      .getHtml("http://monzo.com")
      .map{ doc => Downloader.findLinks(doc)}

    val links = Await.result(futureLinks, 5 seconds)
    assert(38 == links.size)
  }

  test("I can build a pipeline that automate the previous steps") {
    val futureLinks1: Future[MapUrls] = Downloader.parsePipeline("http://monzo.com")
    val futureLinks2: Future[MapUrls] = Downloader.parsePipeline("http://www.monzo.com/blog/2017/01/13/monzo-extraordinary-ideas-board/")

    val links1 = Await.result(futureLinks1, 5 seconds)
    val links2 = Await.result(futureLinks2, 5 seconds)

    assert(38 ==links1("http://monzo.com").size)
    assert(37 ==links2("http://www.monzo.com/blog/2017/01/13/monzo-extraordinary-ideas-board/").size)
  }
}
