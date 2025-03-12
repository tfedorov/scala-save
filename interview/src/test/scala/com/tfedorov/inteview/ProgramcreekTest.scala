package com.tfedorov.inteview

import org.junit.jupiter.api.Assertions.{assertEquals, assertFalse, assertTrue}
import org.junit.jupiter.api.Test

import scala.annotation.tailrec
import scala.collection.SortedSet

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

  def isComplete(arr: Seq[Int], number: Int): Option[Int] = {
    val allSumsS = allSums(arr)
    (1 to number).foreach { cand =>
      if (!allSumsS.contains(cand))
        return Some(cand)
    }
    None
  }


  private def allSums(arr: Seq[Int]): SortedSet[Int] = {
    var allSums = SortedSet.empty[Int]
    for (sep <- 0 to arr.length) {
      val (s1, s2) = arr.splitAt(sep)
      val r = (s2 ++ s1).inits.map(_.sum).toSet
      allSums ++= r
    }
    allSums
  }

  private def findPatch(agg: Seq[Int])(implicit arr: Seq[Int], sum: Int): Seq[Int] = {
    if (isComplete(arr, sum).isEmpty)
      return Seq.empty
    val r1 = (1 to sum).collectFirst { case i if isComplete(arr :+ i, sum).isEmpty => i :: Nil }

    if (r1.isDefined)
      return r1.get
    val r2 = findPatchR((1 to sum), Nil)
    r2
  }

  private def findPatchR(range: Range, agg: Seq[Int])(implicit arr: Seq[Int], sum: Int): Seq[Int] = {
    val r = range.collectFirst { case i if isComplete(arr :+ i, sum).isEmpty => i :: Nil }
    r.getOrElse(Seq.empty)
  }

  @Test
  def patchingArray(): Unit = {
    val inputRange = Seq(1, 3)
    val number = 5

    val actual = findPatch(Nil)(inputRange, number)

    assertEquals(Seq(1), actual)
  }


  @Test
  def patchingArray2(): Unit = {
    val inputRange = Seq(1, 4, 10)
    val number = 24

    val actual = findPatch(Nil)(inputRange, number)

    assertEquals(Seq(9), actual)
  }

  @Test
  def isComplete1(): Unit = {
    val inputRange = Seq(1, 4, 10, 9)
    val number = 24

    val actual = isComplete(inputRange, number)

    assertEquals(Some(2), actual)
  }

  @Test
  def isComplete2(): Unit = {
    val inputRange = Seq(1, 3, 1)
    val number = 5

    val actual = isComplete(inputRange, number)

    assertEquals(None, actual)
  }
}
