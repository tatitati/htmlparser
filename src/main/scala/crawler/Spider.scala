package crawler

import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global

class Spider {
  type Url = String
  type ListUrl = Set[String]

//  def analyze(url: Url, acc: Map[Url, ListUrl]): Future[Map[Url, ListUrl]] = {
//    acc.contains(url) match {
//      case true => Future{acc}
//      case false =>
//        val links: Future[ListUrl] = Downloader.parsePipeline(url)
//        links.flatMap{ analyze(_) }
//    }
//  }
}
