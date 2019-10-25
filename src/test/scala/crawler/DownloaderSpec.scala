package crawler

import org.jsoup.Jsoup
import org.jsoup.nodes.{Document, Element}
import org.scalatest.FunSuite
import scalaj.http._

import scala.concurrent.Await
import scala.concurrent.duration._
import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global
import collection.JavaConverters._
import scala.collection.mutable
import scala.collection.mutable.ArrayBuffer

class DownloaderSpec extends FunSuite {
  test("I can get the the body of a url"){
    val doc = Jsoup.connect("http://monzo.com/").get
    println(doc.body().html().slice(0, 20))
  }

  test("I can use futures to do it async") {
    val futureDoc = Downloader.getHtml("http://monzo.com")
    val doc = Await.result(futureDoc, 5 seconds)
    println(doc.body().html().slice(0, 20))
  }

  test("I can parse links ") {
    val futureDoc: Future[Set[String]] = Downloader.getHtml("http://monzo.com")
      .map{ doc => Downloader.parseLinks(doc)}
  }

  test("whole pipeline") {
    val futureDoc = Downloader.pipeline("http://monzo.com")
    val doc = Await.result(futureDoc, 5 seconds)
    println(doc)
  }
}
