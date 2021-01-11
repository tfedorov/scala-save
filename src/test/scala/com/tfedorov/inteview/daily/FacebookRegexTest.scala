package com.tfedorov.inteview.daily

import org.junit.jupiter.api.Assertions.{assertEquals, fail}
import org.junit.jupiter.api.Test

/*

Implement regular expression matching with the following special characters:

. (period) which matches any single character
* (asterisk) which matches zero or more of the preceding element
That is, implement a function that takes in a string and a valid regular expression and returns whether
or not the string matches the regular expression.

For example, given the regular expression "ra." and the string "ray", your function should return true.
 The same regular expression on the string "raymond" should return false.

Given the regular expression ".*at" and the string "chat", your function should return true.
The same regular expression on the string "chats" should return false.
 */
class FacebookRegexTest {

  @Test
  def default1Test(): Unit = {
    val inputText = "ray"
    val inputReg = "ra."

    val actualResult: Boolean = check(inputText, inputReg)

    assertEquals(true, actualResult)
  }

  @Test
  def default2Test(): Unit = {
    val inputText = "raymond"
    val inputReg = "ra."

    val actualResult: Boolean = check(inputText, inputReg)

    assertEquals(false, actualResult)
  }

  @Test
  def extra1Test(): Unit = {
    val inputText = "ra"
    val inputReg = "ra."

    val actualResult: Boolean = check(inputText, inputReg)

    assertEquals(false, actualResult)
  }


  @Test
  def defaultAsterTest(): Unit = {
    val inputText = "chat"
    val inputReg = ".*at"

    val actualResult: Boolean = check(inputText, inputReg)

    assertEquals(true, actualResult)
  }

  @Test
  def defaultAster2Test(): Unit = {
    val inputText = "chats"
    val inputReg = ".*at"

    val actualResult: Boolean = check(inputText, inputReg)

    assertEquals(false, actualResult)
  }

  @Test
  def customAsterTest(): Unit = {
    val inputText = "hhhat"
    val inputReg = "h*at"

    val actualResult: Boolean = check(inputText, inputReg)

    assertEquals(true, actualResult)
  }

  @Test
  def customAster2Test(): Unit = {
    val inputText = "ahhat"
    val inputReg = "bh*at"

    val actualResult: Boolean = check(inputText, inputReg)

    assertEquals(false, actualResult)
  }

  @Test
  def customAster3Test(): Unit = {
    val inputText = "bhhat"
    val inputReg = "bh*at"

    val actualResult: Boolean = check(inputText, inputReg)

    assertEquals(true, actualResult)
  }

  @Test
  def customAster4Test(): Unit = {
    val inputText = "hbhhat"
    val inputReg = "cbh*at"

    val actualResult: Boolean = check(inputText, inputReg)

    assertEquals(false, actualResult)
  }

  @Test
  def customAster5Test(): Unit = {
    val inputText = "bhhat"
    val inputReg = "bhh*at"

    val actualResult: Boolean = check(inputText, inputReg)

    assertEquals(true, actualResult)
  }

  abstract sealed class Element {
    def begin: Int

    def checkEl(input: Char): Boolean
  }

  private case class Lit(value: Char, begin: Int) extends Element {
    override def checkEl(input: Char): Boolean = value.equals(input)
  }

  private case class Point(begin: Int) extends Element {
    override def checkEl(input: Char): Boolean = true
  }

  private case class Aster(valueBefore: Element) extends Element {

    override def checkEl(input: Char): Boolean = valueBefore.checkEl(input)

    override def begin: Int = valueBefore.begin
  }

  private case class ElementConstructAgg(detectedEls: Seq[Element], isAster: Boolean = false)

  private case class ElementChecker(superElements: Seq[Element], workingElements: Seq[Element], workingText: String) {
    def isCompleted: Boolean = if (workingElements.isEmpty || workingText.isEmpty)
      true
    else
      false

    def proceedEl(): ElementChecker = copy(workingElements = workingElements.init, workingText = workingText.init)

    def move2super(): ElementChecker = copy(superElements = superElements :+ workingElements.last, workingElements = workingElements.init)
  }

  private def check(inputText: String, inputReg: String): Boolean = {

    val splitReg = inputReg.zipWithIndex.foldRight(ElementConstructAgg(Nil)) { (charI: (Char, Int), agg: ElementConstructAgg) =>
      var charResult = agg
      if (agg.isAster) {
        val detectedAsterEl = charI match {
          case ('.', index) => Aster(Point(index))
          case (ch, index) => Aster(Lit(ch, index))
        }
        charResult = charResult.copy(charResult.detectedEls :+ detectedAsterEl, false)
      } else if (charI._1.equals('*'))
        charResult = charResult.copy(isAster = true)
      else {
        val detectedEl = charI match {
          case ('.', index) => Point(index)
          case (ch, index) => Lit(ch, index)
        }
        charResult = charResult.copy(detectedEls = agg.detectedEls :+ detectedEl)
      }
      charResult
    }
    val metaSeq = splitReg.detectedEls.reverse
    var checker = ElementChecker(Nil, metaSeq, inputText)
    while (!checker.isCompleted) {
      val currentEl = checker.workingElements.last
      val currentChar = checker.workingText.last
      var currentElCheck = false
      var superElCheck = false
      if (currentEl.isInstanceOf[Aster])
        checker = checker.move2super()
      else {
        if (currentEl.checkEl(currentChar))
          currentElCheck = true
        if (checker.superElements.exists(_.checkEl(currentChar)))
          superElCheck = true

        val validSuper = checker.superElements.filter(_.checkEl(currentChar))
        checker = checker.copy(superElements = validSuper)
        (superElCheck, currentElCheck) match {
          case (false, false) => return false
          case (false, true) => checker = checker.proceedEl()
          case (true, false) => checker = checker.copy(workingText = checker.workingText.init)
          case (true, true) => checker = checker.proceedEl()
        }
      }
    }
    true
  }

}
