package com.tfedorov.tutorial.collections

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

import scala.collection.{SortedMap, immutable, mutable}
//https://alvinalexander.com/scala/how-to-choose-map-implementation-class-sorted-scala-cookbook/
/*
+----------------------------------+---------------------------------------------------------------------------------------------------------------------------------------------------------------------------+
|               Map                |                                                                                Definietion                                                                                |
+----------------------------------+---------------------------------------------------------------------------------------------------------------------------------------------------------------------------+
| collection.immutable.Map         | This is the default, general-purpose immutable map you get if you don’t import anything.                                                                                  |
+----------------------------------+---------------------------------------------------------------------------------------------------------------------------------------------------------------------------+
| collection.mutable.Map           | A mutable version of the basic map.                                                                                                                                       |
+----------------------------------+---------------------------------------------------------------------------------------------------------------------------------------------------------------------------+
| collection.mutable.LinkedHashMap | All methods that traverse the elements will visit the elements in their insertion order.                                                                                  |
| collection.immutable.ListMap     |                                                                                                                                                                           |
+----------------------------------+---------------------------------------------------------------------------------------------------------------------------------------------------------------------------+
| collection.mutable.ListMap       | Per the Scaladoc, “implements immutable maps using a list-based data structure.” As shown in the examples, elements that are added are prepended to the head of the list. |
+----------------------------------+---------------------------------------------------------------------------------------------------------------------------------------------------------------------------+
| collection.SortedMap             | Keys of the map are returned in sorted order. Therefore, all traversal methods (such as foreach) return keys in that order.                                               |
+----------------------------------+---------------------------------------------------------------------------------------------------------------------------------------------------------------------------+


 */
class SotredLinkedMapTest {

  @Test
  def compareWithMap(): Unit = {
    val inputs: immutable.Seq[(Char, Int)] = ('A' to 'Z').zipWithIndex

    val inputMap = SortedMap(inputs: _*)

    //val actual = /*inputMap*/ Map(inputs: _*)

    var actual: Option[(Char, Int)] = None
    val i = inputMap.iterator
    while (i.hasNext && actual.isEmpty) {
      val nextEl = i.next()
      if (nextEl._1 == 'K')
        actual = Some(nextEl)
    }

    assertEquals(('L', 11), i.next())
  }

  @Test
  def compareWithMap1(): Unit = {
    val inputs: Seq[(Char, Int)] = ('A', 1) :: ('Z', 22) :: ('K', 11) :: Nil
    val inputMap = mutable.LinkedHashMap(inputs: _*)

    val actual = inputMap.remove('Z')

    assertEquals(('A', 1) :: ('K', 11) :: Nil, inputMap.toSeq)
    assertEquals(Some(22), actual)
  }
}
