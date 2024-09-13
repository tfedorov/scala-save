package com.tfedorov.inteview.daily_coding_problem

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

/*
This problem was asked by Stripe.

Given an array of integers, find the first missing positive integer in linear time and constant space. In other words,
find the lowest positive integer that does not exist in the array. The array can contain duplicates and negative numbers as well.

For example, the input [3, 4, -1, 1] should give 2. The input [1, 2, 0] should give 3.
 */
class StripeTest {

  def firstMissingPositive(input: Seq[Int]): Int = {
    val positives = input.filter(_ > 0).sorted

    val last = positives.foldLeft(0) { (prev, next) =>
      if (next - prev > 1)
        return prev + 1
      else
        next
    }
    last + 1
  }

  @Test
  def Test1(): Unit = {
    val input = Seq(1, 2, 0)

    val actualResult = firstMissingPositive(input)

    assertEquals(3, actualResult)
  }

  @Test
  def Test2(): Unit = {
    val input = Seq(3, 4, -1, 1)

    val actualResult = firstMissingPositive(input)

    assertEquals(2, actualResult)
  }

  @Test
  def Test3(): Unit = {
    val input = Seq(-2, -1)

    val actualResult = firstMissingPositive(input)

    assertEquals(1, actualResult)
  }
}
