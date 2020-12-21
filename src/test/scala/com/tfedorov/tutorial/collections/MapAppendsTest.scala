package com.tfedorov.tutorial.collections

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class MapAppendsTest {


  @Test
  def appendElement(): Unit = {
    val input: Map[Int, String] = Map(1 -> "one", 2 -> "two")

    val actualResult = input + (3 -> "three")

    assertEquals(Map(1 -> "one", 2 -> "two", 3 -> "three"), actualResult)
  }

  @Test
  def appendRewrite(): Unit = {
    val input: Map[Int, String] = Map(1 -> "one", 2 -> "two")

    val actualResult = input + (1 -> "uno")

    assertEquals(Map(1 -> "uno", 2 -> "two"), actualResult)
  }

  @Test
  def appendElementsArray(): Unit = {
    val input: Map[Int, String] = Map(1 -> "one", 2 -> "two")

    val actualResult = input + (3 -> "three", 4 -> "four")

    assertEquals(Map(1 -> "one", 2 -> "two", 3 -> "three", 4 -> "four"), actualResult)
  }

  @Test
  def appendVar(): Unit = {
    var target: Map[Int, String] = Map(1 -> "one", 2 -> "two")

    val inputAdd: Map[Int, String] = Map(3 -> "three", 4 -> "four")
    target += (3 -> "three")

    assertEquals(Map(1 -> "one", 2 -> "two", 3 -> "three"), target)
  }

  @Test
  def appendVar2(): Unit = {
    var target: Map[Int, String] = Map(1 -> "one", 2 -> "two")

    val inputAdd: Map[Int, String] = Map(3 -> "three", 4 -> "four")
    target ++= inputAdd

    assertEquals(Map(1 -> "one", 2 -> "two", 3 -> "three", 4 -> "four"), target)
  }

  @Test
  def appendAll(): Unit = {
    val input: Map[Int, String] = Map(1 -> "one", 2 -> "two")
    val inputAdd: Map[Int, String] = Map(3 -> "three", 4 -> "four")

    val actualResult = input ++ inputAdd
    // The same as
    //val actualResult2 = input ++: inputAdd

    assertEquals(Map(1 -> "one", 2 -> "two", 3 -> "three", 4 -> "four"), actualResult)
  }

  @Test
  def appendAllRewrite(): Unit = {
    val input: Map[Int, String] = Map(1 -> "one", 2 -> "two")
    val inputAdd: Map[Int, String] = Map(1 -> "uno", 3 -> "three", 4 -> "four")

    val actualResult = input ++ inputAdd
    // The same as
    //val actualResult2 = input ++: inputAdd

    assertEquals(Map(1 -> "uno", 2 -> "two", 3 -> "three", 4 -> "four"), actualResult)
  }

  @Test
  def delete(): Unit = {
    val input: Map[Int, String] = Map(1 -> "one", 2 -> "two")

    val actualResult = input - 1

    assertEquals(Map(2 -> "two"), actualResult)
  }

  @Test
  def deleteAll(): Unit = {
    val input: Map[Int, String] = Map(1 -> "one", 2 -> "two", 3 -> "three", 4 -> "four")

    val actualResult = input -- (1 :: 3 :: 4 :: Nil)

    assertEquals(Map(2 -> "two"), actualResult)
  }

  @Test
  def deleteValues(): Unit = {
    val input: Map[Int, String] = Map(1 -> "one", 2 -> "two", 3 -> "one")

    val actualResult = input -- input.filter(_._2.equals("one")).keys

    assertEquals(Map(2 -> "two"), actualResult)
  }

}
