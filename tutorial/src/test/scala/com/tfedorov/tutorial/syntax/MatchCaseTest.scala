package com.tfedorov.tutorial.syntax

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class MatchCaseTest {

  case class Mammal(name: String, stillAlive: Boolean) {
    def isAlive = s"You can see $name"
  }

  @Test
  def matchCaseTypesTest(): Unit = {
    val animals = Mammal("mammoth", false) ::
      Mammal("elephant", true) ::
      Mammal("lion", true) ::
      "rhinoceros" ::
      Nil

    val actualResult: Seq[String] = animals.map(_ match {
      case Mammal(name@"lion", true) => s"$name is alive"
      case m@Mammal(_, true) => m.isAlive
      case Mammal(extinct, false) => s"Unfortunately $extinct is extinct"
      case any => s"unknown $any alive or not"
    }
    )

    val expected = "Unfortunately mammoth is extinct" ::
      "You can see elephant" ::
      "lion is alive" ::
      "unknown rhinoceros alive or not" ::
      Nil
    assertEquals(expected, actualResult)
  }

  @Test
  def matchCaseCollectTest(): Unit = {
    val animals = Mammal("mammoth", false) ::
      Mammal("elephant", true) ::
      Mammal("lion", true) ::
      "rhinoceros" ::
      Nil

    val actualResult: Seq[String] = animals.collect {
      case Mammal(name@"lion", true) => s"$name is alive"
      case m@Mammal(_, true) => m.isAlive
    }

    val expected = "You can see elephant" :: "lion is alive" :: Nil
    assertEquals(expected, actualResult)
  }
}
