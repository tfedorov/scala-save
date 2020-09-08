package com.tfedorov.tutorial

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

//https://www.lihaoyi.com/post/ImplicitDesignPatternsinScala.html
class TypeClassJson {

  sealed trait Json

  object Json {

    case class Str(s: String) extends Json

    case class Num(value: Double) extends Json

    // ... many more definitions
  }

  def convertToJson(x: Any): Json = {
    x match {
      case s: String => Json.Str(s)
      case d: Double => Json.Num(d)
      case i: Int => Json.Num(i.toDouble)
      // maybe more cases for float, short, etc.
    }
  }

  @Test
  def typeClassSimple(): Unit = {

    val resString: Json = convertToJson("hello")
    val resNum: Json = convertToJson(1234)
    //scala.MatchError: . (of class java.io.File)
    //convertToJson(new java.io.File("."))

    assertEquals(Json.Str("hello"), resString)
    assertEquals(Json.Num(1234), resNum)
  }


  @Test
  def typeClassImplicit(): Unit = {

    trait Jsonable[T] {
      def serialize(t: T): Json
    }

    object Jsonable {

      implicit object StringJsonable extends Jsonable[String] {
        def serialize(t: String): Json = Json.Str(t)
      }

      implicit object DoubleJsonable extends Jsonable[Double] {
        def serialize(t: Double): Json = Json.Num(t)
      }

      implicit object IntJsonable extends Jsonable[Int] {
        def serialize(t: Int): Json = Json.Num(t.toDouble)
      }

    }
    def convertToJson[T](x: T)(implicit converter: Jsonable[T]): Json = {
      converter.serialize(x)
    }

    val actualResult = convertToJson("hello")
    val actualResultExp = convertToJson("hello")(Jsonable.StringJsonable)
    val actualIntResult = convertToJson(123)

    assertEquals(Json.Str("hello"), actualResult)
    assertEquals(Json.Str("hello"), actualResultExp)
    assertEquals(Json.Num(123), actualIntResult)
  }

  @Test
  def methodOverloading(): Unit = {
    // Instead of
    //def convertToJson(t: String) = Json.Str(t)
    //def convertToJson(t: Double) = Json.Num(t)
    //def convertToJson(t: Int) = Json.Num(t.toDouble)

    //def convertToJsonAndPrint(t: String) = println(convertToJson(t))
    //def convertToJsonAndPrint(t: Double) = println(convertToJson(t))
    //def convertToJsonAndPrint(t: Int) = println(convertToJson(t))

    //def convertMultipleItemsToJson(t: Array[String]) = t.map(convertToJson)
    //def convertMultipleItemsToJson(t: Array[Double]) = t.map(convertToJson)
    //def convertMultipleItemsToJson(t: Array[Int]) = t.map(convertToJson)
    //def convertToJson[T: Jsonable](x: T): Json = {
    //  implicitly[Jsonable[T]].serialize(x)
    //}

    trait Jsonable[T] {
      def serialize(t: T): Json
    }

    object Jsonable {

      implicit object StringJsonable extends Jsonable[String] {
        def serialize(t: String): Json = Json.Str(t)
      }

      implicit object DoubleJsonable extends Jsonable[Double] {
        def serialize(t: Double): Json = Json.Num(t)
      }

      implicit object IntJsonable extends Jsonable[Int] {
        def serialize(t: Int): Json = Json.Num(t.toDouble)
      }

    }

    def convertToJson[T](x: T)(implicit converter: Jsonable[T]): Json = converter.serialize(x)

    def convertMultipleItemsToJson[T: Jsonable](t: Array[T]): Array[Json] = t.map(convertToJson(_))

    val actualStrResult: Array[Json] = convertMultipleItemsToJson(Array("Hello", "world"))
    //val actualStrResultExp: Array[Json] = convertMultipleItemsToJson(Array("Hello", "world"))(Jsonable.StringJsonable)
    val actualIntResult: Array[Json] = convertMultipleItemsToJson(Array(1, 2))

    assertEquals(Json.Str("Hello") :: Json.Str("world") :: Nil, actualStrResult.toList)
    assertEquals(Json.Num(1) :: Json.Num(2) :: Nil, actualIntResult.toList)
  }

  @Test
  def derivedImplicits(): Unit = {
    sealed trait Json
    object Json {

      case class Str(s: String) extends Json

      case class Num(value: Double) extends Json

      case class List(items: Seq[Json]) extends Json

      // ... many more definitions
    }
    trait Jsonable[T] {
      def serialize(t: T): Json
    }

    object Jsonable {

      implicit object StringJsonable extends Jsonable[String] {
        def serialize(t: String): Json = Json.Str(t)
      }

      implicit object DoubleJsonable extends Jsonable[Double] {
        def serialize(t: Double): Json = Json.Num(t)
      }

      implicit object IntJsonable extends Jsonable[Int] {
        def serialize(t: Int): Json = Json.Num(t.toDouble)
      }

      implicit object SeqJsonable extends Jsonable[Seq[_]] {
        override def serialize(t: Seq[_]): Json = Json.List(t.map {
          _ match {
            case t: Int => IntJsonable.serialize(t)
            case d: Double => DoubleJsonable.serialize(d)
            case s: String => StringJsonable.serialize(s)
          }
        }
        )
      }

    }

    def convertToJsonSoph[T](x: T)(implicit converter: Jsonable[T]): Json = {
      x match {
        case s: String => Json.Str(s)
        case d: Double => Json.Num(d)
        case i: Int => Json.Num(i.toDouble)
        case s: Seq[_] => Jsonable.SeqJsonable.serialize(s)

      }

      // maybe more cases for float, short, etc.
    }

    val actualResult = convertToJsonSoph(Seq(1, "home", 3.0))

    assertEquals(Json.List(List(Json.Num(1.0), Json.Str("home"), Json.Num(3.0))), actualResult)

  }
}