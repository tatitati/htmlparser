package crawler

import crawler.Downloader.{MapUrls, SetUrls, Url}

import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global
import cats._
import cats.Functor
import cats.data.Nested
import cats.implicits._

object Spider {
  def shouldIVisit(link: Url, mapUrls: MapUrls): Boolean = {
    !mapUrls.contains(link)
  }


  def main(args: Array[String]): Unit = {
    controller(Baseurl.url)
  }

  def controller(url: Url, visitedLinks: SetUrls = Set(), mapUrls:MapUrls = Map()): Unit = {
    val futureMap: Future[MapUrls] = analyze(url)

    futureMap foreach { mapUrls: MapUrls =>
      println(url + " | " + visitedLinks.size)
      val parsedLinks = mapUrls(url)
      val newlinks = visitedLinks ++ parsedLinks
    }

    Thread.sleep(6000)
  }

  def analyze(url: Url): Future[MapUrls] = {
    Downloader.parsePipeline(url)
  }
}
