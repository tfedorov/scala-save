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

    val actualOnce: PartialFunction[Int, String] = oneF orElse twoF
    //val actualFromList = list.collect(oneF.orElse(twoF))
    val actualFromList = list.collect(oneF orElse twoF)

    assertEquals("one", actualOnce(1))
    assertEquals("two", actualOnce(2))
    assertEquals("one" :: "two" :: "one" :: Nil, actualFromList)
    assertEquals("one" :: "two" :: "one" :: Nil, list.collect(actualOnce))
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

    val actual = list.map {
      case 1 => "one"
    }

    assertEquals("one" :: Nil, actual)
  }

  @Test
  def orElseFCompose(): Unit = {
    val input: Seq[Char] = 'A' to 'Z'
    val vovelF: PartialFunction[Char, Int] = {
      case vovel if "AEIOU".contains(vovel) => -1
    }

    val actualResult = input.sortBy(vovelF orElse { case _ => 1 })
    val actualResult2 = input.map(vovelF orElse { case _ => 1 })

    assertEquals(Seq('A', 'E', 'I', 'O', 'U', 'B', 'C', 'D', 'F', 'G', 'H', 'J', 'K', 'L', 'M', 'N', 'P', 'Q', 'R', 'S', 'T', 'V', 'W', 'X', 'Y', 'Z'), actualResult)
    assertEquals(Vector(-1, 1, 1, 1, -1, 1, 1, 1, -1, 1, 1, 1, 1, 1, -1, 1, 1, 1, 1, 1, -1, 1, 1, 1, 1, 1), actualResult2)
  }

  @Test
  def orElseAppyFCompose(): Unit = {
    val input: Seq[Char] = 'A' to 'Z'

    val vovelF: PartialFunction[Char, Int] = {
      case vovel if "aeiou".contains(vovel) => -1
    }

    val isCF: PartialFunction[Char, Int] = {
      case _ => 1
    }

    val changedF: Char => Int = (ch: Char) => vovelF.applyOrElse(ch.toLower, isCF(_))
    val actualResult = input.sortBy(changedF)

    assertEquals(Seq('A', 'E', 'I', 'O', 'U', 'B', 'C', 'D', 'F', 'G', 'H', 'J', 'K', 'L', 'M', 'N', 'P', 'Q', 'R', 'S', 'T', 'V', 'W', 'X', 'Y', 'Z'), actualResult)

  }

  @Test
  def lift(): Unit = {
    val vovelF: PartialFunction[Char, Int] = {
      case vovel if "aeiou".contains(vovel) => -1
    }

    val actualResult = vovelF.lift('B')
    val actualResult2 = vovelF.lift('a')

    assertEquals(None, actualResult)
    assertEquals(Some(-1), actualResult2)
  }

  @Test
  def runWith(): Unit = {
    val vovelF: PartialFunction[Char, Int] = {
      case vovel if "aeiou".contains(vovel) => -1
    }

    var sideEffect = ""
    val actualResult = ('a' to 'f').map(vovelF.runWith { el =>
      sideEffect += el
      el
    })

    assertEquals(Vector(true, false, false, false, true, false), actualResult)
    assertEquals("-1-1", sideEffect)
  }
}
