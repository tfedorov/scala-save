package com.tfedorov.tutorial.functions

import org.junit.jupiter.api.Assertions._
import org.junit.jupiter.api.Test

class FunctionsTest {

  @Test
  def objectFunc1Example(): Unit = {
    object addOne extends Function1[Int, Int] {
      override def apply(v1: Int): Int = v1 + 1
    }

    assertEquals(6, addOne(5))
  }

  @Test
  def syntaxFunc1Example(): Unit = {
    object addOne extends (Int => Int) {
      override def apply(v1: Int): Int = v1 + 1
    }

    assertEquals(6, addOne(5))
  }

  @Test
  def syntaxFunc2Example(): Unit = {
    object multipleString extends ((String, Int) => String) {
      override def apply(v1: String, v2: Int): String = v1 * v2
    }

    assertEquals("ababab", multipleString("ab", 3))
  }

  @Test
  def funcPatternMatching(): Unit = {
    //val func: Double => Double = math.exp
    //val func = math.exp(_)
    val func = math exp _

    val actualValue = func match {
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

    val actualValue = plus2 match {
      case f: AddTwo => f(2)
      case f: (Double => Double) => f(25)
      case _ => Int.MinValue
    }
    assertEquals(4.0, actualValue)
  }

  @Test
  def funcPatternMatchingNamed2(): Unit = {
    class AddTwo extends (Double => Double) {
      def apply(m: Double): Double = m + 2
    }

    val sqrtAlias: Double => Double = math.sqrt

    val actualValue = sqrtAlias match {
      case fAdd2: AddTwo => fAdd2(2)
      case fDouble: (Double => Double) => fDouble(25)
      case _ => Int.MinValue
    }
    assertEquals(5.0, actualValue)
  }

  @Test
  def andThenF(): Unit = {
    def oneF(before: String): String = before + "1"

    def twoF(before: String): String = before + "2"

    /*
    val composed: String => String = {
      val f1: String => String = oneF
      f1.andThen(twoF(_))
    }


    val composed: String => String = {
      val f1: String => String = oneF
      f1.andThen(twoF)
    }
    val composed: String => String = oneF _ andThen(el => twoF(el))
    val composed: String => String = oneF _ andThen(twoF(_))
    val composed: String => String = oneF _ andThen(twoF _)
    val composed: String => String = oneF _ andThen twoF _

     */
    val composed: String => String = oneF _ andThen twoF

    val actual = composed("test")

    assertEquals("test12", actual)
    assertEquals("test12", twoF(oneF("test")))
    assertEquals("test12", (oneF _ andThen twoF _) ("test"))
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
  def composeF2(): Unit = {
    val composed: String => String = ((_: String) + "1") compose ((_: String) + "2")

    val actual = composed("test")

    assertEquals("test21", actual)
  }

  @Test
  def curriedF1(): Unit = {
    val curriedFunc: String => String => String = (a: String) => a + " " + _

    val actual: String => String = curriedFunc("Hello")
    val actualResult: String = actual("World")

    assertEquals("Hello World", actualResult)
  }

  @Test
  def curriedF2(): Unit = {
    val func2: (String, String) => String = (a: String, b: String) => a + " " + b
    val curriedFunc: String => String => String = func2.curried

    val actual: String => String = curriedFunc("Hello")
    val actualResult: String = actual("World")

    assertEquals("Hello World", actualResult)
  }

}
