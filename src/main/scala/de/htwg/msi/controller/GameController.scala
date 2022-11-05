package de.htwg.msi.controller

import de.htwg.msi.model.{Field, GameData, Player, PlayerColor}
import de.htwg.msi.util.Constants.alphabetList

case class GameController() extends TGameController {
  //TODO find way to val this
  var controllerState: TControllerState = InitState(this)

  override def eval(input: String): Unit = {
    controllerState.evaluate(input).fold(
      newState => {
        controllerState = newState
        notifyObservers(None)
      },
      e => notifyObservers(Option.apply(e)),
    )
  }

  override def printGameBoard(board: List[List[Field]]): String = {
    val header: String = ("0 " :: alphabetList).slice(0, board.length + 1).mkString("  ")
    val empty: String = ("  " :: List.fill(board.length)("-")).mkString("  ")
    val boardAsString: List[String] = board.zipWithIndex.map((row, idx) => alphabetList(idx) + " | " + row.map(field => field.toPrettyString).mkString("  "))
    header + " \r\n" + empty + " \r\n" + boardAsString.mkString("\r\n")
  }

  override def getControllerState: TControllerState = {
    controllerState
  }
}