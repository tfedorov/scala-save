package com.tfedorov.tutorial.functions

import org.junit.jupiter.api.Assertions.{assertEquals, assertFalse, assertTrue}
import org.junit.jupiter.api.Test

import scala.language.postfixOps

class FunctionSugarTest {

  @Test
  def valF(): Unit = {
    //    val input = new (Int => Int) {
    //      override def apply(v1: Int): Int = v1 * 3
    //    }
    //val input: Int => Int = _ * 3
    val input: Int => Int = (par: Int) => par * 3

    val actualResult = input(3)

    assertEquals(9, actualResult)
  }

  @Test
  def valFSugar(): Unit = {

    val actualResult = ((_: Int) * 3) (3)

    assertEquals(9, actualResult)
  }


  case class Container(inner: Int) {

    def add(a: Int): Int = inner + a

    def +(a: Int): Container = Container(add(a))

    def add2(a: Int, b: Int): Int = a + b
  }

  @Test
  def commaNotation(): Unit = {
    val container = Container(2)

    val actualResult = container.add(4)

    assertEquals(6, actualResult)
  }

  @Test
  def plusType(): Unit = {
    val container = Container(2)

    val actualResult = container + 4
    //val actualResult = container.+(4)

    assertEquals(Container(6), actualResult)
  }

  @Test
  def infixNotation(): Unit = {
    val container = Container(2)

    //val actualResult = container add (4)
    val actualResult = container add 4

    assertEquals(6, actualResult)
  }

  @Test
  def infixNotationList(): Unit = {
    val containers = Container(2) :: Container(3) :: Nil

    //val actualResult = containers.map(el => el.add(2))
    //val actualResult = containers.map(_.add(2))
    val actualResult = containers.map(_ add 2)

    assertEquals(4 :: 5 :: Nil, actualResult)
  }

  @Test
  def methodList(): Unit = {
    val containers = Container(2) :: Container(3) :: Nil

    def twiceValue(input: Container): Int = input.inner * 2

    //val actualResult = containers.map(el => twiceValue(el))
    //val actualResult = containers.map(twiceValue(_))
    //val actualResult = containers.map(twiceValue _)
    val actualResult = containers.map(twiceValue)

    assertEquals(4 :: 6 :: Nil, actualResult)
  }

  @Test
  def curriedList(): Unit = {
    val containers = Container(2) :: Container(3) :: Nil

    def valueMulti(multiplier: Int)(input: Container): Int = input.inner * multiplier

    //val actualResult = containers.map(el => valueMulti(3)(el))
    //val actualResult = containers.map(valueMulti(3)(_))
    val actualResult = containers.map(valueMulti(3))

    assertEquals(6 :: 9 :: Nil, actualResult)
  }

  @Test
  def curriedMethodBody(): Unit = {

    def checker[A](value: A)(f: A => Boolean): Boolean = f(value)

    val yesNoChecker: String => Boolean = checker(_)({
      case "yes" => true
      case "no" => false
    })
    val actualResultYes = yesNoChecker("yes")
    val actualResultNo = yesNoChecker("no")

    assertTrue(actualResultYes)
    assertFalse(actualResultNo)
    //scala.MatchError: maybe
    //assertFalse(yesNoChecker("maybe"))
  }

  @Test
  def curriedFuncList(): Unit = {
    val containers = Container(2) :: Container(3) :: Nil

    def valueMulti(multiplier: Int)(input: Container): Int = input.inner * multiplier

    //val multi3: Container => Int = valueMulti(3)(_)
    val multi3: Container => Int = valueMulti(3)

    val actualResult = containers.map(multi3)

    assertEquals(6 :: 9 :: Nil, actualResult)
  }


  @Test
  def fFuncList(): Unit = {
    val data = 2 :: 3 :: Nil

    def createContainer(value: Int): Container = Container(value)

    //def addF(value: Int): Int => Int = createContainer(value) add _
    def addF(value: Int): Int => Int = createContainer(value) add

    //val actualResult = data.map(el => addF(3)(el))
    //val actualResult = data.map(addF(3)(_))
    //val actualResult = data.map(addF(3))
    val actualResult = data map addF(3)

    assertEquals(5 :: 6 :: Nil, actualResult)
  }

}
