package de.htwg.msi.go
package view

import controller.TGameController

class Tui(controller: TGameController) {

  def processInputLine(input: String): Unit = {
    input match {
      case "q" =>
      case _ => controller.eval(input)
    }
  }
}
