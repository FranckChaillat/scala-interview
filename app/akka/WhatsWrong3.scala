package com.particeep.test.akka

import akka.actor.typed.Behavior
import akka.actor.typed.scaladsl.Behaviors

import scala.concurrent.Future
import scala.util.{Failure, Success}
import scala.concurrent.ExecutionContext.Implicits.global

/**
 * Do you see anything that could lead to potential problems ?
 * What would you do to fix it ?
 * Do not mind about the not implemented code
 *
 * RESPONSE:
 *  The problem is this is a statefull actor and the state can be accessed (read and write) concurrently
 *  What i would do instead is make the actor stateless by implementing recursion
 */
object WhatsWrong3 {
  def apply(): Behavior[String] = Behaviors.setup { context =>
    receive(0)
  }

  def receive(state: Int): Behavior[String] = Behaviors.receive { (_, message) =>
    message match {
      case "a query" =>
        val requestF: Future[String] = queryAsyncServer()
        requestF.onComplete {
          case Success(r) => receive(r.toInt + state) // EXAMPLE of modification
          case Failure(e) => e.printStackTrace()
        }
        Behaviors.same

    }
  }

  /*def receive: Receive = {
    case "a query" => {
      val requestF: Future[String] = queryAsyncServer()
      requestF.onComplete {
        case Success(r) => handleResponse(r)
        case Failure(e) => e.printStackTrace()
      }
    }
  }*/

  def handleResponse(r: String) = ??? // mutate internal state

  def queryAsyncServer(): Future[String] = ???
}
