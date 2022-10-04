package com.tfedorov.tutorial.functions

import org.junit.jupiter.api.Assertions._
import org.junit.jupiter.api.Test

class HigherOrderFunctionsTest {

  @Test
  def methodReturnFunction(): Unit = {
    val input = 1 to 10

    //    def moreThan(check: Int): Int => Boolean = {
    //      (i: Int) => i > check
    //    }
    def moreThan(check: Int): Int => Boolean = _ > check

    val actual9 = moreThan(8)(9)
    //val actual8 = input.filter(el => moreThan(8)(el))
    val actual8 = input.filter(moreThan(8))
    val actual6 = input.filter(moreThan(6))

    assertTrue(actual9)
    assertEquals(9 :: 10 :: Nil, actual8)
    assertEquals(7 :: 8 :: 9 :: 10 :: Nil, actual6)
  }

  @Test
  def methodWithParamFunction(): Unit = {
    var iAmCalled = false

    //def methodWithParam(innerFunc: Function0[_]): Unit = innerFunc()
    def methodWithParam(innerFunc: () => Any): Unit = innerFunc()

    //    def func(): Unit = iAmCalled = true
    //    method(func)
    //    method(() => {
    //      iAmCalled = true
    //    })
    methodWithParam {
      iAmCalled = true
      return
    }

    assertTrue(iAmCalled)
  }

  @Test
  def returnGetCurlFunction(): Unit = {
    //    def curlBuilder(isGet: Boolean, domainName: String): (String, String) => String = {
    //      val begin = if (isGet) "curl -XGET " else "curl -XPOST "
    //      val returnFunc: (String, String) => String = (key: String, value: String) => {
    //        s"$begin -H '$key: $value' $domainName"
    //      }
    //      returnFunc
    //    }
    def curlBuilder(isGet: Boolean, domainName: String): (String, String) => String = {
      val begin = if (isGet) "curl -XGET " else "curl -XPOST "
      (key: String, value: String) => s"$begin -H '$key: $value' $domainName"
    }

    def getBuilderF = curlBuilder(isGet = true, "www.example.com")

    val actualResult = getBuilderF("Content-type", "application/json")

    assertEquals("curl -XGET  -H 'Content-type: application/json' www.example.com", actualResult)
  }

  @Test
  def returnPostCurlFunction(): Unit = {
    def curlBuilder(isGet: Boolean, domainName: String): (String, String) => String = {
      val begin = if (isGet) "curl -XGET " else "curl -XPOST -d''"
      val result = (key: String, value: String) => s"$begin -H '$key: $value' $domainName"
      result
    }

    def postBuilderF: (String, String) => String = curlBuilder(isGet = false, "www.example.com")

    val actualResult = postBuilderF("Content-type", "application/json")

    assertEquals("curl -XPOST -d'' -H 'Content-type: application/json' www.example.com", actualResult)
  }

  @Test
  def seqFunctions(): Unit = {
    def textBuilder(compose: Seq[(Int, String) => String]): (Int, String) => String = {
      compose.tail.foldLeft(compose.head) { (aggF, elF) =>
        (key: Int, value: String) => aggF(key, value) + ";" + elF(key, value)
      }
    }

    def multi(key: Int, value: String): String = value * key

    def add(key: Int, value: String): String = key + value

    //def multiAddFSeq: Seq[(Int, String) => String] = (multi(_, _)) :: (add(_, _)) :: Nil
    def multiAddFSeq: Seq[(Int, String) => String] = multi _ :: (add(_, _)) :: Nil

    def multiAddF = textBuilder(multi _ :: add _ :: Nil)

    def multiAddMultiF = textBuilder(multi _ :: add _ :: multi _ :: Nil)

    val actualResult1 = multiAddF(3, "a")
    val actualResult2 = multiAddMultiF(4, "b")

    assertEquals("aaa;3a", actualResult1)
    assertEquals("aaa;3a", textBuilder(multiAddFSeq)(3, "a"))
    assertEquals("bbbb;4b;bbbb", actualResult2)
  }

  @Test
  def seqFunctionsFoldLeft(): Unit = {
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
