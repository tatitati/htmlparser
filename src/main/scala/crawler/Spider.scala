package crawler

import crawler.Downloader.{MapUrls, SetUrls, Url}
import scala.annotation.tailrec
import scala.concurrent.{Await, Future}
import scala.concurrent.ExecutionContext.Implicits.global
import scala.util.{Failure, Success}
import scala.concurrent.duration._

object Spider {

  def main(args: Array[String]): Unit = {
    val futureMap: Future[MapUrls] = analyze(Baseurl.url)

    futureMap foreach { mapped =>
      println("\n\n\nFINAL:\n" + mapped)
    }

    Thread.sleep(50000)
  }

  def analyze(url: Url, completedNodes: MapUrls = Map(), requested: List[String] = List()): Future[MapUrls] = {
    if(!completedNodes.contains(url)) {
      val futureMap: Future[MapUrls] = Downloader.parsePipeline(url)

      futureMap foreach { subMap =>
        val foundLinks = subMap(url)

        foundLinks.map { link =>
          analyze(link, completedNodes ++ subMap)
        }
      }

      futureMap
    } else Future{completedNodes}
  }
}
