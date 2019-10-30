package crawler

import crawler.Downloader.{MapUrls, SetUrls, Url}

import scala.concurrent.{Await, Future}
import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global
import cats._
import cats.Functor
import cats.data.Nested
import cats.implicits._

import scala.collection.immutable.Queue
import scala.concurrent.duration._

object Spider {
  def main(args: Array[String]): Unit = {
    val q = Queue(Baseurl.url)

    scan(q)
    Thread.sleep(40000)
  }

  def scan(queue: Queue[Url], mapUrls: MapUrls = Map(), all: SetUrls = Set()): MapUrls = {

    println("QUEUE: " + queue.length)

    while(!queue.isEmpty) {
      val (url, q) = queue.dequeue

      if(!mapUrls.contains(url)) {
        val parsedUrls: SetUrls = Downloader.parseUrlSerial(url)
        val newAll: SetUrls = all ++ parsedUrls

        val parsedNotEnqueued: SetUrls = newAll diff parsedUrls //problem here, is empty at the start
        println(newAll)
        println(parsedNotEnqueued)
        Thread.sleep(10000)
        scan(q ++ parsedNotEnqueued, mapUrls ++ Map(url -> parsedUrls), newAll)

      } else {
        println("Already processed: " + url)
        scan(q, mapUrls, all)
      }
    }

    println("RESULT:")
    for((k, v) <- mapUrls){
      println("URL:" + k)
      println(v.foreach{_ => println("\t\t" + _)})
      println("################")
    }
    mapUrls
  }
}
