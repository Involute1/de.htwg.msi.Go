package de.htwg.msi

import de.htwg.msi.controller.GameController
import de.htwg.msi.view.Tui

@main
def main(args: String*): Unit = {
  val controller: GameController = GameController();
  val tui: Tui = Tui(controller);
  controller.notifyObservers(None)
  var input: String = "";
  if (args.nonEmpty) input = args(0);
  while (input != "q") do {
    input = scala.io.StdIn.readLine()
    tui.processInputLine(input)
  }
}
