package crawler

import crawler.Downloader.{MapUrls, SetUrls, Url}

import scala.concurrent.{Await, Future}
import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global
import cats._
import cats.Functor
import cats.data.Nested
import cats.implicits._
import scala.concurrent.duration._

object Spider {
  def shouldIVisit(link: Url, mapUrls: MapUrls): Boolean = {
    !mapUrls.contains(link)
  }


  def main(args: Array[String]): Unit = {
    start(Baseurl.url)
  }

  def start(url: Url, mapUrls: MapUrls = Map()): Unit = {
    val futureMap: Future[SetUrls] = analyze(url)

    futureMap foreach { urls: SetUrls =>
      val newmap = mapUrls ++ Map(url -> urls)

      for(url <- urls){
        if(shouldIVisit(url, newmap)){
          start(url, newmap)
        }
      }
    }
    
    println("\n\n")
    println(mapUrls.keys)
  }

  def analyze(url: Url): Future[SetUrls] = {
    Downloader.parsePipeline2(url)
  }
}
