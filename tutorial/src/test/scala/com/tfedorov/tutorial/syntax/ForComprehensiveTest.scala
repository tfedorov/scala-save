package com.tfedorov.tutorial.syntax

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

import scala.collection.immutable

class ForComprehensiveTest {

  /*
  see https://gist.github.com/loicdescotte/4044169

  A for-comprehension is syntactic sugar for map, flatMap and filter operations on collections.

    The general form is for (s) yield e
    • s is a sequence of generators and filters
    • p <- e is a generator
    • if f is a filter
    • If there are several generators (equivalent of a nested loop), the last generator varies faster than the first
    • You can use { s } instead of ( s ) if you want to use multiple lines without requiring semicolons
    • e is an element of the resulting collection
   */

  @Test
  def flatUnions(): Unit = {
    val abc = "A" :: "B" :: "C" :: Nil
    val deF = "1" :: "2" :: "3" :: Nil

    val actualResult: Seq[(String, String)] = for {
      a: String <- abc
      d: String <- deF
    }
    yield (a, d)

    val actualResultFlat: Seq[(String, String)] = abc.flatMap(abcEl => deF.map(defEl => (abcEl, defEl)))
    val expected =
      ("A", "1") :: ("A", "2") :: ("A", "3") ::
        ("B", "1") :: ("B", "2") :: ("B", "3") ::
        ("C", "1") :: ("C", "2") :: ("C", "3") :: Nil
    assertEquals(expected, actualResult)
    assertEquals(expected, actualResultFlat)
  }

  @Test
  def flatUnionsMultiplier(): Unit = {
    val abc = "A" :: "B" :: "C" :: Nil
    val multiplier = 2 :: 3 :: Nil

    val actualResult = for {
      a: String <- abc
      d: Int <- multiplier
    }
    yield a * d
    val actualResultFlat = abc.flatMap(abcEl => multiplier.map(abcEl * _))

    val expected = "AA" :: "AAA" :: "BB" :: "BBB" :: "CC" :: "CCC" :: Nil
    assertEquals(expected, actualResult)
    assertEquals(expected, actualResultFlat)
  }

  @Test
  def flattenFlatten(): Unit = {
    val abcd: Seq[List[String]] = List("A", "B") :: List("C", "1") :: Nil
    val efgh: Seq[List[String]] = List("2", "F") :: List("G", "H") :: Nil

    val actualResult = for {
      a: String <- abcd.flatten
      e: String <- efgh.flatten
    }
    yield (a, e)
    val actualResultFlat = abcd.flatten.flatMap(abcdEl => efgh.flatten.map(efghEl => (abcdEl, efghEl)))

    val expected =
      ("A", "2") :: ("A", "F") :: ("A", "G") :: ("A", "H") ::
        ("B", "2") :: ("B", "F") :: ("B", "G") :: ("B", "H") ::
        ("C", "2") :: ("C", "F") :: ("C", "G") :: ("C", "H") ::
        ("1", "2") :: ("1", "F") :: ("1", "G") :: ("1", "H") :: Nil
    assertEquals(expected, actualResult)
    assertEquals(expected, actualResultFlat)
  }

  @Test
  def flatInMap(): Unit = {
    val abcd: Seq[List[String]] = List("A", "B") :: List("C", "1") :: Nil

    val actualResult = for {
      level1Seq: Seq[String] <- abcd
      level2El: String <- level1Seq
    }
    yield level2El * 2
    val actualResultFlat = abcd.flatMap((level1Seq: Seq[String]) => level1Seq.map(_ * 2))

    val expected = "AA" :: "BB" :: "CC" :: "11" :: Nil
    assertEquals(expected, actualResult)
    assertEquals(expected, actualResultFlat)
  }

  @Test
  def flatInMap2(): Unit = {
    val abc: Seq[List[String]] = List("AAA", "BB") :: List("C") :: Nil

    val actualResult = for {
      level1Seq: Seq[String] <- abc
      level2El: String <- level1Seq
    }
    yield level2El.length
    val actualResultFlat = abc.flatMap((level1Seq: Seq[String]) => level1Seq.map(_.length))

    val expected = 3 :: 2 :: 1 :: Nil
    assertEquals(expected, actualResult)
    assertEquals(expected, actualResultFlat)
  }

  @Test
  def ifTest(): Unit = {
    val input = 1 to 30

    val actualResult = for {letter <- input
                            if letter % 3 == 0}
    yield letter

    assertEquals(Seq(3, 6, 9, 12, 15, 18, 21, 24, 27, 30), actualResult)
  }

  @Test
  def forUsualView(): Unit = {
    val input = 1 to 9

    val actualResult: Seq[Int] = for (letter <- input)
      yield {
        letter % 3
      }

    assertEquals(Seq(1, 2, 0, 1, 2, 0, 1, 2, 0), actualResult)
  }

  @Test
  def forUsualViewNoYield(): Unit = {
    val input = 1 to 9

    var actualResult = List.empty[Int]

    for {letter <- input} {
      actualResult ++= letter % 3 :: Nil
    }

    assertEquals(Seq(1, 2, 0, 1, 2, 0, 1, 2, 0), actualResult)
  }


  @Test
  def yieldNoYieldTest(): Unit = {
    val mapping = ('a' to 'z').zipWithIndex.toMap

    val actualResultYield: immutable.Iterable[Int] =
      for {entry: (Char, Int) <- mapping
           if "aoeui".contains(entry._1)
           }
      yield entry._2


    val actualResultSimple: Unit = for {entry: (Char, Int) <- mapping
                                        if "aoeui".contains(entry._1)
                                        } println(entry._2)

    assertEquals(4 :: 20 :: 0 :: 8 :: 14 :: Nil, actualResultYield)
    assertEquals((), actualResultSimple)
  }
}
