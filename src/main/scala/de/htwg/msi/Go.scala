package de.htwg.msi

import de.htwg.msi.controller.{GameController, InitState}
import de.htwg.msi.view.Tui

@main
def main(args: String*): Unit = {
  val controller: GameController = GameController();
  val tui: Tui = Tui(controller);
  controller.notifyObservers(None)
  var input: String = "";
  while input != "q" do {
    input = scala.io.StdIn.readLine()
    tui.processInputLine(input)
  }
}
