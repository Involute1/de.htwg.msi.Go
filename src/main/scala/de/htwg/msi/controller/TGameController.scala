package de.htwg.msi.controller

import de.htwg.msi.util.Subject

trait TGameController extends Subject[Any] {
  def eval(input: String): Unit;
  def printGameBoard(): String;
  def printActions(): String;
}
