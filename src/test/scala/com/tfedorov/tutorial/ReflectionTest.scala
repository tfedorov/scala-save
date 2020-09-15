package com.tfedorov.tutorial

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

import scala.collection.generic.CanBuildFrom
import scala.collection.immutable
import scala.reflect.runtime.universe

class ReflectionTest {

  @Test
  def typeErasuresManifests(): Unit = {
    class MakeFoo[A](implicit manifest: Manifest[A]) {
      //Runtime instead of it
      def make: A = manifest.erasure.newInstance.asInstanceOf[A]
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
  def createEmptyElement(): Unit = {
    import scala.reflect.runtime.universe._


    class Typer[T: TypeTag] {
      val tt = typeTag[T]

      //runtime instead of it
      def getEmptyElement(implicit cbf: CanBuildFrom[_, _, T]): T = {
        cbf().result()
      }
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
