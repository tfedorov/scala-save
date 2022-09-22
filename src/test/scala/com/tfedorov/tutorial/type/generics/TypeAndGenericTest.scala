package com.tfedorov.tutorial.`type`.generics

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class TypeAndGenericTest {

  @Test
  def exampleStructural(): Unit = {
    trait Pure {
      type B

      def generateB: B
    }
    class PureImpl extends Pure {
      override type B = String

      override def generateB: B = "Hello Pure"
    }
    trait Generic[B] {
      def generateB: B
    }
    class GenericImpl extends Generic[String] {
      override def generateB: String = "Hello Generic"
    }
    class GenericIntImp extends Generic[Int] {
      override def generateB: Int = 0
    }


    assertEquals("Hello", new PureImpl().generateB)
    assertEquals(0, new GenericIntImp().generateB)
  }
}
