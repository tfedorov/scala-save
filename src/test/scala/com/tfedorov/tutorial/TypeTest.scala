package com.tfedorov.tutorial

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class TypeTest {

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

  /*
A central question that comes up when mixing OO with polymorphism is:
 if T’ is a subclass of T, is Container[T’] considered a subclass of Container[T]?
+===============+================================+================+
|       -       |            Meaning             | Scala notation |
+===============+================================+================+
| covariant     | C[T’] is a subclass of C[T]    | [+T]           |
+---------------+--------------------------------+----------------+
| contravariant | C[T] is a subclass of C[T’]    | [-T]           |
+---------------+--------------------------------+----------------+
| invariant     | C[T] and C[T’] are not related | [T]            |
+---------------+--------------------------------+----------------+
   */
  @Test
  def contravariantFunction(): Unit = {
    val chicken = new Rooster()

    //val getTweet: Function1[Bird, String] = (a: Animal) => a.sound
    //val getTweet: Bird => String = _.sound
    val animalF: Animal => String = (a: Animal) => a.sound
    val getTweet: Bird => String = animalF

    assertEquals("'Cuckoo-ri-coo'", getTweet(chicken))
  }

  @Test
  def contravariantHierarchy(): Unit = {

    abstract class Descriptor[-A] {
      def desc(value: A): String
    }

    class AnimalDescriptor extends Descriptor[Animal] {
      def desc(anim: Animal): String = "The animal(" + anim.getClass.getSimpleName + ") on ground sounds: " + anim.sound
    }

    class BirdDescriptor extends Descriptor[Bird] {
      def desc(bird: Bird): String = "The bird(" + bird.getClass.getSimpleName + ") on fly sounds: " + bird.soundOnFly
    }

    def printMyBird(descriptor: Descriptor[Bird], bird: Bird): String = descriptor.desc(bird)

    val chicky = new Rooster()
    val bird = new Bird()
    val actualAnimalChicken = printMyBird(new AnimalDescriptor, chicky)
    val actualBirdChicken = printMyBird(new BirdDescriptor, chicky)
    val actualAnimalBird = printMyBird(new AnimalDescriptor, bird)
    val actualBirdBird = printMyBird(new BirdDescriptor, bird)

    assertEquals("The animal(Rooster) on ground sounds: 'Cuckoo-ri-coo'", actualAnimalChicken)
    assertEquals("The bird(Rooster) on fly sounds: 'Oh my God, I can't fly!!!'", actualBirdChicken)
    assertEquals("The animal(Bird) on ground sounds: 'Zwin-zwin'", actualAnimalBird)
    assertEquals("The bird(Bird) on fly sounds: 'Zwiiiiiiiiiin-zwiiiiiiiiin'", actualBirdBird)

  }

  @Test
  def boundry(): Unit = {
    //def cacophony[T](things: Seq[T]) = things map (_.sound)
    //error: value sound is not a member of type parameter T
    //       def cacophony[T](things: Seq[T]) = things map (_.sound)
    def biophony[T <: Animal](things: Seq[T]): Seq[String] = things map (_.sound)

    val actual = biophony(Seq(new Rooster, new Bird))

    assertEquals("'Cuckoo-ri-coo'" :: "'Zwin-zwin'" :: Nil, actual)
  }

  @Test
  def quantification(): Unit = {
    def drop1Wildcard(l: List[_]) = l.tail

    def drop1Type(l: List[T forSome {type T}]) = l.tail


    val tailWild = drop1Wildcard("'Cuckoo-ri-coo'" :: "'Zwin-zwin'" :: Nil)
    val tailType = drop1Type("'Cuckoo-ri-coo'" :: "'Zwin-zwin'" :: Nil)

    assertEquals(classOf[String], tailWild.head.getClass)
    assertEquals(classOf[String], tailType.head.getClass)

    //headString(tail)
    //Error:(101, 16) type mismatch;
    //found   : List[_$2] where type _$2
    // required: List[String]
    //headString(tail)


  }
}
