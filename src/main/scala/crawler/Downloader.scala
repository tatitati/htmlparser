package crawler

import org.jsoup.Jsoup
import org.jsoup.nodes.{Document, Element}
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future
import collection.JavaConverters._
import scala.collection.mutable

object Downloader {
  type Url = String
  type SetUrls = Set[String]
  type MapUrls = Map[Url, SetUrls]

  def parseUrl(url: Url): Future[SetUrls] = {
    getHtml(url)
      .map{doc => findLinks(doc)}
  }

  def parseUrlSerial(url: Url): SetUrls = {
      findLinks(getHtmlSerial(url)).filter(!_.endsWith("pdf"))
  }

  def getHtml(url: Url): Future[Document] = {
    Future {Jsoup.connect(url).get}
  }

  def getHtmlSerial(url: Url): Document = {
    Jsoup.connect(url).get
  }

  def findLinks(doc: Document): SetUrls = {
    val body: Element = doc.body()
    val links: mutable.Buffer[Element] = body.select("a").asScala

    val whatever: mutable.Buffer[String] = for {
      link <- links
      if link.attr("href").startsWith("/")
    } yield Baseurl.url + link.attr("href")

    whatever.toSet
  }
}
