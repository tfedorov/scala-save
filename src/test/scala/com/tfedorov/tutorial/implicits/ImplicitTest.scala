package com.tfedorov.tutorial.implicits

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

import java.util.Date
import scala.collection.immutable

//https://twitter.github.io/scala_school/advanced-types.html
class ImplicitTest {

  /*
  A view bound specifies a type that can be “viewed as” another.
   */
  @Test
  def viewBounds(): Unit = {
    implicit def strToInt(x: String) = x.toInt

    class Container[A <% Int] {
      def addIt(x: A): Int = 123 + x
    }

    val strResult: Int = new Container[String].addIt("123")
    //But not
    //val strResultWrong: Int = new Container().addIt("123")
    val intResult: Int = new Container[Int].addIt(123)
    //new Container[Float].addIt(123.0F)
    //Error:(18, 5) not enough arguments for constructor Container: (implicit evidence$1: Float => Int)Container[Float].
    //Unspecified value parameter evidence$1.
    //    new Container[Float].addIt(123.0F)

    assertEquals(246, strResult)
    assertEquals(246, intResult)
  }

  @Test
  def viewBoundsMethod(): Unit = {
    implicit def strToInt(x: String) = x.toInt

    //val actual = math.max(strToInt("123"), 111)
    val actual = math.max("123", 111)
    //But not
    //val actualWrong = math.max("123", new Date())

    assertEquals(123, actual)
  }


}
