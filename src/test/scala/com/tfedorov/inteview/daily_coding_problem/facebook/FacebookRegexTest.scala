package com.tfedorov.inteview.daily_coding_problem.facebook

import org.junit.jupiter.api.Assertions.assertEquals
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

  @Test
  def customAster6Test(): Unit = {
    val inputText = "hhat"
    val inputReg = "bh*at"

    val actualResult: Boolean = check(inputText, inputReg)

    assertEquals(false, actualResult)
  }

  abstract sealed class RegElement {
    def checkEl(input: Char): Boolean
  }

  private case class Lit(value: Char) extends RegElement {
    override def checkEl(input: Char): Boolean = value.equals(input)
  }

  private class Point() extends RegElement {
    override def checkEl(input: Char): Boolean = true
  }

  private case class Aster(valueBefore: RegElement) extends RegElement {
    override def checkEl(input: Char): Boolean = valueBefore.checkEl(input)
  }


  private case class ElementChecker(superElements: Seq[RegElement], workingElements: Seq[RegElement], workingText: String) {
    def isOver: Boolean = if (workingElements.isEmpty || workingText.isEmpty)
      true
    else
      false

    def proceedElAndChar(): ElementChecker = copy(workingElements = workingElements.init, workingText = workingText.init)

    def proceedChar(): ElementChecker = copy(workingText = workingText.init)

    def move2super(): ElementChecker = copy(superElements = superElements :+ workingElements.last, workingElements = workingElements.init)

    def isComplete: Boolean = {
      if (workingElements.isEmpty && workingText.isEmpty)
        return true
      if (workingElements.nonEmpty && workingText.isEmpty)
        return false

      if (superElements.nonEmpty) {
        superElements.foreach { superElement =>
          val notMatched = workingText.filter(!superElement.checkEl(_))
          if (notMatched.isEmpty)
            return true
        }
      }
      false
    }
  }

  private def check(inputText: String, inputReg: String): Boolean = {

    val metaSeq: Seq[RegElement] = buildReqElements(inputReg)
    var checker = ElementChecker(Nil, metaSeq, inputText)

    while (!checker.isOver) {
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

        checker = (superElCheck, currentElCheck) match {
          case (false, false) => return false
          case (false, true) => checker.proceedElAndChar()
          case (true, false) => checker.proceedChar()
          case (true, true) => checker.proceedElAndChar()
        }
      }
    }
    checker.isComplete
  }

  private def buildReqElements(inputReg: String): Seq[RegElement] = {
    case class ElementConstructAgg(detectedEls: Seq[RegElement], isAster: Boolean = false)
    val splitReg = inputReg.foldRight(ElementConstructAgg(Nil)) { (charEl: Char, agg: ElementConstructAgg) =>
      var charResult = agg
      if (agg.isAster) {
        val detectedAsterEl = charEl match {
          case '.' => Aster(new Point())
          case ch => Aster(Lit(ch))
        }
        charResult = charResult.copy(charResult.detectedEls :+ detectedAsterEl, isAster = false)
      } else if (charEl.equals('*'))
        charResult = charResult.copy(isAster = true)
      else {
        val detectedEl = charEl match {
          case '.' => new Point()
          case ch => Lit(ch)
        }
        charResult = charResult.copy(detectedEls = agg.detectedEls :+ detectedEl)
      }
      charResult
    }
    splitReg.detectedEls.reverse
  }
}
