package com.tfedorov.tutorial.collections

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class SetAppendsTest {

  @Test
  def append(): Unit = {

    val actualResult = Set(2, 3) + 4

    assertEquals(Set(2, 3, 4), actualResult)
  }

  @Test
  def appendVar(): Unit = {

    var actualResult = Set(2, 3)

    actualResult += 4

    assertEquals(Set(2, 3, 4), actualResult)
  }

  @Test
  def appendAll(): Unit = {

    val actualResult1 = Set(2, 3) ++ Set(1, 4)
    val actualResult2 = Set(2, 3) + (1, 4)

    assertEquals(Set(1, 2, 3, 4), actualResult1)
    assertEquals(Set(1, 2, 3, 4), actualResult2)
  }

  @Test
  def removeElement(): Unit = {

    val actualResult = Set(1, 2, 3) - 1

    assertEquals(Set(2, 3), actualResult)
  }

  @Test
  def removeVarElement(): Unit = {
    var actualResult = Set(1, 2, 3)

    actualResult -= 1

    assertEquals(Set(2, 3), actualResult)
  }

}
