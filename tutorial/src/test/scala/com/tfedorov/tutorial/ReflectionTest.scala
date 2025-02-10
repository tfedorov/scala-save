package com.tfedorov.tutorial

import org.junit.jupiter.api.Assertions.{assertEquals, assertTrue}
import org.junit.jupiter.api.Test

import scala.collection.generic.CanBuildFrom
import scala.collection.immutable
import scala.reflect.runtime.universe

class ReflectionTest {

  @Test
  def reflectGenericParamTest(): Unit = {
    def displayGeneric[T](arg: T)(implicit m: reflect.Manifest[T]): String = s"${arg.toString} : ${m.toString}"

    val actualResultStr = displayGeneric("text")
    val actualResultInt = displayGeneric(42)
    val actualResultFun = displayGeneric((_: String).toUpperCase)

    assertEquals("text : java.lang.String", actualResultStr)
    assertEquals("42 : Int", actualResultInt)
    assertTrue(actualResultFun.endsWith("scala.Function1[java.lang.String, java.lang.String]"))
  }

  @Test
  def typeErasuresManifests(): Unit = {
    class MakeFoo[A](implicit manifest: Manifest[A]) {
      //Runtime instead of it
      def make: A = manifest.runtimeClass.newInstance.asInstanceOf[A]
    }

    val actualResult = new MakeFoo[String].make

    assertEquals(new String(), actualResult)
  }

  @Test
  def typeRuntimeErasures(): Unit = {
    import scala.reflect.runtime.{universe => ru}
    class Typer[T: ru.TypeTag] {
      //runtime instead of it
      def getType: ru.TypeTag[T] = ru.typeTag[T]
    }

    val actualResult: ru.Type = new Typer[String]().getType.tpe

    assertEquals(ru.typeOf[String], actualResult)
  }

  @Test
  def createEmptyElementManifest(): Unit = {
    def arr[T](implicit m: Manifest[T]): Seq[T] = {

      if (m.toString().equals("Float"))
        return Seq(0L).asInstanceOf[Seq[T]]

      if (m.toString().equals("Int"))
        return Seq(0).asInstanceOf[Seq[T]]

      if (m.toString().equals("java.lang.String"))
        return Seq("").asInstanceOf[Seq[T]]

      Seq.empty[T]
    }

    val actualResultFloat = arr[Float]
    val actualResultInt = arr[Int]
    val actualResultStr = arr[String]
    assertEquals(0L :: Nil, actualResultFloat)
    assertEquals(0 :: Nil, actualResultInt)
    assertEquals("" :: Nil, actualResultStr)
  }

  @Test
  def createEmptyElement(): Unit = {
    import scala.reflect.runtime.universe._

    class Typer[T: TypeTag] {
      //runtime instead of it
      def getEmptyElement(implicit cbf: CanBuildFrom[_, _, T]): T = cbf().result()
    }

    val actualResultStr = new Typer[String]().getEmptyElement
    val actualResultList = new Typer[List[_]]().getEmptyElement

    assertEquals(new String(), actualResultStr)
    assertEquals(List.empty, actualResultList)
  }

  @Test
  def paramInfo: Unit = {
    import scala.reflect.runtime.universe._

    def paramInfo[T](x: T)(implicit tag: TypeTag[T]): String = {
      val targs = tag.tpe match {
        case TypeRef(_, _, args: immutable.Seq[universe.Type]) => args
      }
      s"type of $x has type arguments $targs"
    }

    val actualIntResult = paramInfo(42)
    val actualListResult = paramInfo(List(1, 2))
    //class Foo {}
    //val actualClasstResult = paramInfo(List(new Foo, new Foo))
    //Error:(79, 39) No TypeTag available for List[Foo]
    //val actualClassResult = paramInfo(List(new Foo, new Foo))

    assertEquals("type of 42 has type arguments List()", actualIntResult)
    assertEquals("type of List(1, 2) has type arguments List(Int)", actualListResult)
  }
}
