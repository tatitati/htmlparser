package crawler

import crawler.Downloader.{MapUrls, SetUrls, Url}
import scala.concurrent.{Await, Future}
import scala.concurrent.ExecutionContext.Implicits.global
import scala.util.{Failure, Success}
import scala.concurrent.duration._

object Spider {

  def main(args: Array[String]): Unit = {
    val futureMap = analyze("http://monzo.com")
    val linkMaps = Await.result(futureMap, 50 seconds)
    Thread.sleep(15000)
    println(linkMaps)
  }


  def analyze(url: Url, visitedMap: MapUrls = Map()): Future[MapUrls] = {
    if(!visitedMap.contains(url)) {
      val futureMap: Future[MapUrls] = Downloader.parsePipeline(url)

      futureMap onComplete {
        case Success(subMap) =>
//          println("====>for url:  " + subMap.toString())
//          println("====>whole map:  " + visitedMap.keys.toString())
//          println("\n\n\n\n\n\n\n")

          val newVisitedMap = visitedMap ++ subMap
          val discoveredLinks = subMap(url)
          discoveredLinks.map { parsedLink => analyze(parsedLink, newVisitedMap)}
        case Failure(_) => Future{visitedMap}
      }

      futureMap
    } else Future{visitedMap}
  }
}
