package de.htwg.msi.view

import de.htwg.msi.controller.{GameController, InitState, TGameController}

class Tui {
  var controller: TGameController = GameController(InitState())

  def processInputLine(input: String): Unit = {
    input match {
      case "q" =>
      case _ => {
        controller.eval(input).fold(
          newController => {
            controller = newController
            receiveUpdate(None)
          },
          e => receiveUpdate(Option.apply(e))
        )
      }
    }
  }

  def receiveUpdate(errorMsg: Option[String]): Boolean = {
    if (errorMsg.isDefined) println(Console.RED + errorMsg.get)
    println(Console.RESET + controller.getControllerState.getControllerMessage())
    true
  }
}
