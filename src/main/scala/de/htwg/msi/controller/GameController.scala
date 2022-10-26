package de.htwg.msi.controller

class GameController extends TGameController {
  val controllerState: TControllerState = PreInitGameState(this)
  override def eval(input: String): Unit = {
    controllerState.evaluate(input)
    notifyObservers()
  }

  override def printGameBoard(): String = ???

  override def printActions(): String = ???
}

trait TControllerState {
  def evaluate(input: String): Unit
  def nextState: TControllerState

}

case class PreInitGameState(controller: GameController) extends TControllerState {
  override def evaluate(input: String): Unit = {
    //TODO init gameboard 11x11 9x9 19x19

  }

  override def nextState: TControllerState = PlayerSetupState(controller)
}

case class PlayerSetupState(controller: GameController) extends TControllerState {
  override def evaluate(input: String): Unit = {
    //TODO init players
  }

  override def nextState: TControllerState = PlayingState(controller)
}

case class PlayingState(controller: TGameController) extends TControllerState {
  override def evaluate(input: String): Unit = {
    //TODO place stone
    // forfeit
  }

  override def nextState: TControllerState = GameOverState(controller)
}

case class GameOverState(controller: TGameController) extends TControllerState {
  override def evaluate(input: String): Unit = {
    //TODO count score
  }

  override def nextState: TControllerState = this
}
