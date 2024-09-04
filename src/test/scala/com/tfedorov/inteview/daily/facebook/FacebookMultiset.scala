package com.tfedorov.inteview.daily.facebook

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

import scala.collection.immutable

/**
 * Given a multiset of integers, return whether it can be partitioned into two subsets whose sums are the same.
 *
 * For example, given the multiset {15, 5, 20, 10, 35, 15, 10}, it would return true, since we can split it up into {15, 5, 10, 15, 10} and {20, 35}, which both add up to 55.
 *
 * Given the multiset {15, 5, 20, 10, 35}, it would return false, since we can't split it up into two subsets that add up to the same sum.
 */
class FacebookMultiset {

  def checkSplt(input: Seq[Int]): immutable.Seq[(Seq[Int], Seq[Int])] = {
    val size: Int = input.size
    (1 until size).map { spitIdx =>
      input.splitAt(spitIdx)
    }.filter(twoSplit => twoSplit._1.sum == twoSplit._2.sum).toSeq

  }

  def splitOn2(input: Seq[Int]): Seq[(Seq[Int], Seq[Int])] =
    input.permutations.flatMap(checkSplt).toSeq

  @Test
  def defaultTrueTest(): Unit = {
    val result = splitOn2(Seq(15, 5, 20, 10, 35, 15, 10))

    result.foreach(println)
    assertEquals(false, result.isEmpty)
  }

  @Test
  def defaultFalseTest(): Unit = {
    val result = splitOn2(Seq(115, 5, 20, 10, 35))

    assertEquals(Seq.empty, result)
  }
}
