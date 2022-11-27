package de.htwg.msi.view

import akka.actor.{Actor, ActorRef, ActorSystem, Props}
import de.htwg.msi.controller.{GameController, GameControllerActor, InitState, TGameController}

class ActorTui(gameController: ActorRef) extends Actor {
  import ActorTui._

  def receive = {
    case Eval(input: String) => gameController ! GameControllerActor.Eval(input)
    case Message => gameController ! GameControllerActor.GetControllerMessage
    case GameControllerActor.Success(msg: String) => println(Console.RESET + msg)
    case GameControllerActor.Error(msg: String) =>
      println(Console.RED + msg)
      gameController ! GameControllerActor.GetControllerMessage
  }
}

object ActorTui {
  case class Eval(input: String)
  case object Message
}
