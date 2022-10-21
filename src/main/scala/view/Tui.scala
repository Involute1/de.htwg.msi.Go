package de.htwg.msi.go
package view

import controller.GameController

class Tui(controller: GameController) {

  def processInputLine(input: String): Unit = {
    input match {
      case "q" =>
      case _ => controller.eval(input)
    }
  }
}
