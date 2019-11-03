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

  def parseUrlSerial(url: Url): SetUrls = {
    findLinks(getHtmlSerial(url)).filter(!_.endsWith("pdf"))
  }

  //def parseBunchUrls(urls: SetUrls): Future[Set[SetUrls]] = {
  //  val futs: Future[Set[Future[SetUrls]]] = Future{
  //    urls.map{parseParallel(_)}
  //  }
  //
  //  futs.flatMap{ (s: Set[Future[SetUrls]]) =>
  //    Future.sequence(s)
  //  }
  //}

  def parseBunchUrls(urls: SetUrls): Future[MapUrls] = {
      val whatever: Set[Future[SetUrls]] = urls.map{ url => parseParallel(url)}
      val futs: Future[Set[SetUrls]] = Future.sequence(whatever)

      futs.map{f =>
        (urls zip f).toMap
      }
  }

  def parseParallel(url: Url): Future[SetUrls] = {
    Future{
      findLinks(getHtmlSerial(url)).filter(!_.endsWith("pdf"))
    }
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
