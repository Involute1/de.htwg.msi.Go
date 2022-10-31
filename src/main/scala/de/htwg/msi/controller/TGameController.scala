package de.htwg.msi.controller

import de.htwg.msi.model.{Field, GameData, PlayerColor}
import de.htwg.msi.util.Subject

trait TGameController() extends Subject[Any] {
  def eval(input: String): Unit
  def printGameBoard(board: List[List[Field]]): String
  def printActions(playerColor: PlayerColor, currentGameData: GameData): String
  def updateControllerState(nextState: TControllerState): TControllerState
  def getControllerState: TControllerState
}
