package de.htwg.msi

import de.htwg.msi.controller.{GameController, InitState}
import de.htwg.msi.view.{ExternalDSLTui, Tui}

@main
def main(args: String*): Unit = {
  var input: String = "";
  if (args.nonEmpty) input = args(0);
  if (input == "DSL") {
    val tui: ExternalDSLTui = ExternalDSLTui();
    tui.receiveUpdate(None)
    while input != "q" do {
      input = scala.io.StdIn.readLine()
      tui.processInputLine(input)
    }
  } else {
    val tui: Tui = Tui();
    tui.receiveUpdate(None)
    while input != "q" do {
      input = scala.io.StdIn.readLine()
      tui.processInputLine(input)
    }
  }
}
