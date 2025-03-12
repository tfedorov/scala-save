package com.tfedorov.inteview.daily_coding_problem

import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test

import scala.collection.immutable
import scala.util.Random

/*
Assume you have access to a function toss_biased() which returns 0 or 1 with a probability that's not 50-50 (but also not 0-100 or 100-0). You do not know the bias of the coin.

Write a function to simulate an unbiased coin toss.
 */
class SquareTossBiasedTest {

  private val percentage = 0.36
  private val NUMBER_OF_REPEAT = 10000000

  def secretToss(): Boolean = {

    val random = Random.nextFloat()
    random < percentage
  }

  def tossBiased(): Boolean = {
    val checkProb: Map[Boolean, Int] = (1 to NUMBER_OF_REPEAT).map(_ => secretToss()).groupBy(identity).mapValues(_.size)
    val valuesForOut = (1 to NUMBER_OF_REPEAT).map(_ => secretToss()).groupBy(identity).mapValues(_.size)

    checkProb(true) > valuesForOut(true)
  }

  @Test
  def tossBiasedTestTest(): Unit = {


    val actualResult: immutable.Seq[Boolean] = (1 to 100).map(_ => tossBiased())

    val actualMap = actualResult.groupBy(identity).mapValues(_.size)
    actualMap.foreach(println)
    assertTrue(actualMap(true) > 45)
    assertTrue(actualMap(true) < 55)
  }
}
