package crawler

import org.jsoup.Jsoup
import org.scalatest.FunSuite

class DownloaderSpec extends FunSuite {
  test("Can get html body WITHOUT using Futures") {
    val doc = Jsoup.connect("http://monzo.com/").get
    println(doc.body().html().slice(0, 20))
  }

  test("Can get html body using Futures") {
    val doc = Downloader.getHtml("http://monzo.com")
    println(doc.body().html().slice(0, 20))
  }

  test("how behaves diff?"){
    val a = Set("a", "b", "c", "d", "f")
    val b = Set("a", "b", "d")
    val empty: Set[String] = Set()

    val ab = a diff b
    val ba = b diff a
    val emptyA = empty diff a

    println(ab) // Set(f, c)
    println(ba) // Set()
    println(emptyA) // Set()
  }

  test("++ with set avoid duplicates when joining"){
    val a = Set("a", "b", "c", "d", "f")
    val b = Set("a", "b", "d")

    println(a ++ b) // Set(f, a, b, c, d)
  }

  test("extract all values from a map") {
    val a = Map(
      "aa" -> Set("a", "b"),
      "bb" -> Set("c", "d")
    )

    assert(Set("a", "b", "c", "d") === a.values.toSet.flatten)
  }
}
