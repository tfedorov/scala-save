package com.tfedorov.tutorial.`type`

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class StructuralTypesTest {

  /*
  A structural type system (or property-based type system) is a major class of type system, in which type compatibility
   and equivalence are determined by the type’s structure, and not by other characteristics such as its name or place of declaration
   */
  @Test
  def exampleStructural(): Unit = {
    class StrucType {
      def whoIsWalking(c: {def walk(): String}): String = "Struct: " + c.walk
    }

    class Cat {
      def walk(): String = "Cat walking"
    }

    class Dog {
      def walk(): String = "Dog walking"
    }

    val walkerStruct = new StrucType()

    val actualCat = walkerStruct.whoIsWalking(new Cat())
    val actualDog = walkerStruct.whoIsWalking(new Dog())

    assertEquals("Struct: Cat walking", actualCat)
    assertEquals("Struct: Dog walking", actualDog)
  }

  @Test
  def exampleStructuralw(): Unit = {
    class StrucType {
      def whoIsWalking(c: {def walk(): String}): String = "Struct: " + c.walk
    }

    class Cat {
      def walk(): String = "Cat walking"

      def anotherMethod(par: String) = "string"
    }

    class Dog {
      def walk(): String = "Dog walking"
    }

    val walkerStruct = new StrucType()

    val actualCat = walkerStruct.whoIsWalking(new Cat())
    val actualDog = walkerStruct.whoIsWalking(new Dog())

    assertEquals("Struct: Cat walking", actualCat)
    assertEquals("Struct: Dog walking", actualDog)
  }

}
