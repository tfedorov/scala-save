package com.tfedorov.tutorial.`type`

import scala.collection.immutable

class EnumADTTest {

  import org.junit.jupiter.api.Assertions.assertEquals
  import org.junit.jupiter.api.Test

  @Test
  def ADTTest(): Unit = {

    sealed abstract class Http extends Product with Serializable

    object Http {

      final case object Ok extends Http

      final case object Error extends Http

      final case object Fail extends Http

    }

    def response(number: Int): Http = number match {
      case fNum if fNum >= 200 && fNum < 300 => Http.Ok
      case fNum if fNum >= 400 && fNum < 500 => Http.Fail
      case fNum if fNum >= 500 => Http.Error
    }

    assertEquals(Http.Fail, response(404))
    assertEquals(Http.Ok, response(200))
  }

  @Test
  def ADTProductTest(): Unit = {

    sealed abstract class Http

    object Http {

      final case object Ok extends Http

      final case object Error extends Http

      final case object Fail extends Http

    }

    val list: List[Product with Serializable with Http] = Http.Ok :: Http.Error :: Http.Fail :: Nil

  }
}
