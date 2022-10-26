package de.htwg.msi.controller

class GameController extends TGameController {
  override def eval(input: String): Unit = {
    println(input)
    notifyObservers()
  }

  override def printGameBoard(): String = ???

  override def printActions(): String = ???
}
