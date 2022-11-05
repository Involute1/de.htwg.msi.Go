package de.htwg.msi.controller

import de.htwg.msi.controller.{TControllerState, TGameController}
import de.htwg.msi.model.GameData

case class PlayerSetupState(controller: TGameController, gameData: GameData) extends TControllerState {
  override def evaluate(input: String): Either[TControllerState, String] = {
    if (input.isEmpty) return Right("Input can`t be empty")
    val gameDataWithPlayer = gameData.copy(players = gameData.initPlayer(input))
    if (gameDataWithPlayer.players.length < 2) {
      Left(this.copy(gameData = gameDataWithPlayer))
    } else {
      Left(nextState(gameData = gameDataWithPlayer))
    }
  }

  override def nextState(gameData: GameData): TControllerState = PlayingState(controller, gameData)

  override def getControllerMessage(): String = {
    """
      |Player %d enter your Name:
      |""".stripMargin.format(gameData.players.length + 1)
  }
}