package com.tfedorov.tutorial.`type`.generics

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class GenericTest {

  abstract sealed class Animal {
    def sound: String
  }

  class Bird extends Animal {
    override val sound = "'Zwin-zwin'"
    val soundOnFly = "'Zwiiiiiiiiiin-zwiiiiiiiiin'"
  }

  class Rooster extends Bird {
    //override val sound = "'Cuckoo-ri-coo'"
    override val sound = "'Cuckoo-ri-coo'"
    override val soundOnFly = "'Oh my God, I can't fly!!!'"
  }

  @Test
  def inheritance(): Unit = {
    def myFct[T <: Animal](arg1: T, arg2: T): String = {
      arg1.sound + " & " + arg2.sound
    }

    val actualResult = myFct(new Bird, new Rooster)

    assertEquals("'Zwin-zwin' & 'Cuckoo-ri-coo'", actualResult)
  }

  @Test
  def supertype(): Unit = {
    def myFct[T >: Rooster](arg1: T, arg2: T): String = {
      arg1.getClass + " & " + arg2.getClass
    }

    val actualResult = myFct(new Bird, new Object)

    assertEquals("class com.tfedorov.tutorial.type.generics.GenericTest$Bird & class java.lang.Object", actualResult)
  }

  @Test
  def inheritanceSupertype(): Unit = {
    def myFct[T >: Rooster <: Animal](arg1: T, arg2: T): String = {
      arg1.getClass + " & " + arg2.getClass
    }

    //Error: inferred type arguments [Object] do not conform to method myFct's type parameter bounds [T >: GenericTest.this.Rooster <: GenericTest.this.Animal]
    //    val actualResult = myFct(new Bird, new Object)
    //val actualResult = myFct(new Bird, new Object)
    val actualResult = myFct(new Rooster, new Animal {
      override def sound: String = "uuu"
    })

    assertEquals("class com.tfedorov.tutorial.type.generics.GenericTest$Rooster & class com.tfedorov.tutorial.type.generics.GenericTest$$anon$1", actualResult)
  }

  //https://www.drmaciver.com/2008/03/existential-types-in-scala/
  @Test
  def existentialTypes(): Unit = {
    def sound1st(x: List[T] forSome {type T <: Animal}): String = x.head.sound

    //Error:(66, 41) type mismatch;
    // found   : String
    // required: GenericTest.this.Animal
    //    val actualResult2 = sound1st("Not a":: Nil)
    // val actualResult2 = sound1st("Not a":: Nil)
    val actualResult = sound1st(new Rooster :: new Bird :: Nil)

    assertEquals("'Cuckoo-ri-coo'", actualResult)
  }

  @Test
  def existentialTypes2(): Unit = {
    //Error:(78, 116) type mismatch;
    // found   : (Class[Float], String)
    // required: (Class[_ <: GenericTest.this.Animal], String)
    //    val map = Map[Class[T] forSome {type T <: Animal}, String](classOf[Animal] -> "this is Animal", classOf[Float] -> "this is Bird")
    //val map = Map[Class[T] forSome {type T <: Animal}, String](classOf[Animal] -> "this is Animal", classOf[Float] -> "this is Bird")
    val map = Map[Class[T] forSome {type T <: Animal}, String](classOf[Animal] -> "this is Animal", classOf[Bird] -> "this is Bird")

    val actualResult = map(new Bird().getClass)

    assertEquals("this is Bird", actualResult)
  }

  @Test
  def wildCard(): Unit = {
    val map = Map[Class[_], String](classOf[String] -> "this is String", classOf[Float] -> "this is Float")

    val actualResult1 = map("some".getClass)
    val actualResult2 = map(2f.getClass)
    //java.util.NoSuchElementException: key not found: int
    //val actualResult3 = map(2.getClass)

    assertEquals("this is String", actualResult1)
    assertEquals("this is Float", actualResult2)
  }

}
