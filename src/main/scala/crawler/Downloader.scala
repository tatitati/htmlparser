package crawler

import org.jsoup.Jsoup
import org.jsoup.nodes.{Document, Element}
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future
import collection.JavaConverters._
import scala.collection.mutable

object Downloader {

  def parsePipeline(url: String): Future[Set[String]] = {
    getHtml(url).map{doc => findLinks(doc)}
  }

  def getHtml(url: String): Future[Document] = {
    Future {Jsoup.connect(url).get}
  }

  def findLinks(doc: Document): Set[String] = {
    val body: Element = doc.body()
    val links: mutable.Buffer[Element] = body.select("a").asScala

    val whatever: mutable.Buffer[String] = for {
      link <- links
      if link.attr("href").startsWith("/")
    } yield "https://monzo.com" + link.attr("href")

    whatever.toSet
  }
}
