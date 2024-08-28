package com.tfedorov.inteview

import org.scalatest.FlatSpec

object Solution extends FlatSpec {

  import scala.collection.mutable

  /**
   * Merge Overlapping Ranges
   *
   * Given a set of inclusive ranges [Int, Int] (e.g. [1, 2] is overlapping with [2, 3]),
   * merge the ranges into a (potentially shorter) set of non-overlapping ranges.
   */

  case class Range(start: Int, end: Int)

  class MergedRange(var start: Int, var end: Int) extends Comparable[MergedRange] {
    def isOverlapped(another: MergedRange): Boolean = {
      // 0, 2 & 2, 3
      if (this.end <= another.end && this.start >= another.start)
        return true
      // 2, 3 & 0, 2
      if (another.end <= this.end && another.start <= this.start)
        return true
      // 0,4 & 2,3
      if (this.end > another.end && this.start <= another.end)
        return true
      // 2,3 & 0,4
      if (another.end >= this.end && another.start <= this.end)
        return true
      //add conditions
      false
    }

    def merge(another: MergedRange): Unit = {
      start = math.min(this.start, another.start)
      end = math.max(this.end, another.end)
    }

    def toRange: Range = Range(start, end)

    override def compareTo(o: MergedRange): Int = start - o.start
  }

  def merge(ranges: Set[Range]): Set[Range] = {
    val candidate2Merge = ranges.map(r => new MergedRange(r.start, r.end)).toSeq.sorted

    val result = candidate2Merge.foldLeft(collection.mutable.Set.empty[MergedRange]) { (accumulator: mutable.Set[MergedRange], mergeCandidate: MergedRange) =>
      val overlapped = accumulator.filter(_.isOverlapped(mergeCandidate))
      overlapped.foreach(_.merge(mergeCandidate))
      if (overlapped.isEmpty)
        accumulator.add(mergeCandidate)
      accumulator
    }
    result.map(_.toRange).toSet
  }

  // tests
  def test(p: Boolean) = println(if (p) "Success" else "Failure")

  "test function " should "return" in {
    test(merge(Set(Range(0, 2), Range(2, 3))) == Set(Range(0, 3)))
    test(merge(Set(Range(0, 4))).toSet == Set(Range(0, 4)))
    test(merge(Set(Range(0, 4))).toSet == Set(Range(0, 4)))

    test(merge(Set(Range(0, 4), Range(2, 6), Range(8, 10))) == Set(Range(0, 6), Range(8, 10)))
    test(merge(Set(Range(2, 3), Range(0, 4), Range(8, 10))) == Set(Range(0, 4), Range(8, 10)))

    test(merge(Set(Range(2, 3), Range(0, 4), Range(8, 10), Range(9, 12))) == Set(Range(0, 4), Range(8, 12)))
  }

}
