package com.tfedorov.inteview

import org.junit.jupiter.api.Assertions._
import org.junit.jupiter.api.Test

import scala.collection.SortedSet

class DailyCodingTest {
  //
  //  private def recCheck(input: Seq[Int], neededSum: Int): Boolean = {
  //    if (input.isEmpty)
  //      return false
  //    recursiveCheck(input.head, input.tail)(neededSum)
  //  }
  //
  //  private def recursiveCheck(checkingEl: Int, checkingSeq: Seq[Int])(implicit neededSum: Int): Boolean =
  //    checkingSeq match {
  //      case Nil => false
  //      case checkingElMatch if checkingElMatch.exists(_ + checkingEl == neededSum) => true
  //      case noMatchCheckingEl => recursiveCheck(noMatchCheckingEl.head, noMatchCheckingEl.tail)
  //    }
  //
  //  private def combCheck(inputChecked: Seq[Int], neededSum: Int): Boolean = inputChecked.combinations(2).exists(_.sum == neededSum)
  //
  //  //https://dev.to/awwsmm/scala-daily-coding-problem-001-fi2
  //  @Test
  //  def task1Test(): Unit = {
  //
  //    val input = Seq(10, 15, 3, 7)
  //
  //    assertTrue(recCheck(input, 17))
  //    assertTrue(combCheck(input, 17))
  //    assertFalse(recCheck(input, 19))
  //    assertFalse(combCheck(input, 19))
  //  }
  //
  //  def check(input: Seq[Int]): Seq[Int] = {
  //    val indexedInput = input.zipWithIndex
  //    indexedInput.map { case (_, skipElIndex) =>
  //      indexedInput.filterNot(_._2 == skipElIndex).map(_._1).product
  //    }
  //  }
  //
  //  @Test
  //  def task2Test(): Unit = {
  //    val input1 = Seq(3, 2, 1)
  //    val input2 = Seq(1, 2, 3, 4, 5, 2)
  //
  //    val actualResult1 = check(input1)
  //    val actualResult2 = check(input2)
  //
  //    val r = input2.lift
  //    val r2 = input2.padTo(2, 2)
  //
  //    assertEquals(Seq(2, 3, 6), actualResult1)
  //    assertEquals(Seq(120, 60, 40, 30, 24), actualResult2)
  //  }
  //
  //  @Test
  //  def task2Test2(): Unit = {
  //    val input = Array(Array(1, 2, 3, 4, 5), Array(6, 7, 8, 9, 10))
  //    val actualResult = Array.ofDim[Integer](input(0).length, 2)
  //    for (i <- 0 to input.length - 1) {
  //      for (j <- 0 to input(0).length - 1) {
  //        actualResult(j)(i) = input(i)(j)
  //      }
  //    }
  //
  //    assertEquals(Array(Array(1, 6), Array(2, 7), Array(3, 8), Array(4, 9), Array(5, 10)), actualResult)
  //  }
  //
  //  @Test
  //  def task2Test3(): Unit = {
  //    val input = Array(Array(1, 2, 3, 4, 5), Array(6, 7, 8, 9, 10))
  //    val proxy: Seq[Seq[Int]] = input.map(_.toSeq).toSeq
  //    var output = scala.collection.mutable.ListBuffer.empty[scala.collection.mutable.ListBuffer[Int]]
  //    proxy.map { i: Seq[Int] =>
  //      var temp = Seq.empty
  //      i.map { j: Int =>
  //        temp
  //      }
  //    }
  //    assertEquals(Array(Array(1, 6), Array(2, 7), Array(3, 8), Array(4, 9), Array(5, 10)), proxy)
  //  }

  def searcher(arr: Array[Int]): Option[Int] = {
    /*
        val positives = arr.filter(_ > 0)
        if (positives.isEmpty)
          return None
        val minimumNum = positives.min
        if (minimumNum > 1)
          return Some(1)
    */
    val sorted = SortedSet[Int](arr: _*)

    (1 to Integer.MAX_VALUE).collectFirst { case cand if !sorted.contains(cand) => cand }
  }

  def searcherQuick(arr: Array[Int]): Int = {
    var sorted = SortedSet.empty[Int]
    arr.filter(_ > 0).foreach(sorted += _)

    val result = sorted.foldLeft(0) { (agg, el) =>
      if (agg + 1 == el) {
        el
      } else {
        return agg + 1
      }
    }
    result + 1
    //val result = sorted.sliding(2).collectFirst { case el2 if el2.last - el2.head > 1 => el2.head + 1 }
    //result.getOrElse(sorted.last + 1)
  }

  @Test
  def taskSerialiazble(): Unit = {
    val input = Array(3, 4, -1, 1)

    val actualResult = searcherQuick(input)

    assertEquals(2, actualResult)
  }

  @Test
  def taskSerialiazble2(): Unit = {
    val input = Array(1, 2, 0)

    val actualResult = searcherQuick(input)

    assertEquals(3, actualResult)
  }

  @Test
  def taskSerialiazble3(): Unit = {
    val input = Array(4, 2, 3)

    val actualResult = searcherQuick(input)

    assertEquals(1, actualResult)
  }

}
