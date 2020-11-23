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
    val r2 = input2.padTo(2,2)

    assertEquals(Seq(2, 3, 6), actualResult1)
    assertEquals(Seq(120, 60, 40, 30, 24), actualResult2)
  }
}
