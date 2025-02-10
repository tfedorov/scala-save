package com.tfedorov.tutorial.syntax

import org.junit.jupiter.api.Assertions._
import org.junit.jupiter.api.Test

class SugarExampleTest {

  @Test
  def f_BoundedPolymorphism(): Unit = {
    //trait Container extends Ordered[Container]
    //class MyContainer extends Container {  def compare(that: MyContainer): Int }
    //error: Class 'MyContainer' must either be declared abstract or implement abstract member 'compare(that: MyContainer): Int' in 'MyContainer'


    /*
        class MyContainer extends Container[MyContainer] {
          def compare(that: MyContainer): Int = 0
        }
    */
    trait Container[A <: Container[A]] extends Ordered[A]
    class MyContainer extends Container[MyContainer] {
      def compare(that: MyContainer): Int = 0
    }

    class YourContainer extends Container[YourContainer] {
      def compare(that: YourContainer) = 0
    }
    //val target = List(new MyContainer, new MyContainer, new YourContainer)
    //Error:(143, 31) diverging implicit expansion for type Ordering[Container[_ >: YourContainer with MyContainer <: Container[_ >: YourContainer with MyContainer <: Object]]]
    //starting with method $conforms in object Predef

    val target = List(new MyContainer, new MyContainer, new MyContainer)
    val actualResult = target.min

    assertEquals(target.head, actualResult)
  }

  @Test
  def structuralTypes(): Unit = {
    def foo(x: {def get: Int}): Int = 123 + x.get

    val actualResult = foo(new {
      def get = 10
    })

    assertEquals(133, actualResult)
  }

  @Test
  def typeValTest(): Unit = {
    case class Cats(breed: String, sound: String)

    val cat1@Cats(actualBreed, actualSound) = Cats("Cheshire", "meow")

    assertEquals("Cheshire", actualBreed)
    assertEquals("meow", actualSound)
    assertEquals(cat1, Cats("Cheshire", "meow"))
  }
}
