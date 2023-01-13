package com.particeep.test.akka

import akka.actor.{ Actor, ActorSystem, Props }

/**
 * Question about Akka framework http://akka.io
 *
 * When receiving a message that says "Hello", BasicActor must print "Hello there."
 * It must print "What?" when receiving any other message
 */
class BasicActor extends Actor {
  override def receive: Receive = {
    case "Hello" =>
      println("Hello there.")
    case _ => println("What?")
  }
}

// Run it here
object FireActor extends App {

  /**
   * Create an instance of BasicActor
   *
   * Make it print "Hello there." and "What?"
   */
  def fireActor(): Unit = {
    val system = ActorSystem("BasicActorSystem")
    val basic_actor = system.actorOf(Props[BasicActor], name = "basicactor")
    basic_actor ! "Hello" // prints Hello there.
    basic_actor ! "foo bar" // prints What?
  }

  fireActor()
}
