package com.tfedorov.inteview

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

import scala.collection.immutable

class HasherTest {

  @Test
  def testSum8(): Unit = {
    val alphabet: immutable.Seq[Char] = 'a' to 'z'

    val possible: immutable.Seq[(String, Boolean)] =
      for (ch1 <- alphabet;
           ch2 <- alphabet;
           ch3 <- alphabet;
           ch4 <- alphabet;
           ch5 <- alphabet)
        yield ("" + ch1 + ch2 + ch3 + ch4 + ch5, false)

    val sortedMap = scala.collection.immutable.TreeMap(possible: _*)

    val changed = sortedMap + (("abcgi", true))

    val iterator: Iterator[(String, Boolean)] = changed.iteratorFrom("abcgh")
    iterator.next()
    val resultIter: (String, Boolean) = iterator.next()
    assertEquals(("abcgi", true), resultIter)

    assertEquals(11881376, possible.length)
  }
}
