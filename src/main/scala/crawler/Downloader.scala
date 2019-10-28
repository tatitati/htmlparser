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

  def parsePipeline(url: Url): Future[MapUrls] = {
    getHtml(url)
      .map{doc => findLinks(doc)}
      .map{links => buildMap(url, links)}
  }

  def getHtml(url: Url): Future[Document] = {
    Future {Jsoup.connect(url).get}
  }

  def findLinks(doc: Document): SetUrls = {
    val body: Element = doc.body()
    val links: mutable.Buffer[Element] = body.select("a").asScala

    val whatever: mutable.Buffer[String] = for {
      link <- links
      if link.attr("href").startsWith("/")
    } yield "https://monzo.com" + link.attr("href")

    whatever.toSet
  }

  def buildMap(url: Url, links: SetUrls): MapUrls = Map(url -> links)
}
