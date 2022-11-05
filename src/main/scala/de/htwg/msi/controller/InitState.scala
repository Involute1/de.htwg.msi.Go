package de.htwg.msi.controller

import de.htwg.msi.model.GameData

case class InitState(controller: TGameController) extends TControllerState {
  val gameData: GameData = GameData(Nil, 0, 0, Nil)

  override def evaluate(input: String): Either[TControllerState, String] = {
    if (input.isEmpty) return Right("Input can`t be empty")
    val emptyBoard = gameData.initBoard(input)
    if (emptyBoard.isEmpty) return Right("Please enter a valid Input")
    val gameDataWithGameBoard = gameData.copy(board = emptyBoard)
    Left(nextState(gameDataWithGameBoard))
  }

  override def nextState(gameData: GameData): TControllerState = PlayerSetupState(controller, gameData)

  override def getControllerMessage(): String = {
    """
      |Welcome to Go!
      |Please select a Board size:
      |9x9 => type 9
      |10x10 => type 10
      |11x11 => type 11
      |12x12 => type 12
      |13x13 => type 13
      |14x14 => type 14
      |15x15 => type 15
      |16x16 => type 16
      |17x17 => type 17
      |18x18 => type 18
      |19x19 => type 19
      |Or press q to quit
      |""".stripMargin
  }
}
