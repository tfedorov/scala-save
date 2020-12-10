package com.tfedorov.tutorial.functions

import org.junit.jupiter.api.Assertions._
import org.junit.jupiter.api.Test

class FunctionsTest {

  @Test
  def objectFunc1(): Unit = {
    object addOne extends Function1[Int, Int] {
      override def apply(v1: Int): Int = v1 + 1
    }

    assertEquals(6, addOne(5))
  }

  @Test
  def objectFunc1ExtendsSyntax(): Unit = {
    object addOne extends (Int => Int) {
      override def apply(v1: Int): Int = v1 + 1
    }

    assertEquals(6, addOne(5))
  }

  @Test
  def objectFunc2ExtendsSyntax(): Unit = {
    object multipleString extends ((String, Int) => String) {
      override def apply(v1: String, v2: Int): String = v1 * v2
    }

    assertEquals("ababab", multipleString("ab", 3))
  }

  @Test
  def andThenF(): Unit = {
    def oneF(before: String): String = before + "1"

    def twoF(before: String): String = before + "2"
/*
    // in case of: val oneF: String => String
    //ERROR//val andThened: String => String = oneF.andThen(twoF)

    val andThened: String => String = {
      val f1: String => String = oneF
      f1.andThen(twoF(_))
    }

    val andThened: String => String = oneF _ andThen(el => twoF(el))
    val andThened: String => String = oneF _ andThen(twoF(_))
    val andThened: String => String = oneF _ andThen(twoF _)
    val andThened: String => String = oneF _ andThen twoF _

*/

    val andThened = oneF _ andThen twoF

    assertEquals("test12", andThened("test"))
    assertEquals("test12", twoF(oneF("test")))
    assertEquals("test12", (oneF _ andThen twoF) ("test"))
  }

  @Test
  def andThenVal(): Unit = {
    val oneF: String => String = _ + "1"
    val twoF: String => String = _ + "2"

    val andThened = oneF.andThen(twoF)

    assertEquals("test12", andThened("test"))
  }

  @Test
  def andThenSugared(): Unit = {

    val andThened: String => String = ((_: String) + "1") compose ((_: String) + "2")

    assertEquals("test21", andThened("test"))
  }

  @Test
  def composeF(): Unit = {
    def oneF(before: String): String = before + "1"

    def twoF(before: String): String = before + "2"

    /*
    val composed: String => String = {
      val f1: String => String = oneF
      f1.compose(twoF(_))
    }

    val composed: String => String = {
      val f1: String => String = oneF
      f1.compose(twoF)
    }
    val composed: String => String = oneF _ compose(el => twoF(el))
    val composed: String => String = oneF _ compose(twoF(_))
    val composed: String => String = oneF _ compose(twoF _)
    val composed: String => String = oneF _ compose twoF _
     */

    val composed: String => String = oneF _ compose twoF
    val actual = composed("test")

    assertEquals("test21", actual)
    assertEquals("test21", oneF(twoF("test")))
    assertEquals("test21", (oneF _ compose twoF _) ("test"))
  }

  @Test
  def composeFSugared(): Unit = {
    val composed: String => String = ((_: String) + "1") compose ((_: String) + "2")

    val actual = composed("test")

    assertEquals("test21", actual)
  }

  @Test
  def composeFChangedType(): Unit = {
    val plus5F: Int => Int = (before: Int) => before + 5

    val isPositiveF: Int => Boolean = (before: Int) => before >= 0

    val actualF: Int => Boolean = plus5F.andThen(isPositiveF)
    val actualResult1 = actualF(-5)
    val actualResult2 = actualF(-4)

    assertTrue(actualResult1)
    assertTrue(actualResult2)
  }


  @Test
  def funcPatternMatching(): Unit = {
    //val func: Double => Double = math.exp
    //val func = math.exp(_)
    val func: Double => Double = math exp _

    val actualValue: Double = func match {
      //case f: Function1[Double, Double] => f(10)
      case f: (Double => Double) => f(3)
      case _ => Double.MinValue
    }

    assertEquals(20.085536923187668, actualValue)
  }

  @Test
  def funcPatternMatchingNamed(): Unit = {
    class AddTwo extends (Double => Double) {
      def apply(m: Double): Double = m + 2
    }

    val plus2: Double => Double = new AddTwo()
    val sqrtAlias: Double => Double = math.sqrt

    def matchF(inputF: Double => Double): Double = inputF match {
      case fAdd2: AddTwo => fAdd2(2)
      case fAnother: (Double => Double) => fAnother(25)
      case _ => Int.MinValue.toDouble
    }

    val actualValue1 = matchF(plus2)
    val actualValue2 = matchF(sqrtAlias)

    assertEquals(4.0, actualValue1)
    assertEquals(5.0, actualValue2)
  }

}
