package de.htwg.msi

import de.htwg.msi.controller.{GameController, InitState}
import de.htwg.msi.model.{GameData, GameDataDSL}
import de.htwg.msi.view.Tui

@main
def main(args: String*): Unit = {
    val tui: Tui = Tui()
    tui.receiveUpdate(None)
    var input: String = ""
    while input != "q" do {
      input = scala.io.StdIn.readLine()
      tui.processInputLine(input)
    }
}
