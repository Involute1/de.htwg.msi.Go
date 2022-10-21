package de.htwg.msi.go

import view.Tui

import de.htwg.msi.go.controller.GameController

@main
def main(args: String*): Unit = {
  val controller: GameController = GameController();
  val tui: Tui = Tui(controller);
  var input: String = "";
  if (args.nonEmpty) input = args(0);
  if (input.nonEmpty) {
    tui.processInputLine(input)
  } else {
    while (input != "q") do {
      input = scala.io.StdIn.readLine()
      tui.processInputLine(input)
    }
  }
}
