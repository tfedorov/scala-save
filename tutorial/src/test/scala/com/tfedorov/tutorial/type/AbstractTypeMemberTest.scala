package com.tfedorov.tutorial.`type`

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class AbstractTypeMemberTest {
  /*
   Abstract types, such as traits and abstract classes, can in turn have abstract type members.
   This means that the concrete implementations define the actual types.
   */
  @Test
  def typeParameterVersion(): Unit = {
    trait FixtureSuite[P1, TMP] {
      def fixtureMethod: P1
    }

    trait StringBuilderFixture {
      this: FixtureSuite[StringBuilder, List[_]] =>
      def stringBuilderFixtureMethod = new StringBuilder("From StringBuilderFixture")
    }

    class SimpleClass extends FixtureSuite[StringBuilder, List[_]] {
      override def fixtureMethod: StringBuilder = new StringBuilder("Simple from class")
    }
    //to much generic
    class MixClass extends FixtureSuite[StringBuilder, List[_]] with StringBuilderFixture {
      override def fixtureMethod: StringBuilder = stringBuilderFixtureMethod
    }

    val actualSimple = new SimpleClass().fixtureMethod
    val actualMix = new MixClass().fixtureMethod
    //to much generic
    val actualInFly = new FixtureSuite[StringBuilder, List[_]] with StringBuilderFixture {
      override def fixtureMethod: StringBuilder = new StringBuilder("Mix on the fly")
    }.fixtureMethod

    assertEquals("Simple from class", actualSimple.toString())
    assertEquals("From StringBuilderFixture", actualMix.toString())
    assertEquals("Mix on the fly", actualInFly.toString())
  }

  @Test
  def typeMemberVersion(): Unit = {
    trait FixtureSuite {
      type P1
      type TMP

      def fixtureMethod: P1
    }

    trait StringBuilderFixture {
      this: FixtureSuite =>
      type P1 = StringBuilder
      type TMP = List[_]

      def stringBuilderFixtureMethod = new StringBuilder("From StringBuilderFixture")
    }

    class SimpleClass extends FixtureSuite {
      override type P1 = StringBuilder
      override type TMP = List[_]

      override def fixtureMethod: StringBuilder = new StringBuilder("Simple from class")
    }

    class MixClass extends FixtureSuite with StringBuilderFixture {
      override def fixtureMethod: StringBuilder = stringBuilderFixtureMethod
    }

    val actualSimple = new SimpleClass().fixtureMethod
    val actualMix = new MixClass().fixtureMethod
    val actualInFly = new FixtureSuite with StringBuilderFixture {
      override def fixtureMethod: P1 = new StringBuilder("Mix on the fly")
    }.fixtureMethod

    assertEquals("Simple from class", actualSimple.toString())
    assertEquals("From StringBuilderFixture", actualMix.toString())
    assertEquals("Mix on the fly", actualInFly.toString())
  }
}
