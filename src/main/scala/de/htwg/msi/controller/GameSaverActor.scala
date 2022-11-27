package de.htwg.msi.controller

import akka.actor.Actor
import de.htwg.msi.model.{Field, GameData, Player, PlayerColor}
import de.htwg.msi.util.Constants.alphabetList

import java.io.{File, FileWriter, PrintWriter}

class GameSaverActor extends Actor {
  import GameSaverActor._

  // Var kann innerhalb vom Actor verwendet werden
  var controllerState: TControllerState = InitState()


  var file: File = new File("")
  var fileWriter: Option[FileWriter] = None
  var turn = 0;

  def receive: Receive = {
    case NewGame(fileName: String) =>
      file = new File(fileName);
      fileWriter = Some(new FileWriter(file));
      fileWriter.get.write("(;")
    case Size(input: String) =>
      if (fileWriter.isDefined) fileWriter.get.write("SZ[" + input + "]\r\n")
    case PlayerW(input: String) =>
      if (fileWriter.isDefined) fileWriter.get.write("PW[" + input + "]\r\n")
    case PlayerB(input: String) =>
      if (fileWriter.isDefined) fileWriter.get.write("PW[" + input + "]\r\n")
    case Move(move: String) =>
      if (fileWriter.isDefined) {
        var player = "B"
        if (turn % 2 != 0) player = "W"
        fileWriter.get.write(";" + player + "[" + move + "]")
      }
    case GameOver =>
      if (fileWriter.isDefined) {
        fileWriter.get.write(")")
        fileWriter.get.close()
      }

    case _ => Error("Please provide an eval object")
  }
}

object GameSaverActor {
  case class NewGame(fileName: String)
  case class Size(input: String)
  case class PlayerW(input: String)
  case class PlayerB(input: String)
  case class Move(move: String)
  case object GameOver
}