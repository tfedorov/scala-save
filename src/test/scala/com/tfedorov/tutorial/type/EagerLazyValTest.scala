package com.tfedorov.tutorial.`type`

import org.junit.jupiter.api.Test

class EagerLazyValTest {

  @Test
  def eagerVal(): Unit = {
    abstract class A {
      val x1: String
      val x2: String = "mom"

      println("A: " + x1 + ", " + x2)
    }
    class B extends A {
      val x1: String = "hello"

      println("B: " + x1 + ", " + x2)
    }
    class C extends B {
      override val x2: String = "dad"

      println("C: " + x1 + ", " + x2)
    }
    new C
  }

  @Test
  def lazyVal(): Unit = {
    abstract class A {
      val x1: String
      lazy val x2: String = "mom"

      println("A: " + x1 + ", " + x2)
    }
    class B extends A {
      lazy val x1: String = "hello"

      println("B: " + x1 + ", " + x2)
    }
    class C extends B {
      override lazy val x2: String = "dad"

      println("C: " + x1 + ", " + x2)
    }

    new C
  }

  @Test
  def defs(): Unit = {
    abstract class A {
      def x1: () => String

      def x2: () => String = () => "mom"

      println("A: " + x1 + ", " + x2)
    }
    class B extends A {
      lazy val x1: () => String = () => "hello"

      println("B: " + x1 + ", " + x2)
    }
    class C extends B {
      override def x2: () => String = () => "dad"

      println("C: " + x1() + ", " + x2())
    }

    new C
  }
}
