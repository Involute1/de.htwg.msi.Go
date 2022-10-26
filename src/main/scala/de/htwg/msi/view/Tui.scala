package de.htwg.msi.view

import de.htwg.msi.controller.TGameController
import de.htwg.msi.util.Observer

class Tui(controller: TGameController) extends Observer[Any] {

  controller.addObserver(this)
  def processInputLine(input: String): Unit = {
    input match {
      case "q" =>
      case _ => controller.eval(input)
    }
  }

  override def receiveUpdate(subject: Any, errorMsg: Option[String]): Boolean = {
    if (errorMsg.isDefined) println(Console.RED + errorMsg.get )
    println(Console.RESET + controller.getControllerState.getControllerMessage())
    true
  }
}
