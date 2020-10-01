package com.tfedorov.tutorial.books.herding_cats

import org.junit.jupiter.api.Assertions._
import org.junit.jupiter.api.Test

//http://eed3si9n.com/herding-cats/making-our-own-typeclass-with-simulacrum.html
class TypeClassTest {

  trait CanTruthy[A] {
    def truthy(a: A): Boolean
  }

  object CanTruthy {

    def fromTruthy[A](f: A => Boolean): CanTruthy[A] = new CanTruthy[A] {
      def truthy(a: A): Boolean = f(a)
    }

    //def apply[A](implicit instance: CanTruthy[A]): CanTruthy[A] = instance


    trait Ops[A] {
      def typeClassInstance: CanTruthy[A]

      def self: A

      //Wrapper from Ops
      def truthy: Boolean = typeClassInstance.truthy(self)
    }

    object ops {
      implicit def toAllCanTruthyOps[A](target: A)(implicit tc: CanTruthy[A]): Ops[A] = new Ops[A] {
        val self: A = target
        val typeClassInstance: CanTruthy[A] = tc
      }
    }

  }

  @Test
  def intOpsTest(): Unit = {
    implicit val intCanTruthy: CanTruthy[Int] = CanTruthy.fromTruthy({
      case 0 => false
      case _ => true
    })

    //val result: Boolean = CanTruthy.apply(intCanTruthy).truthy(10)
    //val result: Boolean = CanTruthy.ops.toAllCanTruthyOps(10).truthy
    import CanTruthy.ops._
    val result: Boolean = 10.truthy

    assertTrue(result)
    assertFalse(0.truthy)
  }

  @Test
  def stringOpsTest(): Unit = {
    implicit val intCanTruthy: CanTruthy[String] = CanTruthy.fromTruthy({
      case "yes" => true
      case "true" => true
      case _ => false
    })

    //val result: Boolean = CanTruthy.apply.truthy("yes")
    //val result: Boolean = CanTruthy.apply(intCanTruthy).truthy("yes")
    //val result: Boolean = CanTruthy.ops.toAllCanTruthyOps("yes").truthy
    //val result: Boolean = CanTruthy.ops.toAllCanTruthyOps("yes")(intCanTruthy).truthy
    import CanTruthy.ops._
    val result: Boolean = "yes".truthy

    assertTrue(result)
    assertFalse("no".truthy)
  }
}