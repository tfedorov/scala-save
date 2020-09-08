package com.tfedorov.tutorial

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.scalatest.Ignore

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

  @Ignore
  @Test
  def createEmptyElement(): Unit = {
    import scala.reflect.runtime._
    import scala.reflect.runtime.universe._


    class Typer[T: TypeTag] {
      val tt = typeTag[T]

      //runtime instead of it
      def getEmptyElement: T = {
        val members: universe.MemberScope = tt.tpe.members
        val iterator = members.filter(m => m.isMethod && m.asMethod.isConstructor).iterator.toList
        val constructor = iterator.head.asMethod

        currentMirror
          .reflectClass(tt.tpe.typeSymbol.asClass)
          // error is here
          .reflectConstructor(constructor)().asInstanceOf[T]
      }
    }

    val actualResult = new Typer[String]().getEmptyElement

    assertEquals(new String(), actualResult)
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
