package crawler

import org.jsoup.Jsoup
import org.jsoup.nodes.{Document, Element}
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future
import collection.JavaConverters._
import scala.collection.mutable

object Downloader {
  type Url = String
  type ListUrl = Set[String]
  type MapUrls = Map[Url, ListUrl]

  def parsePipeline(url: Url, acc: MapUrls = Map()): Future[ListUrl] = {
    getHtml(url).map{doc => findLinks(doc)}
  }

  def getHtml(url: Url): Future[Document] = {
    Future {Jsoup.connect(url).get}
  }

  def findLinks(doc: Document): ListUrl = {
    val body: Element = doc.body()
    val links: mutable.Buffer[Element] = body.select("a").asScala

    val whatever: mutable.Buffer[String] = for {
      link <- links
      if link.attr("href").startsWith("/")
    } yield "https://monzo.com" + link.attr("href")

    whatever.toSet
  }

  def buildMap(url: Url, links: ListUrl, acc: MapUrls): MapUrls = {
    acc + (url -> links)
  }
}
