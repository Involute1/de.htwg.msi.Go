package de.htwg.msi

import akka.actor.{ActorSystem, Props}
import de.htwg.msi.controller.{GameController, GameControllerActor, GameSaverActor, InitState}
import de.htwg.msi.view.{ActorTui, ExternalDSLTui, Tui}

object Go {
  def main(args: Array[String]): Unit = {
    var input: String = ""
    if (args.nonEmpty) input = args(0)
    if (input == "DSL") {
      val tui: ExternalDSLTui = new ExternalDSLTui()
      tui.receiveUpdate(None)
      do {
        input = scala.io.StdIn.readLine()
        tui.processInputLine(input)
      } while (input != "q")
    } else if (input == "Actor") {
      val system = ActorSystem.create("MySystem");
      val gameSaver = system.actorOf(Props[GameSaverActor](), name = "gameSaver")
      val gameController = system.actorOf(Props(classOf[GameControllerActor], gameSaver), name = "gameController")
      val tui = system.actorOf(Props(classOf[ActorTui], gameController), name = "actorTui")
      tui ! ActorTui.Message
      do {
        input = scala.io.StdIn.readLine()
        tui ! ActorTui.Eval(input)
      } while (input != "q")
    } else {
      val tui: Tui = new Tui()
      tui.receiveUpdate(None)
      do {
        input = scala.io.StdIn.readLine()
        tui.processInputLine(input)
      } while (input != "q")
    }
  }
}

