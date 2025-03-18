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

    /*
     * +===============+====================================================================+================+
     * |       -       |            Meaning                                                 | Scala notation |
     * +===============+====================================================================+================+
     * | contravariant | Function1[Animal, String] is a subtype of Function1[Bird, String]  | [-T] |
     * +---------------+--------------------------------------------------------------------+----------------+
     * | covariant     | Function1[A, String] is a subtype of Function1[A, Any]             | [+R]        |
     * +---------------+--------------------------------------------------------------------+----------------+
     *
     * Function types in Scala follow this variance:
     * - **Contravariant in parameter type (`-T`)** → A function that accepts `Animal` can be used where a function that accepts `Bird` is expected.
     * - **Covariant in return type (`+R`)** → A function returning `String` can be assigned to another function returning `String` or a supertype like `Any`.
     *
     * This means:
     * - `Animal => String` can be assigned to `Bird => String` because `Animal` is a broader type.
     * - This is safe because `AnimalF` can handle any `Animal`, including `Bird` and `Rooster`.
     */
    val chicken = new Rooster()

    //val getTweet: Function1[Bird, String] = (a: Animal) => a.sound
    val animalF: Animal => String = (a: Animal) => a.sound
    val getTweet: Bird => String = animalF

    assertEquals("'Cuckoo-ri-coo'", getTweet(chicken))
  }

  @Test
  def contravariantHierarchy(): Unit = {
    //+===============+======================================================+================+
    //|       -       |            Meaning                                   | Scala notation |
    //+===============+======================================================+================+
    //+---------------+------------------------------------------------------+----------------+
    //| contravariant | Descriptor[Animal] is a subclass of Descriptor[Bird] | [-A]           |
    //+---------------+------------------------------------------------------+----------------+
    //   The `Descriptor` class is contravariant in `A` (denoted by `[-A]`)
    //    - This means that `Descriptor[Animal]` can be used where `Descriptor[Bird]` is expected
    //    - Allows flexibility in defining behavior for broader categories
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

    // C[Animal] is a subclass of C[Bird]
    def printMyBird(descriptor: Descriptor[Bird], bird: Bird): String = descriptor.desc(bird)

    val chicky: Rooster = new Rooster()
    val bird: Bird = new Bird()
    //AnimalDescriptor[Animal] = BirdDescriptor[Bird]
    val actualAnimalChicken = printMyBird(new AnimalDescriptor, chicky)
    assertEquals("The animal(Rooster) on ground sounds: 'Cuckoo-ri-coo'", actualAnimalChicken)

    val actualBirdChicken = printMyBird(new BirdDescriptor, chicky)
    assertEquals("The bird(Rooster) on fly sounds: 'Oh my God, I can't fly!!!'", actualBirdChicken)

    val actualAnimalBird = printMyBird(new AnimalDescriptor, bird)
    assertEquals("The animal(Bird) on ground sounds: 'Zwin-zwin'", actualAnimalBird)

    val actualBirdBird = printMyBird(new BirdDescriptor, bird)
    assertEquals("The bird(Bird) on fly sounds: 'Zwiiiiiiiiiin-zwiiiiiiiiin'", actualBirdBird)

    def printMyAnimals(descriptor: Seq[Descriptor[Bird]], el: Bird): String = descriptor.map(_.desc(el)).mkString("|")

    val animals = printMyAnimals(new AnimalDescriptor() :: new AnimalDescriptor() :: new AnimalDescriptor() :: Nil, bird)
    assertEquals("The animal(Bird) on ground sounds: 'Zwin-zwin'|The animal(Bird) on ground sounds: 'Zwin-zwin'|The animal(Bird) on ground sounds: 'Zwin-zwin'", animals)
  }

  @Test
  def covariantHierarchy(): Unit = {

    /*
     * +===============+======================================================+================+
     * |       -       |            Meaning                                   | Scala notation |
     * +===============+======================================================+================+
     * | covariant     | Descriptor[Bird] is a subclass of Descriptor[Animal] | [+A]           |
     * +---------------+------------------------------------------------------+----------------+
     *
     * The `Descriptor` class is covariant in `A` (denoted by `[+A]`).
     * - This means that `Descriptor[Bird]` can be used where `Descriptor[Animal]` is expected.
     * - This is useful when the consumer **only reads** from the container but does not modify it.
     * - **Covariant types allow flexibility** when returning values but do not allow mutation.
     *
     * If `Descriptor[A]` were **contravariant** (`[-A]`), `Descriptor[Animal]` could be used for `Bird`.
     * If `Descriptor[A]` were **invariant** (`[A]`), `Descriptor[Bird]` and `Descriptor[Animal]` would be unrelated.
     */
    abstract class Container[+A] {
      def get: A
      //def put(animal: A) - Don't allow
      // You can put bird to animal
    }

    class AnimalContainer(animal: Animal) extends Container[Animal] {
      override def get: Animal = animal
    }

    class BirdContainer(bird: Bird) extends Container[Bird] {
      override def get: Bird = bird
    }

    val chicky: Rooster = new Rooster()
    val bird: Bird = new Bird()

    def printMyBird(descriptor: Container[Bird]): String = descriptor.get.soundOnFly

    val actualBirdChicken = printMyBird(new BirdContainer(chicky))
    assertEquals("'Oh my God, I can't fly!!!'", actualBirdChicken)

    def printMyAnimal(descriptor: Container[Animal]): String = descriptor.get.sound

    //AnimalDescriptor[Animal] != BirdDescriptor[Bird]
    val actualAnimalChicken = printMyAnimal(new AnimalContainer(chicky))
    assertEquals("'Cuckoo-ri-coo'", actualAnimalChicken)

    val actualBirdBird = printMyBird(new BirdContainer(bird))
    assertEquals("'Zwiiiiiiiiiin-zwiiiiiiiiin'", actualBirdBird)

    def printMyAnimals(descriptor: Seq[Container[Animal]]): String = descriptor.map(_.get.sound).mkString("|")

    val animals = printMyAnimals(new BirdContainer(bird) :: new AnimalContainer(chicky) :: new BirdContainer(chicky) :: Nil)
    assertEquals("'Zwin-zwin'|'Cuckoo-ri-coo'|'Cuckoo-ri-coo'", animals)
  }


  @Test
  def invariantHierarchy(): Unit = {
    abstract class Container[A] {
      def get: A
    }

    class AnimalContainer(animal: Animal) extends Container[Animal] {
      override def get: Animal = animal
    }

    class BirdContainer(bird: Bird) extends Container[Bird] {
      override def get: Bird = bird
    }

    def printMyBird(descriptor: Container[Bird]): String = descriptor.get.soundOnFly

    def printMyAnimal(descriptor: Container[Animal]): String = descriptor.get.sound

    val chicky: Rooster = new Rooster()
    val bird: Bird = new Bird()

    val actualBirdChicken = printMyBird(new BirdContainer(chicky))
    assertEquals("'Oh my God, I can't fly!!!'", actualBirdChicken)

    //AnimalDescriptor[Animal] != BirdDescriptor[Bird]
    val actualAnimalChicken = printMyAnimal(new AnimalContainer(chicky))
    assertEquals("'Cuckoo-ri-coo'", actualAnimalChicken)

    val actualBirdBird = printMyBird(new BirdContainer(bird))
    assertEquals("'Zwiiiiiiiiiin-zwiiiiiiiiin'", actualBirdBird)

    def printMyAnimals(descriptor: Seq[Container[Animal]]): String = descriptor.map(_.get.sound).mkString("|")
    // val animals = printMyAnimals(new BirdContainer(bird) :: new AnimalContainer(chicky) :: new BirdContainer(chicky) :: Nil)
    // wrong because     //Container[Animal] != Container[Bird]
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
