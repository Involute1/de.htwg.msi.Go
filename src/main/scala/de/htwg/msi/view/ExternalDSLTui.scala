package de.htwg.msi.view

import de.htwg.msi.controller.*
import de.htwg.msi.model.{SgfData, SgfGameData}

class ExternalDSLTui {
  val parser: ExternalDSLParser = ExternalDSLParser()
  val controller: TGameController = ExternalDSLController(parser, SgfData(SgfGameData(0, "", ""), Nil))

  def processInputLine(input: String): Unit = {
    input match {
      case "q" =>
      case _ => {
        controller.eval(input).fold(
          newController => receiveUpdate(None),
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
