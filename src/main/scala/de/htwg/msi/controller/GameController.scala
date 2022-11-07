package de.htwg.msi.controller

import de.htwg.msi.model.{Field, GameData, Player, PlayerColor}
import de.htwg.msi.util.Constants.alphabetList

case class GameController(controllerState: TControllerState) extends TGameController {

  override def eval(input: String): Either[TGameController, String] = {
    controllerState.evaluate(input).fold(
      newState => Left(this.copy(controllerState = newState)),
      e => Right(e)
    )
  }

  override def getControllerState: TControllerState = {
    controllerState
  }
}