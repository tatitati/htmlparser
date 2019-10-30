package crawler

import org.scalatest.FunSuite

class SpiderSpec extends FunSuite {
  val parsed1 = "http://monzo.com"
  val parsed2 = "http://www.monzo.com/blog/2017/01/13/monzo-extraordinary-ideas-board/"
  val noparsedA = "http://www.monzo.com/i/salary-sorter"
  val noparsedB = "http://www.monzo.com/careers"
  val noparsedC = "http://www.monzo.com/isa"
}
