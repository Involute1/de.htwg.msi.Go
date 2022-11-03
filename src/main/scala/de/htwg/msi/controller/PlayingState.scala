package de.htwg.msi.controller

import de.htwg.msi.controller.{TControllerState, TGameController}
import de.htwg.msi.model.{GameData, Player}

case class PlayingState(controller: TGameController, gameData: GameData) extends TControllerState {
  override def evaluate(input: String): Option[String] = {
    input match {
      case "forfeit" =>
        controller.updateControllerState(nextState(gameData))
        None
      case _ =>
        if (!gameData.isMoveInputValid(input)) return Some("Input invalid try again")
        val gameDataWithPlacedStone: GameData = gameData.copy(board = gameData.placeStone(input), turn = gameData.turn + 1)
        controller.updateControllerState(this.copy(gameData = gameDataWithPlacedStone))
        None
    }
  }

  override def nextState(gameData: GameData): TControllerState = ForfeitState(controller, gameData)

  override def getControllerMessage(): String = {
    val currentPlayer: Player = gameData.getCurrentPlayer
    controller.printGameBoard(gameData.board) +
      """
        |Player %s enter one of the following:
        |""".stripMargin.format(currentPlayer.name) +
      "\r\n" + gameData.availableMovesAsString(currentPlayer.color) +
      """
        |Or type forfeit to forfeit
        |""".stripMargin
  }
}