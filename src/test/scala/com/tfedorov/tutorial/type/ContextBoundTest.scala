package com.tfedorov.tutorial.`type`

import org.junit.jupiter.api.Assertions._
import org.junit.jupiter.api.Test

class ContextBoundTest {
  /*
    A context bound describes an implicit value, instead of view bound's implicit conversion.
     It is used to declare that for some type A, there is an implicit value of type B[A] available. The syntax goes like this:
     */
  @Test
  def contextBoundNo(): Unit = {
    def implIndex[A](a: A)(implicit evidence: List[A]) = evidence.indexOf(a)

    implicit val fruits: List[String] = List("apple", "orange", "lemon")

    val actualResult = implIndex("apple")

    assertEquals(0, actualResult)
  }

  @Test
  def contextBound(): Unit = {
    def implIndex[A: List](a: A) = implicitly[List[A]].indexOf(a)

    implicit val fruits: List[String]  = List("apple", "orange", "lemon")

    val actualResult = implIndex("orange")

    assertEquals(1, actualResult)
  }

  @Test
  def contextBoundClass(): Unit = {

    class ContextBound[A](val value: A)

    def methodWithCB[M: ContextBound](another: M): Boolean = implicitly[ContextBound[M]].value.equals(another)

    implicit val impl: ContextBound[String] = new ContextBound[String]("orange")
    implicit val implFloat: ContextBound[Float] = new ContextBound[Float](1f)

    assertTrue(methodWithCB("orange"))
    assertFalse(methodWithCB("apple"))
    assertTrue(methodWithCB(1f))
    assertFalse(methodWithCB(2f))
  }

  @Test
  def contextBoundChainAfter(): Unit = {
    class ContextBound1[A](val value1: A)
    class ContextBound2[B](val value2: B)
    def exist[M: ContextBound1 : ContextBound2](another: M): Boolean = implicitly[ContextBound1[M]].value1.equals(another) || implicitly[ContextBound2[M]].value2.equals(another)
    implicit val impl: ContextBound1[String] = new ContextBound1[String]("orange")
    implicit val implFloat: ContextBound2[String] = new ContextBound2[String]("apple")

    val actualResult = exist("orange")

    assertTrue(actualResult)
    assertTrue(exist("apple"))
    assertFalse(exist("lemon"))
  }

  @Test
  def contextBoundChainBefore(): Unit = {
    class ContextBound1[A](val value1: A)
    class ContextBound2[B](val value2: B)
    def exist[M](another: M)(implicit evidence1: ContextBound1[M],
                             evidence2: ContextBound2[M]): Boolean = evidence1.value1.equals(another) || evidence2.value2.equals(another)
    implicit val impl1: ContextBound1[String] = new ContextBound1[String]("orange")
    implicit val impl2: ContextBound2[String] = new ContextBound2[String]("apple")

    val actualResult = exist("orange")

    assertTrue(actualResult)
    assertTrue(exist("apple"))
    assertFalse(exist("lemon"))
  }
}
