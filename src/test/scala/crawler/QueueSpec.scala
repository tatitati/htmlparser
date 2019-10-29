package crawler

import org.scalatest.FunSuite
import scala.collection.mutable.Queue

class QueueSpec extends FunSuite {
  test("check queue in scala") {
    var q = Queue[String]()
    q += "apple"
    q += "coco"
    q += "banana"


    assert("apple" == q.dequeue())
    assert("coco" == q.dequeue())
    assert("banana" == q.dequeue())
  }
}
