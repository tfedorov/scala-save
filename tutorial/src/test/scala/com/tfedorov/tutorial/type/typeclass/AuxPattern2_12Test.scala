package com.tfedorov.tutorial.`type`.typeclass

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

import scala.language.postfixOps

object AuxPattern2_12Test {

  @Test
  def wrongCase(): Unit = {
    trait Finder[A] {
      type Out

      def find(value: A): Out
    }


    // Implicit instance for Int → returns String
    implicit val intFinder: Finder[Int] {type Out = String} = new Finder[Int] {
      type Out = String

      def find(value: Int): String = s"Found number: $value"
    }

    // New conflicting implicit instance for Int → returns Int instead of String
    implicit val intFinderAlt: Finder[Int] {type Out = Int} = new Finder[Int] {
      type Out = Int

      def find(value: Int): Int = value * 10
    }


    // ❌ Now, implicit resolution fails due to ambiguity
    def process[A](value: A)(implicit finder: Finder[A]): finder.Out = {
      finder.find(value)
    }


    //    process(42) // ❌ ERROR: Ambiguous implicits, Scala can't choose

  }


  @Test
  def correctCase(): Unit = {
    trait Finder[A] {
      type Out

      def find(value: A): Out
    }

    object Finder {
      // Implicit instance for Int → returns String
      implicit val intFinder: Finder[Int] {type Out = String} = new Finder[Int] {
        type Out = String

        def find(value: Int): String = s"Found number: $value"
      }

      // New conflicting implicit instance for Int → returns Int instead of String
      //      implicit val intFinderAlt: Finder[Int] { type Out = Int } = new Finder[Int] {
      //        type Out = Int
      //        def find(value: Int): Int = value * 10
      //      }
    }

    def process[A](value: A)(implicit finder: Finder[A]): finder.Out = {
      finder.find(value)
    }

    val result = process(42)

    assertEquals(result, "Found number: 42")
  }
}
