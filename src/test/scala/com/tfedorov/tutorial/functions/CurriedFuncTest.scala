package com.tfedorov.tutorial.functions

import org.junit.jupiter.api.Assertions.{assertEquals, assertFalse, assertTrue}
import org.junit.jupiter.api.Test

import scala.concurrent.{Await, ExecutionContext, ExecutionContextExecutor, Future}
import scala.language.postfixOps

class CurriedFuncTest {

  @Test
  def curriedF3(): Unit = {
    val curriedFunc: String => String => String = (a: String) => a + " " + _
    val minusParam: String => String = curriedFunc("Hello")

    val actualResult: String = minusParam("World")

    assertEquals("Hello World", actualResult)
  }

  @Test
  def curriedF3_2(): Unit = {
    val curriedFunc: (String, Int) => String => String = (a: String, b: Int) => a + " " + _ * b
    val minusParam: String => String = curriedFunc("Hello", 3)

    val actualResult: String = minusParam("World")

    println(actualResult)
    assertEquals("Hello WorldWorldWorld", actualResult)
  }

  @Test
  def curriedMethod(): Unit = {
    val func2: (String, String) => String = (s1: String, s2: String) => s1 + " " + s2
    val curriedFunc: String => String => String = func2.curried

    //val minusParam: String => String = func2("Hello",_)
    val minusParam: String => String = curriedFunc("Hello")

    val actualResult: String = minusParam("World")

    assertEquals("Hello World", actualResult)
  }

  @Test
  def curriedF3InF2(): Unit = {
    val func3Param: String => Int => Boolean = (s1: String) => s1.toInt > _
    val lessThenOneFunc: Int => Boolean = func3Param("1")

    val actual1: Boolean = func3Param("1")(0)
    val actual2: Boolean = func3Param("-2")(0)

    assertTrue(actual1)
    assertFalse(actual2)
    assertTrue(lessThenOneFunc(0))
  }

  @Test
  def curriedF3Rewrite(): Unit = {
    val func2: String => Int = (s: String) => s.toInt
    val func3: String => Int => Boolean = (s1: String) => func2(s1) > _

    val actual1 = func3("1")(0)
    val actual2 = func3("-2")(0)

    assertTrue(actual1)
    assertFalse(actual2)
  }

  @Test
  def curriedF3Future(): Unit = {
    implicit val ec: ExecutionContextExecutor = ExecutionContext.global
    val func2: (String, Int) => Future[Boolean] = (a: String, b: Int) => {
      Future {
        a.toInt > b
      }(ec)
    }
    val curriedFunc: String => Int => Future[Boolean] = func2.curried

    val actual1: Future[Boolean] = curriedFunc("3")(1)
    val actual2: Future[Boolean] = curriedFunc("3")(4)

    import scala.concurrent.duration._
    assertTrue(Await.result(actual1, 1 seconds))
    assertFalse(Await.result(actual2, 1 seconds))
  }
}
