package de.htwg.msi

import akka.actor.{ActorSystem, Props}
import de.htwg.msi.controller.{GameController, GameControllerActor, GameSaverActor, InitState}
import de.htwg.msi.view.{ActorTui, ExternalDSLTui, Tui}

@main
def main(args: String*): Unit = {
  var input: String = ""
  if (args.nonEmpty) input = args(0)
  if (input == "DSL") {
    val tui: ExternalDSLTui = ExternalDSLTui()
    tui.receiveUpdate(None)
    while input != "q" do {
      input = scala.io.StdIn.readLine()
      tui.processInputLine(input)
    }
  } else if(input == "Actor") {
    val system = ActorSystem.create("MySystem");
    val gameSaver = system.actorOf(Props[GameSaverActor](), name = "gameSaver")
    val gameController = system.actorOf(Props(classOf[GameControllerActor], gameSaver), name = "gameController")
    val tui = system.actorOf(Props(classOf[ActorTui], gameController), name = "actorTui")
    tui ! ActorTui.Message
    while input != "q" do {
      input = scala.io.StdIn.readLine()
      tui ! ActorTui.Eval(input)
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
