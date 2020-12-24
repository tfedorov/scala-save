package com.tfedorov.tutorial.functions

import org.junit.jupiter.api.Assertions._
import org.junit.jupiter.api.Test

class HigherOrderFunctionsTest {

  @Test
  def testParamFunction(): Unit = {
    var iAmCalled = false

    //def innerF(f: Function0[_]): Unit = f()
    def innerF(f: () => Any): Unit = f()

    innerF(() => {
      iAmCalled = true
    })

    assertTrue(iAmCalled)
  }

  @Test
  def testReturnFunction(): Unit = {
    def curlBuilder(isGet: Boolean, domainName: String): (String, String) => String = {
      val begin = if (isGet) "curl -XGET " else "curl -XPOST "
      (key: String, value: String) => s"$begin -H '$key: $value' $domainName"
    }

    def getBuilderF = curlBuilder(isGet = true, "www.example.com")

    val actualResult = getBuilderF("Content-type", "application/json")

    assertEquals("curl -XGET  -H 'Content-type: application/json' www.example.com", actualResult)
  }

  @Test
  def testReturnFunction2(): Unit = {
    def curlBuilder(isGet: Boolean, domainName: String): (String, String) => String = {
      val begin = if (isGet) "curl -XGET " else "curl -XPOST -d''"
      val result = (key: String, value: String) => s"$begin -H '$key: $value' $domainName"
      result
    }

    def postBuilderF = curlBuilder(isGet = false, "www.example.com")

    val actualResult = postBuilderF("Content-type", "application/json")

    assertEquals("curl -XPOST -d'' -H 'Content-type: application/json' www.example.com", actualResult)
  }

  @Test
  def testSeqOfFunctions(): Unit = {
    def textBuilder(compose: Seq[(Int, String) => String]): (Int, String) => String = {
      compose.tail.foldLeft(compose.head) { (aggF, elF) =>
        (key: Int, value: String) => aggF(key, value) + ";" + elF(key, value)
      }
    }

    def multi(key: Int, value: String): String = value * key

    def add(key: Int, value: String): String = key + value

    def multiAddF = textBuilder((multi _) :: (add _) :: Nil)

    def multiAddMultiF = textBuilder(multi _ :: add _ :: multi _ :: Nil)

    val actualResult1 = multiAddF(3, "a")
    val actualResult2 = multiAddMultiF(4, "b")

    assertEquals("aaa;3a", actualResult1)
    assertEquals("bbbb;4b;bbbb", actualResult2)
  }

  @Test
  def testMultipleAndThen(): Unit = {
    def functionComposer(compose: Seq[String => String]): String => String = {
      compose.foldLeft(identity[String](_))((aggF, elF) => aggF.andThen(elF))
    }

    val functions = ((_: String).toUpperCase) :: new (String => String) {
      override def apply(v1: String): String = v1.reverse
    } :: Nil
    val builtF = functionComposer(functions)

    val actualResult = builtF("abc")

    assertEquals("CBA", actualResult)
  }
}
