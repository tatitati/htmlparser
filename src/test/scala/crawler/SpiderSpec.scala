package crawler

import org.scalatest.FunSuite

class SpiderSpec extends FunSuite {
  val parsed1 = "http://monzo.com"
  val parsed2 = "http://www.monzo.com/blog/2017/01/13/monzo-extraordinary-ideas-board/"
  val noparsedA = "http://www.monzo.com/i/salary-sorter"
  val noparsedB = "http://www.monzo.com/careers"
  val noparsedC = "http://www.monzo.com/isa"

  test("Given a list of urls parsed, I can get the ones that need still to be parsed"){
    val should1 = Spider.shouldIVisit(
      parsed1,
      Map(
        parsed1 -> Set(noparsedA, parsed2, noparsedB, parsed1),
        parsed2 -> Set(noparsedA, parsed2, noparsedB, parsed1))
    )

    val should2 = Spider.shouldIVisit(
      noparsedA,
      Map(
        parsed1 -> Set(noparsedA, parsed2, noparsedB, parsed1),
        parsed2 -> Set(noparsedA, parsed2, noparsedB, parsed1))
    )

    assert(false == should1)
    assert(true == should2)
  }
}
