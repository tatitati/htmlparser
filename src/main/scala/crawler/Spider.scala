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

  def main(args: Array[String]): Unit = {
    controller(Baseurl.url)
  }

  def controller(url: Url, visitedLinks: SetUrls = Set()): Unit = {
    val futureMap: Future[MapUrls] = analyze(url)

    futureMap foreach { subMap: MapUrls =>
      println(url + " | " + visitedLinks.size)
      val parsedLinks = subMap(url)
      val newlinks = visitedLinks ++ parsedLinks

      for {
        link <- newlinks
        if !visitedLinks.contains(link)
      } yield controller(link, newlinks)
    }

    Thread.sleep(6000)
  }

  def analyze(url: Url): Future[MapUrls] = {
    Downloader.parsePipeline(url)
  }
}
