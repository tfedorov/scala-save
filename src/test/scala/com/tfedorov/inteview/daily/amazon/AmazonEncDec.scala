package com.tfedorov.inteview.daily.amazon

/*
This problem was asked by Amazon.

Run-length encoding is a fast and simple method of encoding strings.
 The basic idea is to represent repeated successive characters as a single count and character.
 For example, the string "AAAABBBCCDAA" would be encoded as "4A3B2C1D2A".

Implement run-length encoding and decoding. You can assume the string to be encoded have no digits and consists solely
 of alphabetic characters. You can assume the string to be decoded is valid.
 */

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class AmazonEncDec {

  def encode(input: String): String = {
    case class Agg(encoded: String, encCharCandidate: Char, number: Int = 1) {
      def resolveEncoded: String = encoded + number + encCharCandidate
    }
    val resultAgg = input.tail.foldLeft(Agg("", input.head)) { (agg, currentChar) =>
      if (agg.encCharCandidate == currentChar)
        agg.copy(number = agg.number + 1)
      else
        Agg(agg.resolveEncoded, currentChar)
    }
    resultAgg.resolveEncoded
  }

  def decode(input: String): String = {
    val result = new StringBuilder("")
    val letters = input.split("\\d").filterNot(_.isEmpty)
    val numbers = input.split("\\D").map(_.toInt)
    letters.zip(numbers).foreach { case (letter, number) => result.append(letter * number) }
    result.toString()
  }

  @Test
  def defaultEncTest(): Unit = {
    val input = "AAAABBBCCDAA"

    val actualResult: String = encode(input)

    assertEquals("4A3B2C1D2A", actualResult)
  }

  @Test
  def smallEncTest(): Unit = {
    val input = "A"

    val actualResult: String = encode(input)

    assertEquals("1A", actualResult)
  }

  @Test
  def bigEncTest(): Unit = {
    val input = "AAAAAAAAAAAAAAAAAAAAAAAAAAA"

    val actualResult: String = encode(input)

    assertEquals("27A", actualResult)
  }

  @Test
  def defaultDecodeTest(): Unit = {
    val input = "4A3B2C1D2A"

    val actualResult: String = decode(input)

    assertEquals("AAAABBBCCDAA", actualResult)
  }

  @Test
  def smallDecodeTest(): Unit = {
    val input = "1A"

    val actualResult: String = decode(input)

    assertEquals("A", actualResult)
  }

  @Test
  def bigDecTest(): Unit = {
    val input = "27A"

    val actualResult: String = decode(input)

    assertEquals("AAAAAAAAAAAAAAAAAAAAAAAAAAA", actualResult)
  }
}
