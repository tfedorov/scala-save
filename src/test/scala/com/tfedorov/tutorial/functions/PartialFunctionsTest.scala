package com.tfedorov.tutorial.functions

import org.junit.jupiter.api.Assertions.{assertEquals, assertFalse, assertTrue}
import org.junit.jupiter.api.Test

class PartialFunctionsTest {

  @Test
  def partialFunctionIsDefined(): Unit = {
    val oneF: PartialFunction[Int, String] = {
      case 1 => "one"
    }

    assertTrue(oneF.isDefinedAt(1))
    assertFalse(oneF.isDefinedAt(2))
  }

  @Test
  def partialFunctionList(): Unit = {
    val oneF: PartialFunction[Int, String] = {
      case 1 => "one"
    }
    val list = 1 :: 2 :: 1 :: 3 :: Nil

    val actual = list.collect(oneF)

    assertEquals("one" :: "one" :: Nil, actual)
  }

  @Test
  def partialFunctionListPart(): Unit = {
    val oneF: PartialFunction[Int, String] = new PartialFunction[Int, String]() {
      override def isDefinedAt(x: Int): Boolean = x == 1

      override def apply(v1: Int): String = "one"
    }
    val list = 1 :: 2 :: 1 :: 3 :: Nil

    val actual = list.collect(oneF)

    assertEquals("one" :: "one" :: Nil, actual)
  }

  @Test
  def partialFunctionList2(): Unit = {
    val oneF: PartialFunction[Int, String] = {
      case 1 => "one"
    }
    val list = 3 :: 2 :: 3 :: 3 :: Nil

    val actual = list.collect(oneF)

    assertEquals(Nil, actual)
  }

  @Test
  def orElseF(): Unit = {
    val oneF: PartialFunction[Int, String] = {
      case 1 => "one"
    }
    val twoF: PartialFunction[Int, String] = {
      case 2 => "two"
    }
    val list = 1 :: 2 :: 1 :: 3 :: Nil

    //val actual = list.collect(oneF.orElse(twoF))
    val actual = list.collect(oneF orElse twoF)

    assertEquals("one" :: "two" :: "one" :: Nil, actual)
    assertEquals("one", (oneF orElse twoF) (1))
  }

  @Test
  def mapPartialFunc(): Unit = {
    val list = 1 :: 2 :: /*3 ::*/ Nil
    val oneF: PartialFunction[Int, String] = {
      case 1 => "one"
    }
    val twoF: PartialFunction[Int, String] = {
      case 2 => "two"
    }

    val actual = list.map(oneF orElse twoF)

    assertEquals("one" :: "two" :: Nil, actual)
  }

  @Test
  def mapPartialFunc2(): Unit = {
    val list = 1 :: /*2 :: 3 ::*/ Nil

    val actual = list.map(p => p match {
      case 1 => "one"
    })

    assertEquals("one" :: Nil, actual)
  }
}
