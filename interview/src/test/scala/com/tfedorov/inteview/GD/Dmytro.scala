package com.tfedorov.inteview.GD

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class Dmytro {

  @Test
  def case1Test(): Unit = {
    val list1 = List(1, 2, 4)
    val list2 = List(1, 3, 4)

    val actualResult = mergeTwoListsRecursive(list1, list2)

    val expectedResult = List(1, 1, 2, 3, 4, 4)
    assertEquals(expectedResult, actualResult)
  }

  @Test
  def case2Test(): Unit = {
    val list1 = List.empty[Int]
    val list2 = List.empty[Int]

    val actualResult = mergeTwoListsRecursive(list1, list2)

    val expectedResult = List.empty[Int]
    assertEquals(expectedResult, actualResult)
  }


  @Test
  def case3Test(): Unit = {
    val list1 = List.empty[Int]
    val list2 = List(0)

    val actualResult = mergeTwoListsRecursive(list1, list2)

    val expectedResult = List(0)
    assertEquals(expectedResult, actualResult)
  }

  def mergeTwoListsRecursive(list1: List[Int], list2: List[Int]): List[Int] = {
    ???
  }
}
