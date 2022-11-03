package de.htwg.msi.controller

import de.htwg.msi.model.{Field, GameData, Player, PlayerColor}
import de.htwg.msi.util.Constants.alphabetList

case class GameController() extends TGameController {
  //TODO find way to val this
  var controllerState: TControllerState = InitState(this)

  override def eval(input: String): Unit = {
    val errorMsg: Option[String] = controllerState.evaluate(input)
    notifyObservers(errorMsg)
  }

  override def printGameBoard(board: List[List[Field]]): String = {
    val header: String = ("0 " :: alphabetList).slice(0, board.length + 1).mkString("  ")
    val empty: String = ("  " :: List.fill(board.length)("-")).mkString("  ")
    val boardAsString: List[String] = board.zipWithIndex.map((row, idx) => alphabetList(idx) + " | " + row.map(field => field.toPrettyString).mkString("  "))
    header + " \r\n" + empty + " \r\n" + boardAsString.mkString("\r\n")
  }

  override def updateControllerState(nextState: TControllerState): TControllerState = {
    controllerState = nextState
    nextState
  }

  override def getControllerState: TControllerState = {
    controllerState
  }
}