package com.tfedorov.tutorial

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class TypeClassPattern {

  trait Show[A] {
    def show(a: A): String
  }

  @Test
  def typeClassPatternInt(): Unit = {
    implicit val intCanShow: Show[Int] =
      new Show[Int] {
        def show(int: Int): String = s"int $int"
      }

    //def show[A: Show](a: A): String = implicitly[Show[A]].show(a)
    def show[A](a: A)(implicit sh: Show[A]): String = sh.show(a)

    assertEquals("int 20", show(20))
  }

  @Test
  def typeClassPatternFloat(): Unit = {

    //def show[A: Show](a: A): String = implicitly[Show[A]].show(a)
    def show[A](a: A)(implicit sh: Show[A]): String = sh.show(a)

    implicit val floatCanShow: Show[Float] =
      new Show[Float] {
        def show(float: Float): String = s"float $float"
      }

    assertEquals("float 20.0", show(20f))
  }

  object Show {
    def apply[A](implicit sh: Show[A]): Show[A] = sh
  }

  @Test
  def typeClassPatternOps(): Unit = {
    implicit val intCanShow: Show[Int] =
      new Show[Int] {
        def show(int: Int): String = s"int $int"
      }
    /*
    implicit class ShowOps[A: Show](a: Int) {
      def show: String = Show.apply[Int](intCanShow).show(a)
    }
    */
    implicit class ShowOps[A: Show](a: A) {
      def show: String = Show[A].show(a)
    }

    assertEquals("int 30", new ShowOps(30).show)
    assertEquals("int 30", ShowOps(30).show)
    assertEquals("int 30", 30.show)
  }

  @Test
  def typeClassPatternOpsString(): Unit = {
    implicit val intCanShow: Show[Int] = (int: Int) => s"int $int"

    implicit val stringCanShow: Show[String] =
      new Show[String] {
        def show(str: String): String = s"string $str"
      }
    implicit class ShowOps[A: Show](a: A) {
      def show: String = Show[A].show(a)
    }
    assertEquals("int 30", 30.show)
    assertEquals("string 30", "30".show)
  }

  @Test
  def typeClassPatternOpsImport(): Unit = {
    object Show {
      def apply[A](implicit sh: Show[A]): Show[A] = sh

      def show[A: Show](a: A): String = Show[A].show(a)

      object ops {

        implicit class ShowOps[A: Show](a: A) {
          def show: String = Show[A].show(a)
        }

      }

    }
    implicit val intCanShow: Show[Int] =
      new Show[Int] {
        def show(int: Int): String = s"int $int"
      }
    import Show.ops._
    assertEquals("int 30", new ShowOps(30).show)
    assertEquals("int 30", ShowOps(30).show)
    assertEquals("int 30", 30.show)
  }

}
