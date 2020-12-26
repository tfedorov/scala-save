package com.tfedorov.tutorial.implicits

import org.junit.jupiter.api.Assertions._
import org.junit.jupiter.api.Test

class ContextBoundTest {
  /*
    A context bound describes an implicit value, instead of view bound's implicit conversion.
     It is used to declare that for some type A, there is an implicit value of type B[A] available. The syntax goes like this:
     */
  @Test
  def noContextBound(): Unit = {
    def implIndex[A](a: A)(implicit evidence: List[A]) = evidence.indexOf(a)

    implicit val fruits: List[String] = List("apple", "orange", "lemon")

    val actualResult = implIndex("apple")

    assertEquals(0, actualResult)
  }

  @Test
  def contextBound(): Unit = {
    def implIndex[A: List](a: A) = implicitly[List[A]].indexOf(a)

    implicit val fruits: List[String] = List("apple", "orange", "lemon")

    val actualResult = implIndex("orange")

    assertEquals(1, actualResult)
  }

  @Test
  def contextBoundClass(): Unit = {

    class ContextBound[A](val value: A)

    def methodWithCB[M: ContextBound](another: M): Boolean =
      implicitly[ContextBound[M]].value.equals(another)

    implicit val impl: ContextBound[String] = new ContextBound[String]("orange")
    implicit val implFloat: ContextBound[Float] = new ContextBound[Float](1f)

    assertTrue(methodWithCB("orange"))
    assertFalse(methodWithCB("apple"))
    assertTrue(methodWithCB(1f))
    assertFalse(methodWithCB(2f))
  }

  @Test
  def contextBoundChainAfter(): Unit = {
    class ContextBound1[A](val value1: A)
    class ContextBound2[B](val value2: B)
    def exist[M: ContextBound1 : ContextBound2](another: M): Boolean =
      implicitly[ContextBound1[M]].value1.equals(another) || implicitly[ContextBound2[M]].value2.equals(another)

    implicit val impl: ContextBound1[String] = new ContextBound1[String]("orange")
    implicit val implFloat: ContextBound2[String] = new ContextBound2[String]("apple")

    val actualResult = exist("orange")

    assertTrue(actualResult)
    assertTrue(exist("apple"))
    assertFalse(exist("lemon"))
  }

  @Test
  def contextBoundChainBefore(): Unit = {
    class ContextBound1[A](val value1: A)
    class ContextBound2[B](val value2: B)
    def exist[M](another: M)(implicit evidence1: ContextBound1[M], evidence2: ContextBound2[M]): Boolean =
      evidence1.value1.equals(another) || evidence2.value2.equals(another)

    implicit val impl1: ContextBound1[String] = new ContextBound1[String]("orange")
    implicit val impl2: ContextBound2[String] = new ContextBound2[String]("apple")

    val actualResult = exist("orange")

    assertTrue(actualResult)
    assertTrue(exist("apple"))
    assertFalse(exist("lemon"))
  }


  /*
  Methods may ask for some kinds of specific “evidence” for a type without setting up strange objects as with Numeric.
  Instead, you can use one of these type-relation operators:
  +---------+--------------------------+
  | Generic |        Relations         |
  +---------+--------------------------+
  | A =:= B | A must be equal to B     |
  | A <:< B | A must be a subtype of B |
  | A <%< B | A must be viewable as B  |
  +---------+--------------------------+
   */

  @Test
  def otherTypeBounds(): Unit = {

    implicit def strToInt(x: String): Int = x.length

    class Container[A](value: A) {
      def addIt(implicit evidence: A =:= Int): Int = 123 + value
    }

    val intResult = new Container(123).addIt
    //val strResult = new Container[String]("123").addIt()

    assertEquals(246, intResult)
  }

  @Test
  def implicitConversionsAsParameters(): Unit = {
    def getIndex[T, CC](seqSource: CC, value: T)(implicit conversion: CC => Seq[T]) = {
      //conversion(seqSource).indexOf(value)
      seqSource.indexOf(value)
    }

    val indexA = getIndex("abc", 'a')

    case class FullName(firstName: String, familyName: String, fatherName: String)
    implicit def confFullName(inp: FullName): Seq[String] = inp.firstName :: inp.familyName :: inp.fatherName :: Nil

    val myName = FullName("Fedorov", "Taras", "Serhiy")
    val indexTaras = getIndex(myName, "Taras")

    assertEquals(1, indexTaras)
    assertEquals(0, indexA)
  }

  @Test
  def contextBoundsImplicitly(): Unit = {
    //def foo[A : Ordered] {}
    //def foo[A](implicit x: Ordered[A]) {}
    //implicitly[Ordering[Int]]

    trait Container[M[_]] {
      def put[A](x: A): M[A]

      def get[A](m: M[A]): A
    }

    implicit val listContainer: Container[List] = new Container[List] {
      def put[A](x: A) = List(x)

      def get[A](m: List[A]): A = m.head
    }

    implicit val optionContainer: Container[Some] = new Container[Some] {
      def put[A](x: A): Some[A] = Some(x)

      def get[A](m: Some[A]): A = m.get
    }

    def tupleize[M[_] : Container, A, B](fst: M[A], snd: M[B])(implicit c: Container[M]): M[(A, B)] = {
      //antoher variant
      // val c = implicitly[Container[M]]
      c.put(c.get(fst), c.get(snd))
    }

    val actualOpts: Option[(Int, String)] = tupleize(Some(1), Some("2"))
    val actualLists: Seq[(Int, Int)] = tupleize(List(1), List(2))

    assertEquals(Some((1, "2")), actualOpts)
    assertEquals(List((1, 2)), actualLists)
  }
}
