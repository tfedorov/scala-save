package com.tfedorov.tutorial.collections

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class SeqAppendsTest {

  @Test
  def appendPrepend(): Unit = {

    val actualResult = 1 +: Seq(2, 3) :+ 4

    assertEquals(Seq(1, 2, 3, 4), actualResult)
  }

  //http://devdoc.net/bigdata/scala-2.12.8-doc_index/scala-library/scala/collection/Seq.html
  @Test
  def appendElement(): Unit = {
    val input: Seq[Int] = Seq(1, 2, 3, 4, 5)

    val actualResult = input :+ 6
    //val actualResult = input.:+(6)

    assertEquals(Seq(1, 2, 3, 4, 5, 6), actualResult)
  }

  @Test
  def appendVar(): Unit = {
    var actualResult: Seq[Int] = Seq(1, 2, 3, 4, 5)

    actualResult ++= 6 :: Nil

    assertEquals(Seq(1, 2, 3, 4, 5, 6), actualResult)
  }

  @Test
  def appendAll(): Unit = {
    val input: Seq[Int] = Seq(1, 2, 3, 4, 5)

    val actualResult1 = input ++ (6 :: 7 :: Nil)
    val actualResult2 = input ++: (6 :: 7 :: Nil)

    assertEquals(Seq(1, 2, 3, 4, 5, 6, 7), actualResult1)
    assertEquals(Seq(1, 2, 3, 4, 5, 6, 7), actualResult2)
  }

  @Test
  def append2Element(): Unit = {

    val actualResult = 0 +: Seq(1, 2)

    assertEquals(Seq(0, 1, 2), actualResult)
  }

  @Test
  def appped(): Unit = {
    val input: Seq[Int] = 0 +: (1 :: Nil)

    //Bun not
    // val actualResult = input +: 0
    val actualResult = input.+:(0)
    // The same us
    // val actualResult = 0 +: input

    assertEquals(Seq(0, 1, 2, 3, 4, 5), actualResult)
  }

  @Test
  def prepend(): Unit = {
    val input: Seq[Int] = Seq(1, 2, 3, 4, 5)

    //Bun not
    // val actualResult = input +: 0
    val actualResult = input.+:(0)
    // The same us
    // val actualResult = 0 +: input

    assertEquals(Seq(0, 1, 2, 3, 4, 5), actualResult)
  }

  @Test
  def prependAll(): Unit = {
    val input: Seq[Int] = Seq(1, 2, 3, 4, 5)

    // But not
    //val actualResult = input ++: Seq(-1, 0)
    val actualResult = input.++:(-1 :: 0 :: Nil)

    assertEquals(Seq(-1, 0, 1, 2, 3, 4, 5), actualResult)
  }

  @Test
  @Deprecated
  def appendString(): Unit = {
    val input: Seq[Int] = Seq(1, 2)

    val actualResult: String = input + "6"
    val actualResultExpl: String = any2stringadd(input) + "6"

    assertEquals("List(1, 2)6", actualResult)
    assertEquals("List(1, 2)6", actualResult)
  }

  @Test
  def deleteElement(): Unit = {
    val input: Seq[Int] = Seq(2, 3, 4, 5, 4)

    val actualId = input.indexOf(4)
    val actualResult = input.take(actualId) ++ input.drop(actualId + 1)

    assertEquals(2 :: 3 :: 5 :: 4 :: Nil, actualResult)
  }
}
