package com.tfedorov.inteview.leetcode

/*
Given an input string s and a pattern p, implement regular expression matching with support for '.' and '*' where:

'.' Matches any single character.
'*' Matches zero or more of the preceding element.
The matching should cover the entire input string (not partial).



Example 1:

Input: s = "aa", p = "a"
Output: false
Explanation: "a" does not match the entire string "aa".
Example 2:

Input: s = "aa", p = "a*"
Output: true
Explanation: '*' means zero or more of the preceding element, 'a'. Therefore, by repeating 'a' once, it becomes "aa".
Example 3:

Input: s = "ab", p = ".*"
Output: true
Explanation: ".*" means "zero or more (*) of any character (.)".

 */
object RegexTest {

  def splitOnToken(pattern: String) = {
    val result: Seq[String] = (pattern + " ").sliding(size = 2, step = 1).toSeq
      .collect {
        //        case t if t.startsWith("*") => Seq.empty
        //        case t if t.startsWith(".") => Seq.empty
        case startEndingToken if startEndingToken.endsWith("*") => startEndingToken
        case pointToken if pointToken.startsWith(".") => pointToken.head.toString
        case letterToken if letterToken.charAt(0) >= 'a' && letterToken.charAt(0) <= 'z' => letterToken.head.toString
      }
    result.toList
  }


  def isMatch(s: String, p: String) = {
    val patternTokens = splitOnToken(p)
    checkToken(s, patternTokens)
  }

  def checkToken(s: String, tokens: List[String]): Boolean = {
    if (s.isEmpty)
      return (tokens.isEmpty) || (tokens.length == 1 & tokens.head.endsWith("*"))
    if (tokens.isEmpty)
      return false
    val currentText = s.head

    tokens.head match {
      case "." => {
        println("letterToken ." + ": currentText " + currentText)
        checkToken(s.tail, tokens.tail)
      }

      case starEnd if starEnd.endsWith("*") && currentText == starEnd.head => {
        println("letterToken " + starEnd + ": currentText " + currentText)
        checkToken(s.tail, tokens)
      }
      case starEnd if starEnd.endsWith("*") && currentText != starEnd.head => {
        println("letterToken " + starEnd + ": currentText " + currentText)
        checkToken(s, tokens.tail)
      }
      case letterToken if letterToken.head >= 'a' && letterToken.head <= 'z' && letterToken.head != currentText => {
        println("letterToken " + letterToken + ": currentText " + currentText)
        false
      }
      case pointStarToken if pointStarToken.startsWith(".") && pointStarToken.endsWith("*") =>
        true

      case letterToken if letterToken.head >= 'a' && letterToken.head <= 'z' && letterToken.head == currentText => {
        println("letterToken " + letterToken + ": currentText " + currentText)
        checkToken(s.tail, tokens.tail)
      }
    }
  }

  def main(args: Array[String]) = {
    println(isMatch("aa", "a."))
    println(isMatch("aa", "a"))
    println(isMatch("aaab", "a*b."))
    println(isMatch("aaabc", "a*b."))
    println(isMatch("aa", "a*"))
    println(isMatch("ab", ".*c"))
  }
}
