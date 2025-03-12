package com.tfedorov.tutorial.`type`.typeclass

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class AuxPatternTest {


  @Test
  def auxCustom(): Unit = {
    case class Cat(name: String)
    case class Dog(nick: String)
    trait Known[A] {
      type B

      def value: B
    }
    object Known {
      type Aux[A0, B0] = Known[A0] {type B = B0}

      implicit def _fInt = new Known[Int] {
        type B = String
        val value = "Int -> String"
      }

      implicit def _fCat: Known[Cat] {type B = Int} = new Known[Cat] {
        type B = Int
        val value = 1
      }

      implicit def _fDog = new Known[Dog] {
        type B = String
        val value = "Dog -> String"
      }
    }

    trait UnknownElement[A] {
      def unknownValue: String
    }

    implicit val _ueString = new UnknownElement[String] {

      override def unknownValue: String = "UnknownElement[String]"
    }
    implicit val _ueInt = new UnknownElement[Int] {

      override def unknownValue: String = "UnknownElement[Int]"
    }

    def coder[K, UN](known: K)(implicit foo: Known.Aux[K, UN], ue: UnknownElement[UN]): String = {
      "Inp param=" + known.getClass.getSimpleName + ";" +
        "knownValue=" + foo.value + ";" +
        "UnknownElement unknownValue =" + ue.unknownValue
    }

    //val actualResultInt = coder[Int, String](3)(Known._fInt, _ueString)
    val actualResultInt = coder(3)
    //val actualResultCat = coder[Cat,Int](Cat("Music"))(Known._fCat, _ueInt)
    val actualResultCat = coder(Cat("Music"))
    val actualResultDog = coder(Dog("Muhtar"))

    val expected = "Inp param=Integer;knownValue=Int -> String;UnknownElement unknownValue =UnknownElement[String]"
    assertEquals(expected, actualResultInt)
    assertEquals("Inp param=Cat$1;knownValue=1;UnknownElement unknownValue =UnknownElement[Int]", actualResultCat)
    assertEquals("Inp param=Dog$1;knownValue=Dog -> String;UnknownElement unknownValue =UnknownElement[String]", actualResultDog)
  }

  //https://gigiigig.github.io/posts/2015/09/13/aux-pattern.html
  @Test
  def aux(): Unit = {
    trait Foo[A] {
      type B

      def value: B
    }

    object Foo {
      type Aux[A0, B0] = Foo[A0] {type B = B0}

      implicit def fi = new Foo[Int] {
        type B = String
        val value = "Foo"
      }

      implicit def fs = new Foo[String] {
        type B = Boolean
        val value = false
      }
    }

    trait Monoid[A] {
      def combine(x: A, y: A): A

      def empty: A
    }
    implicit val _mString = new Monoid[String] {
      override def combine(x: String, y: String): String = x + " World"

      override def empty: String = ""
    }

    implicit val _mBoolean = new Monoid[Boolean] {
      override def combine(x: Boolean, y: Boolean): Boolean = x || y

      override def empty: Boolean = false
    }

    //Error:(35, 32) illegal dependent method type: parameter may only be referenced in a subsequent parameter section
    //def fooError[T](t: T)(implicit f: Foo[T], m: Monoid[f.B]): f.B = m.empty
    def emptier[T, R](t: T)(implicit f: Foo.Aux[T, R], m: Monoid[R]): R = m.empty

    //val actualResult: String = foo[Int, String](3)(Foo.fi, m)
    //val actualResult: String = foo[Int, String](3)
    val actualResultInt: String = emptier(3)
    val actualResultStr = emptier[String, Boolean]("Hello world")

    assertEquals("", actualResultInt)
    assertEquals(false, actualResultStr)
  }

}
