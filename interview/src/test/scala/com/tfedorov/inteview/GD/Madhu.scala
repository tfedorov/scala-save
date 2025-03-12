package com.tfedorov.inteview.GD

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class Madhu {


  /*
    Solve several tasks.
  1. Remove repeated characters
  input = acdddacbbbef
  output = acacef
     */
  @Test
  def removeDuplicatesDefTest(): Unit = {
    val input = "acdddacbbbef"

    val actualResult: String = removeDuplicates(input)
    println(actualResult)
    val expectedResult = "acacef"
    assertEquals(expectedResult, actualResult)
  }

  @Test
  def removeDuplicatesTest(): Unit = {
    val input = "acdddacbbbeff"

    val actualResult: String = removeDuplicates(input)

    val expectedResult = "acace"
    assertEquals(expectedResult, actualResult)
  }

  def removeDuplicates(input: String): String = {
    val inputWithEnd = input + " "
    var prevChar = inputWithEnd.head + ""
    inputWithEnd.tail.foldLeft("") { (agg, char) =>
      if (prevChar.last.equals(char)) {
        prevChar += char
        agg
      } else if (prevChar.length == 1) {
        val result = agg + prevChar
        prevChar = char + ""
        result
      } else {
        prevChar = char + ""
        agg
      }
    }.trim
  }

}
