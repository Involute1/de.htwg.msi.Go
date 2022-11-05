package de.htwg.msi.controller

import de.htwg.msi.controller.{GameOverState, TControllerState, TGameController}
import de.htwg.msi.model.GameData

case class ForfeitState(gameData: GameData) extends TControllerState {
  override def evaluate(input: String): Either[TControllerState, String] = {
    if (input.isEmpty) return Right("Input can´t be empty")
    input match {
      case "y" | "yes" => {
        Left(nextState(gameData))
      }
      case "n" | "no" => {
        Left(PlayingState(gameData.copy(turn = gameData.turn + 1)))
      }
      case _ => Right("Please type yes or no")
    }
  }

  override def nextState(gameData: GameData): TControllerState = GameOverState(gameData)

  override def getControllerMessage(): String = {
    """
      |Player %s wants to forfeit
      |You have to agree to the forfeit in order to end the game
      |Type yes or no
      |""".stripMargin.format(gameData.getCurrentPlayer.name)
  }
}