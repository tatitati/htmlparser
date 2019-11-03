package crawler

import com.sun.org.apache.xalan.internal.utils.XMLSecurityManager.Limit
import crawler.Downloader.{MapUrls, SetUrls, Url}

import scala.collection.immutable.Queue
import scala.concurrent.Future
import scala.concurrent.{Await, Future}
import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.duration._

object Spider {
  def main(args: Array[String]): Unit = {
    val result = scan(Set(Baseurl.url), limitDeepLevel = 2)

    Thread.sleep(160000)
  }

  def scan(urlsToExplore: SetUrls = Set(), limitDeepLevel: Int, exploredDeepLevel: Int = 0, mapProcessed: MapUrls = Map(), exploredUrls: SetUrls = Set()): Unit = {
    val futureUrls: Future[MapUrls] = Downloader.parseBunchUrls(urlsToExplore)
    futureUrls foreach { (m: MapUrls) =>
      val newExploredUrls: SetUrls = exploredUrls ++ urlsToExplore
      val parsedUrls: SetUrls = m.values.toSet.flatten
      val newUnexploredUrls: SetUrls = parsedUrls diff newExploredUrls
      val newMapProcessed = mapProcessed ++ m

      if(exploredDeepLevel != limitDeepLevel) {

        println("LEVEL: " + exploredDeepLevel)
        println("==============")
        for((url,listUrls) <- m) {
          println("LEVEL: " + exploredDeepLevel + " => " + url)
          for(parsed <- listUrls) {
            println("\t\t" + parsed)
          }
        }

        scan(newUnexploredUrls, limitDeepLevel, exploredDeepLevel + 1, newMapProcessed, newExploredUrls)
      } else {
        println("Done.")
      }
    }
  }
}
