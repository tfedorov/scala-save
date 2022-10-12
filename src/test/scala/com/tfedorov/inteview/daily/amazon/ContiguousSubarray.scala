package com.tfedorov.inteview.daily.amazon

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

/*
Given an array of numbers, find the maximum sum of any contiguous subarray of the array.

For example, given the array [34, -50, 42, 14, -5, 86], the maximum sum would be 137, since we would take elements 42, 14, -5, and 86.

Given the array [-5, -1, -8, -9], the maximum sum would be 0, since we would not take any elements.
 */
class ContiguousSubarray {

  def contiguos(input: Seq[Int]): Seq[Int] = {
    contiguosRecurs(input, Seq.empty)
  }

  def contiguosRecurs(input: Seq[Int], result: Seq[Int]): Seq[Int] = {
    if (input.isEmpty)
      return result
    var newCurrentResult = result
    if (input.sum > result.sum)
      newCurrentResult = input

    val tailMaxResult = contiguosRecurs(input.tail, newCurrentResult)
    val headMaxResult = contiguosRecurs(input.reverse.tail.reverse, newCurrentResult)
    (newCurrentResult :: tailMaxResult :: headMaxResult :: Nil).sortWith(_.sum > _.sum).headOption.getOrElse(Nil)
  }

  @Test
  def contiguosDefTest(): Unit = {
    val input = Seq(34, -50, 42, 14, -5, 86)

    val actualResult = contiguos(input)

    val expectedResult = actualResult.sum
    assertEquals(expectedResult, actualResult.sum)
    assertEquals(List(42, 14, -5, 86), actualResult)
  }

  @Test
  def contiguos0Test(): Unit = {
    val input = Seq(-5, -1, -8, -9)

    val actualResult = contiguos(input)

    assertEquals(0, actualResult.sum)
    assertEquals(Nil, actualResult)
  }
}
