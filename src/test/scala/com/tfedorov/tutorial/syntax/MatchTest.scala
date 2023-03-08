package com.tfedorov.tutorial.syntax

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class MatchTest {

  case class Mammal(name: String, stillAlive: Boolean) {
    def canSee = s"You can see $name"
  }

  @Test
  def matchTypesTest(): Unit = {
    val animals = Seq(Mammal("mammoth", false), Mammal("elephant", true), "rhinoceros")

    val actualResult: Seq[String] = animals.map(_ match {
      case m@Mammal(_, true) => m.canSee
      case Mammal(extinct, false) => s"Unfortunately $extinct is extinct"
      case any => s"unknown $any alive or not"
    }
    )

    val expected = "Unfortunately mammoth is extinct" :: "You can see elephant" :: "unknown rhinoceros alive or not" :: Nil
    assertEquals(expected, actualResult)
  }


  @Test
  def matchTypesCollectTest(): Unit = {
    val animals = Seq(Mammal("mammoth", false), Mammal("elephant", true), "rhinoceros")

    val actualResult: Seq[String] = animals.collect {
      case m@Mammal(_, true) => m.canSee
      case Mammal(extinct, false) => s"Unfortunately $extinct is extinct"
    }

    val expected = "Unfortunately mammoth is extinct" :: "You can see elephant" :: Nil
    assertEquals(expected, actualResult)
  }
}
