package com.tfedorov.tutorial.`type`.generics

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class VariancesTest {

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
    //[-A] is mean
    class BirdDescriptor extends Descriptor[Bird] {
      def desc(bird: Bird): String = "The bird(" + bird.getClass.getSimpleName + ") on fly sounds: " + bird.soundOnFly
    }

    def printMyBird(descriptor: Descriptor[Bird], bird: Bird): String = descriptor.desc(bird)

    val chicky: Rooster = new Rooster()
    val bird: Bird = new Bird()
    //AnimalDescriptor[Animal] = BirdDescriptor[Bird]
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
  def covariantHierarchy(): Unit = {

    abstract class Descriptor[A] {
      def desc(value: A): String
    }

    class AnimalDescriptor extends Descriptor[Animal] {
      def desc(anim: Animal): String = "The animal(" + anim.getClass.getSimpleName + ") on ground sounds: " + anim.sound
    }

    class BirdDescriptor extends Descriptor[Bird] {
      def desc(bird: Bird): String = "The bird(" + bird.getClass.getSimpleName + ") on fly sounds: " + bird.soundOnFly
    }

    def printMyBird(descriptor: Descriptor[Bird], bird: Bird): String = descriptor.desc(bird)

    val chicky: Rooster = new Rooster()
    val bird: Bird = new Bird()
    //AnimalDescriptor[Animal] != BirdDescriptor[Bird]
    val actualBirdChicken = printMyBird(new BirdDescriptor, chicky)
    val actualBirdBird = printMyBird(new BirdDescriptor, bird)

    assertEquals("The bird(Rooster) on fly sounds: 'Oh my God, I can't fly!!!'", actualBirdChicken)
    assertEquals("The bird(Bird) on fly sounds: 'Zwiiiiiiiiiin-zwiiiiiiiiin'", actualBirdBird)
  }

  @Test
  def contravariantHierarchyExp(): Unit = {

    abstract class Descriptor[-A] {
      def desc(value: A): String
    }

    class AnimalDescriptor extends Descriptor[Animal] {
      def desc(anim: Animal): String = "The animal(" + anim.getClass.getSimpleName + ") on ground sounds: " + anim.sound
    }

    class BirdDescriptor extends Descriptor[Bird] {
      def desc(bird: Bird): String = "The bird(" + bird.getClass.getSimpleName + ") on fly sounds: " + bird.soundOnFly
    }

    def printMyBird[B <: Animal](descriptor: Descriptor[B], bird: B): String = descriptor.desc(bird)

    val bird: Bird = new Bird()
    class Cat extends Animal {
      override def sound: String = "'Meow'"
    }
    val cat = new Cat

    val actualAnimalChicken = printMyBird(new AnimalDescriptor, cat)
    val actualAnimalBird = printMyBird(new AnimalDescriptor, bird)
    val actualBirdBird = printMyBird(new BirdDescriptor, bird)

    assertEquals("The animal(Cat$1) on ground sounds: 'Meow'", actualAnimalChicken)
    assertEquals("The animal(Bird) on ground sounds: 'Zwin-zwin'", actualAnimalBird)
    assertEquals("The bird(Bird) on fly sounds: 'Zwiiiiiiiiiin-zwiiiiiiiiin'", actualBirdBird)
  }
}
