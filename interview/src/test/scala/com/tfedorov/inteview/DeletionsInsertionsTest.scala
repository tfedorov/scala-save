package com.tfedorov.inteview

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class DeletionsInsertionsTest {

  def method(input1: String, input2: String): Result = {
    val (bigger: String, smaller: String) = compare(input1, input2)
    val insertSize = insert(bigger, smaller)
    if (insertSize > 0)
      return Result(0, insertSize)
    val commonSize = commonChunk(bigger, smaller)
    if (smaller.length == commonSize)
      return Result(bigger.length - commonSize, 0)
    Result(smaller.length - commonSize, bigger.length - commonSize)
  }

  case class Result(delete: Int, insert: Int)

  def insert(bigger: String, smaller: String): Int = {
    (-1 * smaller.length to -2).foreach(i => bigger.sliding(-1 * i, 1).foreach { chunk =>

      val before = bigger.substring(0, bigger.indexOf(chunk))
      val after = bigger.substring(bigger.indexOf(chunk) + chunk.length)
      if (smaller.equals(before + after))
        return chunk.length
    })
    0
  }

  def commonChunk(bigger: String, smaller: String): Int = {
    (-1 * smaller.length to -1).foreach(i => smaller.sliding(-1 * i, 1).foreach { chunk =>
      if (bigger.contains(chunk))
        return chunk.length
    })
    0
  }

  def compare(input1: String, input2: String): (String, String) = {
    if (input1.length >= input2.length)
      return (input1, input2)
    (input2, input1)
  }


  @Test
  def insert0Delete8(): Unit = {
    val input1 = "geeksforgeeks"
    val input2 = "geeks"

    val actualResult = method(input1, input2)

    assertEquals(Result(8, 0), actualResult)
  }

  @Test
  def insert1Delete0(): Unit = {
    val input1 = "aabbcc"
    val input2 = "aabbc"

    val actualResult = method(input1, input2)

    assertEquals(Result(1, 0), actualResult)
  }

  @Test
  def insert2Delete0(): Unit = {
    val input1 = "aabbcc"
    val input2 = "aacc"

    val actualResult = method(input1, input2)

    assertEquals(Result(0, 2), actualResult)
  }

  @Test
  def insert2Delete1(): Unit = {
    val input1 = "heap"
    val input2 = "pea"

    val actualResult = method(input1, input2)

    assertEquals(Result(1, 2), actualResult)
  }

}
