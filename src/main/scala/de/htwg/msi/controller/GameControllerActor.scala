package de.htwg.msi.controller

import akka.actor.{Actor, ActorRef}
import de.htwg.msi.controller.GameSaverActor._

class GameControllerActor(gameSaver: ActorRef) extends Actor {

  import GameControllerActor._

  // Var kann innerhalb vom Actor verwendet werden
  var controllerState: TControllerState = InitState()
  var gameName = "DefaultName"
  var gameId = 0

  def receive: Receive = {
    case Eval(input: String) =>
      controllerState.evaluate(input).fold(
        newState => {
          saveAction(input, newState)
          controllerState = newState
          sender() ! Success(controllerState.getControllerMessage())
        },
        e => sender() ! Error(e)
      )
    case GetControllerMessage =>
      sender() ! Success(controllerState.getControllerMessage())
    case _ => Error("Please provide an eval object")
  }

  def saveAction(input: String, newState: TControllerState): Unit = {
    controllerState match {
      case _: InitState =>
        gameId += 1
        gameSaver ! NewGame(gameName + gameId.toString + ".txt")
        gameSaver ! Size(input)
      case _: PlayerSetupState =>
        newState match {
          case _: PlayerSetupState => gameSaver ! PlayerB(input)
          case _: PlayingState => gameSaver ! PlayerW(input)
        }
      case _: PlayingState =>
        newState match {
          case _: PlayingState => gameSaver ! Move(input)
          case _: ForfeitState => gameSaver ! Move("")
        }
      case _: ForfeitState =>
        newState match {
          case _: GameOverState => {
            gameSaver ! Move("")
            gameSaver ! GameOver
          }
        }
    }
  }
}

object GameControllerActor {
  case class Eval(input: String)

  case class Error(msg: String)

  case class Success(msg: String)

  case object GetControllerMessage
}