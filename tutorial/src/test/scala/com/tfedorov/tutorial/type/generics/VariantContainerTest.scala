package com.tfedorov.tutorial.`type`.generics

import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Assertions.{assertEquals, assertNotEquals, assertSame}


class VariantContainerTest {

  abstract sealed class Animal {
    def sound: String

    override def toString = this.getClass.getSimpleName + " that sounds " + sound
  }

  class Bird extends Animal {
    override val sound = "'Zwin-zwin'"
  }

  class Rooster extends Bird {
    //override val sound = "'Cuckoo-ri-coo'"
    override val sound = "'Cuckoo-ri-coo'"
  }

  class Duck extends Bird {
    //override val sound = "'Cuckoo-ri-coo'"
    override val sound = "'Gal-gal-gal'"
  }

  class Tiger extends Animal {
    override val sound = "'Whrrr'"
  }

  @Test
  def contravariantHierarchy(): Unit = {
    /*
 * +===============+============================================================+================+
 * |       -       | Meaning                                                    | Scala notation |
 * +===============+============================================================+================+
 * | Contravariant | Container[Animal] is a subtype of Container[Bird]          | [-A]           |
 * +---------------+------------------------------------------------------------+----------------+
 *
 * The `Container[-A]` is **contravariant**, meaning:
 * - `Container[Animal]` can be used where `Container[Bird]` is expected.
 * - Useful when we **only consume** values of type `A` (e.g., setters).
 */
    trait Container[-A] {
      //def aVar: A -  ERROR Contravariant type A occurs in covariant position in type A of value aVar
      def setA(a: A)
    }

    class AnimalContainer(var aVar: Animal) extends Container[Animal] {

      override def setA(a: Animal): Unit = aVar = a

      override def toString = this.getClass.getSimpleName + " contains " + aVar
    }

    class BirdContainer(var aVar: Bird) extends Container[Bird] {

      override def setA(a: Bird): Unit = aVar = a

      override def toString = this.getClass.getSimpleName + " contains " + aVar
    }

    val chicky: Rooster = new Rooster()
    val pigeon: Bird = new Bird()

    // Container[Animal] subclass Container[Bird]
    // if    trait Container[A] { fails
    val birdsContainers: Seq[Container[Bird]] = new AnimalContainer(new Tiger) :: new AnimalContainer(chicky) :: new BirdContainer(pigeon) :: Nil
    // ‼️ Error can't setup new BirdContainer here
    // val animalContainers: Seq[Container[Animal]] = new AnimalContainer(new Tiger) :: new AnimalContainer(chicky) :: new BirdContainer(pigeon) :: Nil

    // ‼️ If  trait Container[-A] { /def aVar: A
    // var r: Container[Bird] = new AnimalContainer(new Tiger).aVar returns Tiger

    val expectedBeforeSet =
      """AnimalContainer$1 contains Tiger that sounds 'Whrrr'
        |AnimalContainer$1 contains Rooster that sounds 'Cuckoo-ri-coo'
        |BirdContainer$1 contains Bird that sounds 'Zwin-zwin'""".stripMargin

    assertEquals(expectedBeforeSet, birdsContainers.mkString("\n"))

    val donald: Duck = new Duck()
    birdsContainers.foreach(_.setA(donald))
    val expectedAfterSet =
      "AnimalContainer$1 contains Duck that sounds 'Gal-gal-gal'" ::
        "AnimalContainer$1 contains Duck that sounds 'Gal-gal-gal'" ::
        "BirdContainer$1 contains Duck that sounds 'Gal-gal-gal'" ::
        Nil
    assertEquals(expectedAfterSet, birdsContainers.map(_.toString))

    //val animalContainers: Seq[Container[Animal]] = new AnimalContainer(chicky) :: new BirdContainer(pigeon) :: Nil
    // ERROR Expected Container[Animal]
    // Container[Bird] subclass AnimalContainer[Bird]
  }


  @Test
  def covariantHierarchy(): Unit = {
    /*
 * +===============+====================================================+================+
 * |       -       | Meaning                                            | Scala notation |
 * +===============+====================================================+================+
 * | Covariant     | Container[Bird] is a subtype of Container[Animal]  | [+A]           |
 * +---------------+----------------------------------------------------+----------------+
 *
 * The `Container[+A]` is **covariant**, meaning:
 * - `Container[Bird]` is a subtype of `Container[Animal]`.
 * - Useful when we **only produce** values of type `A` (e.g., getters).
 */
    trait Container[+A] {
      //def aVar: A -  ERROR Contravariant type A occurs in covariant position in type A of value aVar
      def getA: A
    }

    class AnimalContainer(aVar: Animal) extends Container[Animal] {

      override def toString = this.getClass.getSimpleName + " contains " + aVar

      override def getA: Animal = aVar
    }

    class BirdContainer(aVar: Bird) extends Container[Bird] {

      override def toString = this.getClass.getSimpleName + " contains " + aVar

      override def getA: Bird = aVar
    }

    val chicky: Rooster = new Rooster()
    val pigeon: Bird = new Bird()

    // Container[Animal] subclass Container[Bird]
    // if    trait Container[A] { fails
    val animalsContainers: Seq[Container[Animal]] = new AnimalContainer(new Tiger) :: new BirdContainer(chicky) :: new AnimalContainer(pigeon) :: Nil
    // ‼️ Error can't setup new AnimalContainer here
    //val birdsContainers: Seq[Container[Bird]] = new AnimalContainer(new Tiger) :: new BirdContainer(chicky) :: new AnimalContainer(pigeon) :: Nil

    val expectedBeforeSet =
      """AnimalContainer$2 contains Bird that sounds 'Zwin-zwin'
        |AnimalContainer$2 contains Tiger that sounds 'Whrrr'
        |BirdContainer$2 contains Rooster that sounds 'Cuckoo-ri-coo'""".stripMargin

    assertEquals(expectedBeforeSet, animalsContainers.map(_.toString).sorted.mkString("\n"))
  }

  @Test
  def inVariantHierarchy(): Unit = {

    /*
     * +===============+======================================+================+
     * |       -       | Meaning                              | Scala notation |
     * +===============+======================================+================+
     * | Invariant     | Container[Bird] and Container[Animal] are unrelated | [A] |
     * +---------------+--------------------------------------+----------------+
     *
     * `Container[A]` is **invariant**, meaning:
     * - `Container[Bird]` and `Container[Animal]` are completely unrelated.
     */

    trait Container[A] {
      //def aVar: A -  ERROR Contravariant type A occurs in covariant position in type A of value aVar
      def getA: A
    }

    class AnimalContainer(aVar: Animal) extends Container[Animal] {

      override def toString = this.getClass.getSimpleName + " contains " + aVar

      override def getA: Animal = aVar
    }

    class BirdContainer(aVar: Bird) extends Container[Bird] {

      override def toString = this.getClass.getSimpleName + " contains " + aVar

      override def getA: Bird = aVar
    }

    val chicky: Rooster = new Rooster()
    val pigeon: Bird = new Bird()

    // ‼️ Error can't setup new Container here
    val animalsContainers: Seq[Container[Animal]] = new AnimalContainer(new Tiger) :: new AnimalContainer(pigeon) :: Nil

    // ‼️ Error can't setup new AnimalContainer here
    val birdsContainers: Seq[Container[Bird]] = new BirdContainer(pigeon) :: Nil


    val expectedBeforeSet =
      """AnimalContainer$3 contains Bird that sounds 'Zwin-zwin'
        |AnimalContainer$3 contains Tiger that sounds 'Whrrr'""".stripMargin

    assertEquals(expectedBeforeSet, animalsContainers.map(_.toString).sorted.mkString("\n"))
  }

  @Test
  def contrVariantLogger(): Unit = {
    trait Logger[-A] {
      def log(a: A): String
    }

    class AnimalLogger extends Logger[Animal] {
      override def log(a: Animal): String = {
        val message = s"Logging: ${a.toString}"
        println(message)
        message
      }
    }

    val birdLogger: Logger[Bird] = new AnimalLogger() // ✅ Works because Logger[-A] is contravariant
    assertEquals("Logging: Duck that sounds 'Gal-gal-gal'", birdLogger.log(new Duck()))

    val animalLogger: Logger[Animal] = new AnimalLogger() // ✅ Works because Logger[-A] is contravariant
    assertEquals("Logging: Duck that sounds 'Gal-gal-gal'", animalLogger.log(new Duck()))
  }
}
