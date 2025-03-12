package com.tfedorov.inteview.daily_coding_problem.facebook

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

import scala.collection.SortedMap

/*
This problem was asked by Facebook.

Given the mapping a = 1, b = 2, ... z = 26, and an encoded message, count the number of ways it can be decoded.

For example, the message '111' would give 3, since it could be decoded as 'aaa', 'ka', and 'ak'.

You can assume that the messages are decodable. For example, '001' is not allowed.
 */
class FacebookTest {


  val mapping: SortedMap[Char, Int] = SortedMap(('a' to 'z').zipWithIndex.map(t => (t._1, t._2 + 1)): _*)

  @Test
  def mapintTest(): Unit = {
    val input = "111"

    val actualResult = decoder(input)

    val expectedResult = 3
    assertEquals(expectedResult, actualResult)
  }

  @Test
  def mapintTest2(): Unit = {
    val input = "121"

    val actualResult = decoder(input)

    val expectedResult = 3
    assertEquals(expectedResult, actualResult)
  }

  @Test
  def mapintTest3(): Unit = {
    val input = "1213124"

    val actualResult = decoder(input)

    val expectedResult = 15
    assertEquals(expectedResult, actualResult)
  }

  private def decoder(text: String): Int = {
    val allResult = findResult("", "")(text)
    allResult.length
  }

  private def findResult(foundedEnc: String, searchedDec: String)(implicit text: String): Seq[String] = {
    if (searchedDec.length >= text.length)
      return Seq.empty

    if (!text.startsWith(searchedDec))
      return Seq.empty
    var result: Seq[String] = Nil
    for {candidate: (Char, Int) <- mapping} {
      val candidateDecoded = searchedDec + candidate._2.toString
      val candidateEnc = foundedEnc + candidate._1
      if (text.equalsIgnoreCase(candidateDecoded))
        result = result :+ candidateEnc
      result ++= findResult(candidateEnc, candidateDecoded)
    }

    result
  }
}
