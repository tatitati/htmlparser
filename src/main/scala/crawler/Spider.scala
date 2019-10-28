package crawler

import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global

object Spider {
  type Url = String
  type SetUrls = Set[String]
  type MapUrls = Map[Url, SetUrls]

//  def analyze(url: Url, acc: Map[Url, SetUrls] = Map()): Future[MapUrls] = {
//    acc.contains(url) match {
//      case true => Future{acc}
//      case false =>
//        val futureLinks: Future[SetUrls] = Downloader.parsePipeline(url)
//
//        futureLinks.map { (setUrls: SetUrls) =>
//            buildMap(url, setUrls, acc)
//        }
//    }
//  }


}
