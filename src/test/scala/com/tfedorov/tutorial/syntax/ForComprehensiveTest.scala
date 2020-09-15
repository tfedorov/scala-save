package com.tfedorov.tutorial.syntax

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

import scala.collection.immutable

class ForComprehensiveTest {

  /*
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
  def unionFlattenTest(): Unit = {
    val abc = "A" :: "B" :: "C" :: Nil
    val deF = "D" :: "E" :: "F" :: Nil

    val actualResult: immutable.Seq[(String, String)] = for {
      a: String <- abc
      d: String <- deF
    }
      yield (a, d)

    val expected = abc.flatMap(abcEl => deF.map(defEl => (abcEl, defEl)))
    assertEquals(expected, actualResult)
  }

  @Test
  def unionInnerFlattenTest(): Unit = {
    val abcd: immutable.Seq[List[String]] = List("A", "B") :: List("C", "D") :: Nil
    val efgh: immutable.Seq[List[String]] = List("E", "F") :: List("G", "H") :: Nil

    val actualResult = for {
      a: String <- abcd.flatten
      e: String <- efgh.flatten
    }
      yield (a, e)


    val expected = abcd.flatten.flatMap(abcdEl => efgh.flatten.map(efghEl => (abcdEl, efghEl)))
    assertEquals(expected, actualResult)
  }

  @Test
  def unionMultiFlattenTest(): Unit = {
    val abc = "A" :: "B" :: "C" :: Nil
    val multiper = 2 :: 3 :: Nil

    val actualResult = for {
      a: String <- abc
      d: Int <- multiper
    }
      yield a * d

    val expected = abc.flatMap((abcEl: String) => multiper.map((mEl: Int) => abcEl * mEl))
    assertEquals(expected, actualResult)
  }
}
