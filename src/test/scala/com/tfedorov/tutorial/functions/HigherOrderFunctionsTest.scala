package com.tfedorov.tutorial.functions

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class HigherOrderFunctionsTest {

  @Test
  def testReturnFunction(): Unit = {
    def curlBuilder(isGet: Boolean, domainName: String): (String, String) => String = {
      val begin = if (isGet) "curl -XGET " else "curl -XPOST "
      (key: String, value: String) => s"$begin -H '$key: $value' $domainName"
    }

    def getBuilder = curlBuilder(isGet = true, "www.example.com")

    val actualResult = getBuilder("Content-type", "application/json")

    assertEquals("curl -XGET  -H 'Content-type: application/json' www.example.com", actualResult)
  }

  @Test
  def testReturnFunction2(): Unit = {
    def curlBuilder(isGet: Boolean, domainName: String): (String, String) => String = {
      val begin = if (isGet) "curl -XGET " else "curl -XPOST -d''"
      val result = (key: String, value: String) => s"$begin -H '$key: $value' $domainName"
      result
    }

    def getBuilder = curlBuilder(isGet = false, "www.example.com")

    val actualResult = getBuilder("Content-type", "application/json")

    assertEquals("curl -XPOST -d'' -H 'Content-type: application/json' www.example.com", actualResult)
  }

  @Test
  def testMultiple(): Unit = {
    def textBuilder(compose: Seq[(Int, String) => String]): ((Int, String) => String) = {
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
    def textBuilder(compose: Seq[String => String]): ((String, Int) => String) = {
      compose.tail.foldLeft(compose.head) { (aggF, elF) => ???
        // (key: Int, value: String) => aggF(key, value) + ";" + elF(key, value)
      }
      ???
    }

  }
}
