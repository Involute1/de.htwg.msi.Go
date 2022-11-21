package de.htwg.msi

import akka.actor.ActorSystem
import de.htwg.msi.controller.{GameController, InitState}
import de.htwg.msi.view.{ExternalDSLTui, Tui}

@main
def main(args: String*): Unit = {
  val actorSystem: ActorSystem = ActorSystem("GoActorSystem")
  //TODO 1 actor as a turn save service
  var input: String = ""
  if (args.nonEmpty) input = args(0)
  if (input == "DSL") {
    val tui: ExternalDSLTui = ExternalDSLTui()
    tui.receiveUpdate(None)
    while input != "q" do {
      input = scala.io.StdIn.readLine()
      tui.processInputLine(input)
    }
  } else {
    val tui: Tui = Tui()
    tui.receiveUpdate(None)
    while input != "q" do {
      input = scala.io.StdIn.readLine()
      tui.processInputLine(input)
    }
  }
}
