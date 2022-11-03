package de.htwg.msi.controller

import de.htwg.msi.controller.{GameOverState, TControllerState, TGameController}
import de.htwg.msi.model.GameData

case class ForfeitState(controller: TGameController, gameData: GameData) extends TControllerState {
  override def evaluate(input: String): Option[String] = {
    if (input.isEmpty) return Some("Input can´t be empty")
    input match {
      case "y" | "yes" => {
        controller.updateControllerState(nextState(gameData))
        None
      }
      case "n" | "no" => {
        controller.updateControllerState(PlayingState(controller, gameData))
        None
      }
      case _ => Some("Please type yes or no")
    }
  }

  override def nextState(gameData: GameData): TControllerState = GameOverState(controller, gameData)

  override def getControllerMessage(): String = {
    """
      |Player %s wants to forfeit
      |You have to agree to the forfeit in order to end the game
      |Type yes or no
      |""".stripMargin.format(gameData.getCurrentPlayer.name)
  }
}