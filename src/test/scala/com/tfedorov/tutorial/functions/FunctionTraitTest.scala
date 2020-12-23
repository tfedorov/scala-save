package com.tfedorov.tutorial.functions

import org.junit.jupiter.api.Assertions._
import org.junit.jupiter.api.Test

class FunctionTraitTest {

  @Test
  def extendsFunction1(): Unit = {
    object addOne extends Function1[Int, Int] {
      override def apply(v1: Int): Int = v1 + 1
    }

    assertEquals(6, addOne(5))
  }

  @Test
  def extendsFunc1Syntax(): Unit = {
    object addOne extends (Int => Int) {
      override def apply(v1: Int): Int = v1 + 1
    }

    assertEquals(6, addOne(5))
  }

  @Test
  def extendsFunc2Syntax(): Unit = {
    object multipleString extends ((String, Int) => String) {
      override def apply(v1: String, v2: Int): String = v1 * v2
    }

    assertEquals("ababab", multipleString("ab", 3))
  }

  @Test
  def funcPatternMatching(): Unit = {
    //val func: Double => Double = math.exp
    //val func = math.exp(_)
    val func: Double => Double = math exp _

    val actualValue: Double = func match {
      //case f: Function1[Double, Double] => f(10)
      case f: Double => Double => f(3)
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
      case fAnother: Double => Double => fAnother(25)
      case _ => Int.MinValue.toDouble
    }

    val actualValue1 = matchF(plus2)
    val actualValue2 = matchF(sqrtAlias)

    assertEquals(4.0, actualValue1)
    assertEquals(5.0, actualValue2)
  }

}
