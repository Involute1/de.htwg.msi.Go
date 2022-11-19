package de.htwg.msi.controller

import de.htwg.msi.controller.{GameOverState, TControllerState, TGameController}
import de.htwg.msi.model.GameData

case class ExternalDSLState() extends TControllerState {
  override def evaluate(input: String): Either[TControllerState, String] = {
    Left(this)
  }

  override def nextState(gameData: GameData): TControllerState = this

  override def getControllerMessage(): String = {
    """
      |Please provide a sgf file for parsing:
      |""".stripMargin
  }
}