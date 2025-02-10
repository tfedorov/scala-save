package com.tfedorov.inteview.daily_coding_problem.facebook

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class FacebookCollorsTest {

  private def selectCheper(matrix4x4: Seq[Map[String, Int]]): String = {
    /*
    val combinations = for {building1: (String, Int) <- matrix4x4.head
                             building2: (String, Int) <- matrix4x4.tail.head
                             building3: (String, Int) <- matrix4x4.tail.tail.head
                             building4: (String, Int) <- matrix4x4.last
                             } yield building1 :: building2 :: building3 :: building4 :: Nil
*/
    val combinations = generateAllCombinations(matrix4x4)
    val noColorNeighborhood = combinations.filter { el => !el.sliding(2).exists(twoHouse => twoHouse.head._1.equalsIgnoreCase(twoHouse.last._1)) }
    var minPrice: Int = Integer.MAX_VALUE
    var cheapestColorSchema: String = ""
    noColorNeighborhood.foreach { el =>
      val combPrice = el.map(_._2).sum
      val combSchema = el.map(_._1).mkString(",")
      if (combPrice < minPrice) {
        minPrice = combPrice
        cheapestColorSchema = combSchema
      }
    }
    println(minPrice)
    cheapestColorSchema
  }

  private def generateAllCombinations(matrix4x4: Seq[Map[String, Int]]): Seq[List[(String, Int)]] = {
    val combinations = matrix4x4.head.flatMap(
      building1 => matrix4x4.tail.head.flatMap(
        building2 => matrix4x4.tail.tail.head.flatMap(
          building3 => matrix4x4.last.map(
            building4 => building1 :: building2 :: building3 :: building4 :: Nil))))
    combinations.toSeq
  }
  @Test
  def taskTest(): Unit = {
    //val colors = Seq("red", "green", "blue", "orange")
    val matrix4x4 = Seq(
      //1 building in row
      Map("red" -> 1, "green" -> 2, "blue" -> 3, "orange" -> 4),
      //2 building in row
      Map("red" -> 11, "green" -> 22, "blue" -> 43, "orange" -> 1),
      Map("red" -> 1, "green" -> 2, "blue" -> 4, "orange" -> 1),
      Map("red" -> 7, "green" -> 3, "blue" -> 13, "orange" -> 12)
    )

    val actualResult: String = selectCheper(matrix4x4)

    val expectedResult = "red,orange,red,green"
    assertEquals(expectedResult, actualResult)
  }
}
