package crawler

import crawler.Downloader.{MapUrls, SetUrls, Url}
import scala.collection.immutable.Queue
import scala.concurrent.Future
import scala.concurrent.{Await, Future}
import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.duration._

object Spider {
  def main(args: Array[String]): Unit = {
    val result = scan(Set(Baseurl.url))

    Thread.sleep(80000)
  }

  def scan(unexploredUrls: SetUrls = Set(), mapProcessed: MapUrls = Map(), discoveredUrls: SetUrls = Set()): Future[MapUrls] = {
    val futureUrls: Future[MapUrls] = Downloader.parseBunchUrls(unexploredUrls)

    futureUrls foreach { (m: MapUrls) =>
      val parsedUrls: SetUrls = m.values.toSet.flatten
      val newUnexploredUrls: SetUrls = parsedUrls diff discoveredUrls
      val newDiscoveredUrls: SetUrls = discoveredUrls ++ newUnexploredUrls

      if(!newUnexploredUrls.isEmpty) {
        scan(newUnexploredUrls, mapProcessed ++ m, newDiscoveredUrls)
      }
    }
  }
}
