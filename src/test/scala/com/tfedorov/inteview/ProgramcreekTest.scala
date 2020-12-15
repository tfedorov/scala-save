package com.tfedorov.inteview

import org.junit.jupiter.api.Assertions.{assertEquals, assertFalse, assertTrue}
import org.junit.jupiter.api.Test

import scala.annotation.tailrec

//https://www.programcreek.com/2012/11/top-10-algorithms-for-coding-interview/
class ProgramcreekTest {

  def isIso(inputs1: String, inputs2: String): Boolean = {
    if (inputs1.length != inputs2.length)
      return false
    val map1 = inputs1.zipWithIndex.groupBy(_._1)
    val map2 = inputs2.zipWithIndex.groupBy(_._1)
    val indexed1 = map1.values.map(_.map(_._2)).toSeq
    val indexed2 = map2.values.map(_.map(_._2)).toSeq
    indexed1.foreach { el =>
      if (!indexed2.contains(el))
        return false
    }
    true
  }

  def isIso2(inputs1: String, inputs2: String): Boolean = {
    var dictionary = Map.empty[Char, Char]
    inputs1.corresponds(inputs2) { (input1Char, input2Char) =>
      dictionary.get(input1Char)
        .map(_ == input2Char)
        .getOrElse {
          dictionary += (input1Char -> input2Char)
          true
        }
    }
  }

  @Test
  def notIso2(): Unit = {
    val inputs1 = "dda"
    val inputs2 = "egg"

    assertFalse(isIso2(inputs1, inputs2))
  }

  @Test
  def notIso(): Unit = {
    val inputs1 = "ada"
    val inputs2 = "egg"


    assertFalse(isIso2(inputs1, inputs2))
  }

  @Test
  def isIso(): Unit = {
    val inputs1 = "add"
    val inputs2 = "egg"


    assertTrue(isIso2(inputs1, inputs2))
  }

  @Test
  def taskLeapYears(): Unit = {
    val inputs = (1 to 2100).grouped(4).toSeq
    val indexOf2020 = inputs.collectFirst { case el4 if el4.contains(2020) => el4.indexOf(2020) }.get

    val leapYears = inputs.map(_ (indexOf2020)).toSet

    assertTrue(leapYears.contains(2012))

    def calc(year: Int) = (2020 - year) % 4 == 0

    assertTrue(calc(2012))
    assertFalse(calc(2013))
  }

  //https://www.programcreek.com/2015/01/leetcode-patching-array-java/

  def isComplete(arr: Seq[Int], number: Int): Boolean = {
    for (sum <- 1 to number) {
      if (!checkIfSumIs(arr, sum))
        return false
    }
    true
  }

  /*  def findPatch(arr: Seq[Int], number: Int): Seq[Int] = {

      if (isComplete(arr, number))
        return Seq.empty
      for (patch <- 1 to number) {
        if (isComplete(arr :+ patch, number))
          return Seq(patch)
      }
      Seq.empty
    }*/


  private def checkIfSumIs(arr: Seq[Int], sum: Int): Boolean = {
    for (ch <- 0 to arr.length - 1) {
      val split: (Seq[Int], Seq[Int]) = arr.splitAt(ch)
      val newArr = split._2 ++ split._1
      if (!newArr.inits.exists(_.sum == sum))
        return false
    }
    true
  }

  def findPatchR(agg: Seq[Int], alreadyFoundSize: Option[Int])(implicit arr: Seq[Int], number: Int): Seq[Int] = {
    var lessElements = Seq.empty[Int]

    if (isComplete(arr ++ agg, number))
      return agg

    if (alreadyFoundSize.exists(_ < agg.size) || agg.size > 2)
      return Seq.empty

    for (patch <- 1 to number) {
      val aggPlus1 = arr ++ agg :+ patch
      if (isComplete(aggPlus1, number))
        return arr ++ agg :+ patch
    }
    for (patch <- 1 to number) {
      val bestEl = findPatchR(agg :+ patch, Some(lessElements.size).filter(_ > 0))
      if (lessElements.isEmpty)
        lessElements = bestEl
      else if (bestEl.nonEmpty && lessElements.size > bestEl.size)
        lessElements = bestEl
    }
    lessElements
  }


  @Test
  def patchingArray(): Unit = {
    val inputRange = Seq(1, 3)
    val number = 5

    val actual = findPatchR(Seq.empty, None)(inputRange, number)

    assertEquals(Seq(1), actual)
  }

  @Test
  def patchingArray2(): Unit = {
    val inputRange = Seq(1, 4, 10)
    val number = 24

    val actual = findPatchR(Seq.empty, None)(inputRange, number)

    assertEquals(Seq(9), actual)
  }

  @Test
  def check(): Unit = {
    val inputRange = Seq(1, 4, 10, 9)
    val number = 24

    val actual = isComplete(inputRange, number)

    assertEquals(false, actual)
  }

  @Test
  def check2(): Unit = {
    val inputRange = Seq(1, 3, 1)
    val number = 5

    val actual = isComplete(inputRange, number)

    assertEquals(true, actual)
  }
}
