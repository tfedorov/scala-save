package com.tfedorov.tutorial.functions

import org.junit.jupiter.api.Assertions._
import org.junit.jupiter.api.Test

class AndThenComposeTest {

  @Test
  def andThenViaFunc(): Unit = {
    def oneF(before: String): String = before + "1"

    def twoF(before: String): String = before + "2"

    //   val f2Val: String => String = oneF
    //   val andThenedF = f2Val.andThen(twoF)
    //val andThenedF: String => String = (oneF(_)).andThen(el => twoF(el))
    //val andThenedF: String => String = (oneF(_)).andThen(el => twoF(el))
    //   val andThenedF: String => String = oneF _ andThen(el => twoF(el))
    //   val andThenedF: String => String = oneF _ andThen(twoF(_))
    //   val andThenedF: String => String = oneF _ andThen(twoF _)
    //   val andThenedF: String => String = oneF _ andThen twoF _
    val andThenedF = oneF _ andThen twoF

    assertEquals("test12", andThenedF("test"))
    assertEquals("test12", twoF(oneF("test")))
    assertEquals("test12", (oneF _ andThen twoF) ("test"))
  }

  @Test
  def andThenViaVal(): Unit = {
    val oneValF: String => String = _ + "1"
    val twoValF: String => String = _ + "2"

    //val andThenedF = oneValF.andThen(twoValF)
    val andThenedF = oneValF andThen twoValF

    assertEquals("test12", andThenedF("test"))
  }

  @Test
  def andThenFChangedType(): Unit = {
    val plus5F: Int => Int = (before: Int) => before + 5

    val isPositiveF: Int => Boolean = (before: Int) => before >= 0

    val andThenF: Int => Boolean = plus5F.andThen(isPositiveF)

    val actualResult1 = andThenF(-5)
    val actualResult2 = andThenF(-4)

    assertTrue(actualResult1)
    assertTrue(actualResult2)
  }

  @Test
  def composeF(): Unit = {
    def oneF(before: String): String = before + "1"

    def twoF(before: String): String = before + "2"

    //val f2Val: String => String = oneF
    //val composedF = f2Val.compose(twoF)
    //val composedF = f2Val compose twoF
    //    val composedF: String => String = oneF _ compose(el => twoF(el))
    //    val composedF: String => String = oneF _ compose(twoF(_))
    //    val composedF: String => String = oneF _ compose(twoF _)
    //    val composedF: String => String = oneF _ compose twoF _
    val composedF: String => String = oneF _ compose twoF

    assertEquals("test21", composedF("test"))
    assertEquals("test21", oneF(twoF("test")))
    assertEquals("test21", (oneF _ compose twoF) ("test"))
  }

  @Test
  def composeFSugared(): Unit = {
    val composedF: String => String = ((_: String) + "1") compose ((_: String) + "2")

    val actual = composedF("test")

    assertEquals("test21", actual)
  }

}
