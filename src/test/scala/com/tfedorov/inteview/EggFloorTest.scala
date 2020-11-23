package com.tfedorov.inteview

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

import scala.collection.mutable

class EggFloorTest {

  def florVarian(flors: Seq[Int]): Set[scala.Seq[Boolean]] = {
    var current: Seq[Boolean] = flors.map(_ => true)
    var result = Set(current)
    (1 to flors.length - 1).foreach { _ =>
      current = current.tail :+ false
      result += current
    }
    result
  }

  def method(egg: Int, floor: Int) = {
    val flors: Seq[Int] = (1 to floor).map(i => i).toList
    val floorVairantList: Set[Seq[Boolean]] = florVarian(flors)
    val strategiesList = flors.permutations.toList

    4
  }

  @Test
  def egg1Floor10(): Unit = {
    val egg = 2
    val floor = 10

    val actualResult = method(egg, floor)

    assertEquals(10, actualResult)
  }

  @Test
  def egg2Floor10(): Unit = {
    val egg = 1
    val floor = 10

    val actualResult = method(egg, floor)

    assertEquals(10, actualResult)
  }

}
