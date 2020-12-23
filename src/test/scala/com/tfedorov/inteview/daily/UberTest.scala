package com.tfedorov.inteview.daily

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

/*
This problem was asked by Uber.

Given an array of integers, return a new array such that each element at index i of the new array is the
product of all the numbers in the original array except the one at i.

For example, if our input was [1, 2, 3, 4, 5], the expected output would be [120, 60, 40, 30, 24].
If our input was [3, 2, 1], the expected output would be [2, 3, 6].
 */
class UberTest {

  def prod(input: Seq[Int]): Seq[Int] = {
    input.indices.map { ind =>
      val noIndex = input.take(ind) ++ input.drop(ind + 1)
      noIndex.product
    }
  }

  @Test
  def Test1(): Unit = {
    val input = Seq(1, 2, 3, 4, 5)

    val actualResult = prod(input)

    assertEquals(Seq(120, 60, 40, 30, 24), actualResult)
  }

  @Test
  def Test2(): Unit = {
    val input = Seq(3, 2, 1)

    val actualResult = prod(input)

    assertEquals(Seq(2, 3, 6), actualResult)
  }
}
