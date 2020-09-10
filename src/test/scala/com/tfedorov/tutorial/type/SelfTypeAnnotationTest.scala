package com.tfedorov.tutorial.`type`

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class SelfTypeAnnotationTest {

  @Test
  def typeParameterVersion(): Unit = {
    trait User {
      def username: String
    }

    trait Messenger {
      this: User => // reassign this
      def chat(message: String): String = s"$username: $message"
    }

    trait Agent extends User {
      override def username: String = s"agent Smith"
    }

    class UnSecureMessenger extends Messenger with Agent
    val unSecureMessenger = new UnSecureMessenger
    val agentMessage = unSecureMessenger.chat("Hello, Mr. Anderson")


    class SecureMessenger(val username_ : String) extends Messenger with User {
      def username = s"real $username_"
    }

    val secureMessanger = new SecureMessenger("Neo")
    val neoMessage = secureMessanger.chat("My name.........Is Neo!")

    assertEquals("agent Smith: Hello, Mr. Anderson", agentMessage)
    assertEquals("real Neo: My name.........Is Neo!", neoMessage)
  }

}
