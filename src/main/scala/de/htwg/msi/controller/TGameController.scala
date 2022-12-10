package de.htwg.msi.controller

trait TGameController {
  def eval(input: String): Either[TGameController, String]

  def getControllerState: TControllerState
}
