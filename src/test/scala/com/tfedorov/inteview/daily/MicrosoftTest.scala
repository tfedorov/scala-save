package com.tfedorov.inteview.daily

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

import scala.annotation.tailrec

/*
 Microsoft.

Given a dictionary of words and a string made up of those words (no spaces), return the original sentence in a list.
If there is more than one possible reconstruction, return any of them.
If there is no possible reconstruction, then return null.

For example, given the set of words 'quick', 'brown', 'the', 'fox', and the string "thequickbrownfox",
you should return ['the', 'quick', 'brown', 'fox'].

Given the set of words 'bed', 'bath', 'bedbath', 'and', 'beyond', and the string "bedbathandbeyond",
 return either ['bed', 'bath', 'and', 'beyond] or ['bedbath', 'and', 'beyond'].
 */
class MicrosoftTest {

  private def findRecursive(currentAgg: Seq[Seq[String]], leaveDict: Seq[String], sentence: String): Seq[Seq[String]] = {
    if (leaveDict.isEmpty || sentence.isEmpty)
      return currentAgg

    val candidates = leaveDict.filter(sentence.startsWith)
    candidates.flatMap { candidate: String =>
      val nextAgg = if (currentAgg.isEmpty)
        Seq(candidate) :: Nil
      else
        currentAgg.map(_ :+ candidate)
      val nextLeave = leaveDict.filterNot(_.equals(candidate))
      val nextSentences = sentence.drop(candidate.length)
      findRecursive(nextAgg, nextLeave, nextSentences)
    }
  }

  def findVariants(dict: Seq[String], sentence: String): Seq[Seq[String]] = {
    findRecursive(Nil, dict, sentence)
  }

  @Test
  def dictDefault1Test(): Unit = {
    val dict = Seq("quick", "brown", "the", "fox")
    val sentence = "thequickbrownfox"

    val actualResult: Seq[Seq[String]] = findVariants(dict, sentence)

    val expectedResult = Seq(Seq("the", "quick", "brown", "fox"))
    assertEquals(expectedResult, actualResult)
  }

  @Test
  def dictDefault2Test(): Unit = {
    val dict = Seq("bed", "bath", "bedbath", "and", "beyond")
    val sentence = "bedbathandbeyond"

    val actualResult: Seq[Seq[String]] = findVariants(dict, sentence)

    val expectedResult = Seq("bed", "bath", "and", "beyond") :: Seq("bedbath", "and", "beyond") :: Nil
    assertEquals(expectedResult, actualResult)
  }
}
