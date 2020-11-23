package com.tfedorov.tutorial.`type`

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

      override def generateB: B = "Hello"
    }

    trait Generic[B] {
      def generateB: B
    }

    class GenericImpl extends Generic[String] {
      override def generateB: String = "Hello"
    }

    assertEquals(new GenericImpl().generateB, new PureImpl().generateB)
  }
}
