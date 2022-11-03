package de.htwg.msi.controller

import de.htwg.msi.model.GameData

trait TControllerState {
  def evaluate(input: String): Option[String]

  def nextState(gameData: GameData): TControllerState

  def getControllerMessage(): String
}
