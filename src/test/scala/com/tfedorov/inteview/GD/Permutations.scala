package com.tfedorov.inteview.GD

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class Permutations {

  @Test
  def defaultTest(): Unit = {
    val input = "inp"

    val actualResult = permutation(input)

    val expectedResult = List("inp", "ipn", "nip", "npi", "pin", "pni")
    assertEquals(expectedResult, actualResult)
  }

  @Test
  def defaultDubleTest(): Unit = {
    val input = "inpi"

    val actualResult = permutation(input)

    val expectedResult = List("inpi", "inip", "ipni", "ipin", "iinp", "iipn", "nipi", "niip", "npii", "npii", "nipi",
      "niip", "pini", "piin", "pnii", "pnii", "pini", "piin", "inpi", "inip", "ipni", "ipin", "iinp", "iipn")
    assertEquals(expectedResult, actualResult)
  }

  def permutation(input: String): Seq[String] = {
    val dictionary: Seq[String] = input.toList.map(_.toString)

    permutationAll(dictionary, "")
  }

  final def permutationAll(dictionary: Seq[String], builtString: String): Seq[String] = {
    if (dictionary.isEmpty)
      return Seq(builtString)

    val with1DictLetter: Seq[String] = dictionary.flatMap { el =>
      val nextDictionary: Seq[String] = dictionary.diff(Seq(el))
      val nextBuiltString = builtString + el
      permutationAll(nextDictionary, nextBuiltString)
    }
    with1DictLetter
  }

}
