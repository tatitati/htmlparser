package crawler

import crawler.Downloader.{MapUrls, SetUrls}
import org.jsoup.Jsoup
import org.scalatest.FunSuite

import scala.collection.immutable.Queue
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

  test("asdfadsf"){
    val a = Set("a", "b", "c")
    val q = Queue()
    val q1 = q.enqueue("AAA")
    val q2 = q1 ++ a
    val (value1, q3) = q2.dequeue
    println(value1)
    println(q3.dequeue)

  }

  test("diff"){
    val a = Set("a", "b", "c", "d", "f")
    val b = Set("a", "b", "d")
    val empty: Set[String] = Set()

    val c = a diff b
    val d = b diff a
    val e = empty diff a

    println(c) // Set(f, c)
    println(d) // Set()
    println(e) // Set()
  }

  test("set"){
    val a = Set("a", "b", "c", "d", "f")
    val b = Set("a", "b", "d")

    println(a ++ b) // Set(f, a, b, c, d)
  }
}
