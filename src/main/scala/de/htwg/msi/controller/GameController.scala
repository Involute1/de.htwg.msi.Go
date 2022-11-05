package de.htwg.msi.controller

import de.htwg.msi.model.{Field, GameData, Player, PlayerColor}
import de.htwg.msi.util.Constants.alphabetList

case class GameController() extends TGameController {
  //TODO find way to val this
  var controllerState: TControllerState = InitState()

  override def eval(input: String): Unit = {
    controllerState.evaluate(input).fold(
      newState => {
        controllerState = newState
        notifyObservers(None)
      },
      e => notifyObservers(Option.apply(e)),
    )
  }
  
  override def getControllerState: TControllerState = {
    controllerState
  }
}