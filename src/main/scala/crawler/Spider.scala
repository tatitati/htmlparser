package crawler

import crawler.Downloader.{MapUrls, SetUrls, Url}
import scala.collection.immutable.Queue

object Spider {
  def main(args: Array[String]): Unit = {
    scan(Queue(Baseurl.url))
  }

  def scan(queue: Queue[Url], mapProcessed: MapUrls = Map(), discoveredUrls: SetUrls = Set()): MapUrls = {

    queue.isEmpty match {
      case true => mapProcessed
      case false =>
        val (url, q) = queue.dequeue

        if(!mapProcessed.contains(url)) {
          val parsedUrls: SetUrls = Downloader.parseUrlSerial(url)
          val newDiscoveredUrls: SetUrls = parsedUrls diff discoveredUrls
          val newAll: SetUrls = discoveredUrls ++ newDiscoveredUrls

          scan(q ++ newDiscoveredUrls, mapProcessed ++ Map(url -> parsedUrls), newAll)
        } else {
          scan(q, mapProcessed, discoveredUrls)
        }
    }

    println("RESULT:")
    for((k, v) <- mapProcessed){
      println("URL:" + k)
      println(v.foreach{_ => println("\t\t" + _)})
      println("################")
    }
    mapProcessed
  }
}
