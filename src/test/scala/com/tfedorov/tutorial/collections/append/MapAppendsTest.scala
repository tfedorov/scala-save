package com.tfedorov.tutorial.collections

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

/**
 * Note: No v
 */
class MapAppendsTest {


  @Test
  def appendToElement(): Unit = {
    // No present
  }

  @Test
  def appendElementToMap(): Unit = {
    val input: Map[Int, String] = Map(1 -> "one", 2 -> "two")

    val actualResult = input + (3 -> "three")

    assertEquals(Map(1 -> "one", 2 -> "two", 3 -> "three"), actualResult)
  }

  @Test
  def appendElementRewrite(): Unit = {
    val input: Map[Int, String] = Map(1 -> "one", 2 -> "two")

    val actualResult = input + (1 -> "uno")

    assertEquals(Map(1 -> "uno", 2 -> "two"), actualResult)
  }

  @Test
  def appendElements(): Unit = {
    val input: Map[Int, String] = Map(1 -> "one", 2 -> "two")

    val actualResult1 = input + (3 -> "three", 4 -> "four")
    val actualResult2 = input ++ Map(3 -> "three", 4 -> "four")
    val actualResult3 = input ++: Map(3 -> "three", 4 -> "four")

    assertEquals(Map(1 -> "one", 2 -> "two", 3 -> "three", 4 -> "four"), actualResult1)
    assertEquals(Map(1 -> "one", 2 -> "two", 3 -> "three", 4 -> "four"), actualResult2)
    assertEquals(Map(1 -> "one", 2 -> "two", 3 -> "three", 4 -> "four"), actualResult3)
  }

  @Test
  def appendVar(): Unit = {
    var target: Map[Int, String] = Map(1 -> "one", 2 -> "two")

    target += (3 -> "three")

    assertEquals(Map(1 -> "one", 2 -> "two", 3 -> "three"), target)
  }

  @Test
  def appendVar2(): Unit = {
    var target: Map[Int, String] = Map(1 -> "one", 2 -> "two")

    val inputAdd: Map[Int, String] = Map(3 -> "three", 4 -> "four")
    target ++= inputAdd

    assertEquals(Map(1 -> "one", 2 -> "two", 3 -> "three", 4 -> "four"), target)
  }


  @Test
  def appendAllRewrite(): Unit = {
    val input: Map[Int, String] = Map(1 -> "one", 2 -> "two")
    val inputAdd: Map[Int, String] = Map(1 -> "uno", 3 -> "three", 4 -> "four")

    val actualResult = input ++ inputAdd
    // The same as
    //val actualResult2 = input ++: inputAdd

    assertEquals(Map(1 -> "uno", 2 -> "two", 3 -> "three", 4 -> "four"), actualResult)
  }

  @Test
  def delete(): Unit = {
    val input: Map[Int, String] = Map(1 -> "one", 2 -> "two")

    val actualResult = input - 1

    assertEquals(Map(2 -> "two"), actualResult)
  }

  @Test
  def deleteAll(): Unit = {
    val input: Map[Int, String] = Map(1 -> "one", 2 -> "two", 3 -> "three", 4 -> "four")

    val actualResult = input -- (1 :: 3 :: 4 :: Nil)

    assertEquals(Map(2 -> "two"), actualResult)
  }


  @Test
  def deleteValues(): Unit = {
    var input: Map[String, List[Long]] = Map(
      "mz_wallet_200k__join__mz_account_avro_100m" -> List(
        191062L,
        24480L,
        37799L,
        20651L,
        18946L
      )
    )

    def results4Case(caseName: String): String = {
      val caseResults = input.getOrElse(caseName, Nil)
      if (caseResults.isEmpty)
        return s"There are no test cases for '$caseName'. Please use: " + input.keys.mkString("\n")
      val formatNumber = java.text.NumberFormat.getNumberInstance()
      val max = formatNumber.format(caseResults.max)
      val min = formatNumber.format(caseResults.min)
      val average = formatNumber.format(caseResults.sum / caseResults.size)
      val all = caseResults.map(formatNumber.format(_) + " milliseconds").mkString("\n")
      s"Measurement results for '$caseName' : \n max     = $max milliseconds\n min     = $min milliseconds \n average = $average milliseconds\n\n All:\n$all"
    }

    println(results4Case("mz_wallet_200k__join__mz_account_avro_100m"))
  }
}
