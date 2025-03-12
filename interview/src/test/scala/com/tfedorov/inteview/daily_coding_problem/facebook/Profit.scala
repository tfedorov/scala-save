package com.tfedorov.inteview.daily_coding_problem.facebook

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

import scala.annotation.tailrec

//Given a array of numbers representing the stock prices of a company in chronological order,
// write a function that calculates the maximum profit you could have made from buying and selling that stock once.
// You must buy before you can sell it.

//For example, given [9, 11, 8, 5, 7, 10], you should return 5, since you could buy the stock at 5 dollars and sell it at 10 dollars.
//Easy
class Profit {

  case class Candidate(buy: Int, sell: Int) {
    lazy val diff: Int = sell - buy
  }

  def buy(input: Seq[Int]): Candidate = {
    buyRecursive(input.head, input.tail, Candidate(0, Int.MinValue))
  }

  @tailrec
  final def buyRecursive(candidate2Buy: Int, candidate2Sell: Seq[Int], bestChoice: Candidate): Candidate = {
    if (candidate2Sell.size <= 1)
      return bestChoice
    val bestSell4Candidate = candidate2Sell.map(Candidate(candidate2Buy, _)).sortWith(_.diff > _.diff).head
    if (bestSell4Candidate.diff > bestChoice.diff)
      buyRecursive(candidate2Sell.head, candidate2Sell.tail, bestSell4Candidate)
    else
      buyRecursive(candidate2Sell.head, candidate2Sell.tail, bestChoice)
  }

  @Test
  def buyDefaultTest(): Unit = {
    val input = Seq(9, 11, 8, 5, 7, 10)

    val actualResult = buy(input)

    val expectedResult = Candidate(5, 10)
    assertEquals(expectedResult, actualResult)
  }

  @Test
  def buyTest(): Unit = {
    val input = Seq(1, 9, 11, 8, 5, 7, 10)

    val actualResult = buy(input)

    val expectedResult = Candidate(1, 11)
    assertEquals(expectedResult, actualResult)
  }

}
