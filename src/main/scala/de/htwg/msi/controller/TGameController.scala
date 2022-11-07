package de.htwg.msi.controller

import de.htwg.msi.model.{Field, GameData, PlayerColor}

trait TGameController() {
  def eval(input: String): Either[TGameController, String]

  def getControllerState: TControllerState
}
