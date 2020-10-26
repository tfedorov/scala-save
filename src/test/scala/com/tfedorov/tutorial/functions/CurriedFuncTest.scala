package com.tfedorov.tutorial.functions

import org.junit.jupiter.api.Assertions.{assertEquals, assertFalse, assertTrue}
import org.junit.jupiter.api.Test

import scala.concurrent.{Await, ExecutionContext, Future}

class CurriedFuncTest {

  @Test
  def curriedF3(): Unit = {
    val curriedFunc: String => String => String = (a: String) => a + " " + _
    val minusParam: String => String = curriedFunc("Hello")

    val actualResult: String = minusParam("World")

    assertEquals("Hello World", actualResult)
  }

  @Test
  def curriedParams(): Unit = {
    val func2: (String, String) => String = (s1: String, s2: String) => s1 + " " + s2
    val curriedFunc: String => String => String = func2.curried
    //val minusParam: String => String = func2("Hello",_)
    val minusParam: String => String = curriedFunc("Hello")

    val actualResult: String = minusParam("World")

    assertEquals("Hello World", actualResult)
  }

  @Test
  def curriedF3InF2(): Unit = {
    val func3: String => Int => Boolean = (s1: String) => s1.toInt > _
    val curriedFunc: Int => Boolean = func3("1")

    val actual1 = func3("1")(0)
    val actual2 = func3("-2")(0)

    assertTrue(actual1)
    assertFalse(actual2)
    assertEquals(actual1, curriedFunc(0))
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
    implicit val ec = ExecutionContext.global
    val func2: (String, Int) => Future[Boolean] = (a: String, b: Int) => {
      Future {
        a.toInt > b
      }
    }
    val curriedFunc: String => Int => Future[Boolean] = func2.curried

    val actual1: Future[Boolean] = curriedFunc("3")(1)
    val actual2: Future[Boolean] = curriedFunc("3")(4)

    import scala.concurrent.duration._
    assertTrue(Await.result(actual1, 1 seconds))
    assertFalse(Await.result(actual2, 1 seconds))
  }
}
