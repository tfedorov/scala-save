package com.tfedorov.tutorial.collections

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

import scala.collection.{SortedMap, immutable}

class SotredMapTest {

  @Test
  def compareWithMap(): Unit = {
    val inputs: immutable.Seq[(Char, Int)] = ('A' to 'Z').zipWithIndex

    val inputMap = SortedMap(inputs: _*)

    //val actual = /*inputMap*/ Map(inputs: _*)
    val actual = inputMap
      .keys.sliding(2).map(_.toSeq).find(e => e.head > e.last)

    assertEquals(None, actual)
  }
}
