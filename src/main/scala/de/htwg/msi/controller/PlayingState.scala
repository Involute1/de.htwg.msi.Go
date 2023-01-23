package de.htwg.msi.controller

import de.htwg.msi.controller.{TControllerState, TGameController}
import de.htwg.msi.model.{Field, GameData, Player}
import de.htwg.msi.util.Constants.alphabetList

case class PlayingState(gameData: GameData) extends TControllerState {
  override def evaluate(input: String): Either[TControllerState, String] = {
    input match {
      case "forfeit" =>
        Left(nextState(gameData))
      case _ =>
        if (!gameData.isMoveInputValid(input)) return Right("Input invalid try again")
        val gameDataWithPlacedStone: GameData = gameData.copy(board = gameData.placeStone(input), turn = gameData.turn + 1)
        Left(this.copy(gameData = gameDataWithPlacedStone))
    }
  }

  override def nextState(gameData: GameData): TControllerState = ForfeitState(gameData)

  override def getControllerMessage(): String = {
    val currentPlayer: Player = gameData.getCurrentPlayer
    printGameBoard(gameData.board) +
      """
        |Player %s enter one of the following:
        |""".stripMargin.format(currentPlayer.name) +
      "\r\n" + gameData.availableMovesAsString(currentPlayer.color) +
      """
        |Or type forfeit to forfeit
        |""".stripMargin
  }

  def printGameBoard(board: List[List[Field]]): String = {
    val header: String = ("0 " :: alphabetList).slice(0, board.length + 1).mkString("  ")
    val empty: String = ("  " :: List.fill(board.length)("-")).mkString("  ")
    val boardAsString: List[String] = board.zipWithIndex.map(row => alphabetList(row._2) + " | " + row._1.map(field => field.toPrettyString).mkString("  "))
    header + " \r\n" + empty + " \r\n" + boardAsString.mkString("\r\n")
  }
}
