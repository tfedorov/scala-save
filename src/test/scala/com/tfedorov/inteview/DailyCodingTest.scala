package com.tfedorov.inteview

import org.junit.jupiter.api.Assertions._
import org.junit.jupiter.api.Test

class DailyCodingTest {

  private def recCheck(input: Seq[Int], neededSum: Int): Boolean = {
    if (input.isEmpty)
      return false
    recursiveCheck(input.head, input.tail)(neededSum)
  }

  private def recursiveCheck(checkingEl: Int, checkingSeq: Seq[Int])(implicit neededSum: Int): Boolean =
    checkingSeq match {
      case Nil => false
      case checkingElMatch if checkingElMatch.exists(_ + checkingEl == neededSum) => true
      case noMatchCheckingEl => recursiveCheck(noMatchCheckingEl.head, noMatchCheckingEl.tail)
    }

  private def combCheck(inputChecked: Seq[Int], neededSum: Int): Boolean = inputChecked.combinations(2).exists(_.sum == neededSum)


  @Test
  def taks1Test(): Unit = {

    val input = Seq(10, 15, 3, 7)

    assertTrue(recCheck(input, 17))
    assertTrue(combCheck(input, 17))
    assertFalse(recCheck(input, 19))
    assertFalse(combCheck(input, 19))
  }

  def check(input: Seq[Int]): Seq[Int] = {
    val indexedInput = input.zipWithIndex
    indexedInput.map { case (_, skipElIndex) =>
      indexedInput.filterNot(_._2 == skipElIndex).map(_._1).product
    }
  }

  @Test
  def taks2Test(): Unit = {
    val input1 = Seq(3, 2, 1)
    val input2 = Seq(1, 2, 3, 4, 5, 2)

    val actualResult1 = check(input1)
    val actualResult2 = check(input2)

    val r = input2.lift
    val r2 = input2.padTo(2, 2)

    assertEquals(Seq(2, 3, 6), actualResult1)
    assertEquals(Seq(120, 60, 40, 30, 24), actualResult2)
  }


  @Test
  def taks2Test2(): Unit = {
    val input = Array(Array(1, 2, 3, 4, 5), Array(6, 7, 8, 9, 10))
    val actualResult = Array.ofDim[Integer](input(0).length, 2)
    for (i <- 0 to input.length - 1) {
      for (j <- 0 to input(0).length - 1) {
        actualResult(j)(i) = input(i)(j)
      }
    }

    assertEquals(Array(Array(1, 6), Array(2, 7), Array(3, 8), Array(4, 9), Array(5, 10)), actualResult)

  }

  @Test
  def taks2Test3(): Unit = {
    val input = Array(Array(1, 2, 3, 4, 5), Array(6, 7, 8, 9, 10))
    val proxy: Seq[Seq[Int]] = input.map(_.toSeq).toSeq
    var output = scala.collection.mutable.ListBuffer.empty[scala.collection.mutable.ListBuffer[Int]]
    proxy.map{i: Seq[Int] =>
      var temp = Seq.empty
      i.map{j: Int =>
        temp
      }
    }

    assertEquals(Array(Array(1, 6), Array(2, 7), Array(3, 8), Array(4, 9), Array(5, 10)), proxy)

  }
}
