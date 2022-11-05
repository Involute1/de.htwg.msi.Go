package de.htwg.msi.controller

import de.htwg.msi.model.{Field, GameData, PlayerColor}
import de.htwg.msi.util.Subject

trait TGameController() extends Subject[Any] {
  def eval(input: String): Unit

  def printGameBoard(board: List[List[Field]]): String

  def getControllerState: TControllerState
}
