package com.tfedorov.tutorial.collections

import org.junit.jupiter.api.Assertions.{assertEquals, assertTrue}
import org.junit.jupiter.api.Test

import scala.collection.mutable.ListBuffer

class ListAppendsTest {

  @Test
  def appendPrepend(): Unit = {

    val actualResult1 = 1 +: List(2, 3) ::: (4 :: Nil)
    //Seq example
    val actualResult2 = 1 +: List(2, 3) :+ 4

    assertEquals(Seq(1, 2, 3, 4), actualResult1)
    assertEquals(Seq(1, 2, 3, 4), actualResult2)
  }

  @Test
  def appendElement(): Unit = {
    val input: List[Int] = List(1, 2, 3, 4, 5)

    val actualResult = input :+ 6
    //The same as
    //val actualResult = input.:+(6)
    //The same as
    //val actualResult2 = input ++ (6 :: Nil)

    assertEquals(Seq(1, 2, 3, 4, 5, 6), actualResult)
  }

  @Test
  def appendVar(): Unit = {
    var actualResult: List[Int] = List(1, 2, 3, 4, 5)

    actualResult ++= 6 :: Nil

    assertEquals(Seq(1, 2, 3, 4, 5, 6), actualResult)
  }

  @Test
  def appendAll(): Unit = {
    val input: List[Int] = List(1, 2, 3, 4, 5)

    val actualResult = input ::: List(6, 7)
    //Seq example
    //val actualResult = input ++ (6 :: 7 :: Nil)

    assertEquals(List(1, 2, 3, 4, 5, 6, 7), actualResult)
  }

  @Test
  def append2Element(): Unit = {

    val actualResult = 0 +: List(1, 2)

    assertEquals(List(0, 1, 2), actualResult)
  }


  @Test
  @Deprecated
  def prepend(): Unit = {
    val input: List[Int] = List(1, 2, 3, 4, 5)

    //Bun not
    // val actualResult = input +: 0
    val actualResult = input.+:(0)
    // The same us
    // val actualResult = 0 +: input

    assertEquals(Seq(0, 1, 2, 3, 4, 5), actualResult)
  }

  @Test
  def appendAllHierarchy(): Unit = {
    val input: List[Int] = List(1, 2, 3, 4, 5)

    val actualResult = input :: List(6, 7)
    //val actualResult = input :: 6 :: 7 :: Nil

    assertEquals(List(List(1, 2, 3, 4, 5), 6, 7), actualResult)
  }

  @Test
  def appendNilFlatten(): Unit = {
    val input: List[Int] = List(1, 2, 3, 4, 5)

    val actualResult = input ::: Nil
    //Seq example
    //val actualResult = input ++ Nil

    assertEquals(input, actualResult)
  }

  @Test
  def appendNilHierarchy(): Unit = {
    val input: List[Int] = List(1, 2, 3, 4, 5)

    val actualResult = input :: Nil

    assertEquals(List(List(1, 2, 3, 4, 5)), actualResult)
  }

  @Test
  @Deprecated
  def appendString(): Unit = {
    val input: List[Int] = List(1, 2)

    val actualResult: String = input + "6"

    assertEquals("List(1, 2)6", actualResult)
  }

  @Test
  def deleteElementById(): Unit = {
    val input: List[Int] = List(2, 3, 4, 5, 4)

    val actualResult = input.take(2) ++ input.drop(3)

    assertEquals(2 :: 3 :: 5 :: 4 :: Nil, actualResult)
  }

  @Test
  def deleteElement(): Unit = {
    val input: List[Int] = List(2, 3, 4, 5, 4)

    val actualId = input.indexOf(4)
    val actualResult = input.take(actualId) ++ input.drop(actualId + 1)

    assertEquals(2 :: 3 :: 5 :: 4 :: Nil, actualResult)
  }
}
