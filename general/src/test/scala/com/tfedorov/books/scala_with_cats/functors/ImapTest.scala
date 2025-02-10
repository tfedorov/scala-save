package com.tfedorov.books.scala_with_cats.functors

import org.junit.jupiter.api.Assertions._
import org.junit.jupiter.api.Test

class ImapTest {

  @Test
  def exampleString(): Unit = {
    trait Codec[A] {
      def encode(value: A): String

      def decode(value: String): A

      def imap[B](dec: A => B, enc: B => A): Codec[B] = {
        val self = this
        new Codec[B] {
          def encode(value: B): String =
            self.encode(enc(value))

          def decode(value: String): B =
            dec(self.decode(value))
        }
      }
    }
    implicit val stringCodec: Codec[String] = new Codec[String] {
      override def encode(value: String): String = s"{value: '$value'}"

      override def decode(value: String): String = value.substring("{value: '".length, value.length - 2)
    }

    val actualResult1 = stringCodec.encode("abc")
    val actualResult2 = stringCodec.decode("{value: 'abc'}")

    assertEquals("{value: 'abc'}", actualResult1)
    assertEquals("abc", actualResult2)
  }

  @Test
  def exampleInt(): Unit = {
    trait Codec[A] {
      def encode(value: A): String

      def decode(value: String): A

      def imap[B](decF: A => B, encF: B => A): Codec[B] = {
        val self = this
        new Codec[B] {
          def encode(value: B): String = self.encode(encF(value))

          def decode(value: String): B = {
            decF(self.decode(value))
          }
        }
      }
    }
    implicit val stringCodec: Codec[String] = new Codec[String] {
      override def encode(value: String): String = s"{value: '$value'}"

      override def decode(value: String): String = value.substring("{value: '".length, value.length - 2)
    }
    implicit val intCodec: Codec[Int] = stringCodec.imap(_.toInt, _.toString)
    implicit val booleanCodec: Codec[Boolean] = stringCodec.imap(_.toBoolean, _.toString)

    val actualResult1: String = intCodec.encode(3)
    val actualResult2: Int = intCodec.decode("{value: '3'}")
    val actualResult3: String = booleanCodec.encode(true)
    val actualResult4: Boolean = booleanCodec.decode("{value: 'true'}")

    assertEquals("{value: '3'}", actualResult1)
    assertEquals(3, actualResult2)
    assertEquals("{value: 'true'}", actualResult3)
    assertTrue(actualResult4)
  }

  @Test
  def exampleCustom(): Unit = {
    trait Codec[A] {
      def encode(value: A): String

      def decode(value: String): A

      def imap[B](dec: A => B, enc: B => A): Codec[B] = {
        val self = this
        new Codec[B] {
          def encode(value: B): String =
            self.encode(enc(value))

          def decode(value: String): B =
            dec(self.decode(value))
        }
      }
    }
    implicit val stringCodec: Codec[String] = new Codec[String] {
      override def encode(value: String): String = s"{value: '$value'}"

      override def decode(value: String): String = value.substring("{value: '".length, value.length - 2)
    }
    case class Box[A](value: A)
    implicit def boxCodec[A](implicit c: Codec[A]): Codec[Box[A]] = c.imap[Box[A]](Box(_), _.value)

    val actualResult1 = boxCodec[String].encode(Box("inside"))
    val actualResult2: Box[String] = boxCodec[String].decode("{value: 'inside'}")
    implicit val floatCodec: Codec[Double] = stringCodec.imap(_.toDouble, _.toString)
    val actualResult3: String = boxCodec[Double].encode(Box(123.4))

    assertEquals("{value: 'inside'}", actualResult1)
    assertEquals(Box("inside"), actualResult2)
    assertEquals("{value: '123.4'}", actualResult3)
  }

  @Test
  def catsIvariant(): Unit = {
    import cats.Monoid
    import cats.instances.string._
    import cats.syntax.invariant._
    import cats.syntax.semigroup._ // for |+|

    implicit val symbolMonoid: Monoid[Symbol] = Monoid[String].imap(Symbol.apply)(_.name)
    Monoid[Symbol].empty
    val actualResult = 'a |+| 'few |+| 'words

    assertEquals('afewwords, actualResult)
  }
}
