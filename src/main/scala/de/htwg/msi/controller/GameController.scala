package de.htwg.msi.controller

import de.htwg.msi.model.{Field, GameData}

class GameController() extends TGameController {
  val alphabetList: List[String] = List("A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z")
  val gameData: GameData = GameData(Nil, 0, 0, Nil)
  //TODO find way to val this
  var controllerState: TControllerState = InitState(this, gameData)

  override def eval(input: String): Unit = {
    val errorMsg: Option[String] = controllerState.evaluate(input)
    notifyObservers(errorMsg)
  }

  override def printGameBoard(board: List[List[Field]]): String = {
    //TODO make it prettier
    val header: String = ("0"::alphabetList).slice(0, board.length + 1).mkString(" ")
    val empty: String = (" " :: List.fill(board.length)("-")).mkString(" ")
    val boardSeq: IndexedSeq[String] = for (row <- board.indices) yield {
      val legend: String = alphabetList(row) + "|"
      val rowString: String = board(row).map(e => e.toPrettyString()).mkString(" ")
      legend + rowString
    }
    header + " \r\n" + empty + " \r\n" + boardSeq.mkString("\r\n")
  }

  override def printActions(): String = {
    //TODO
    """"""
  }

  override def updateControllerState(nextState: TControllerState): TControllerState = {
    controllerState = nextState
    nextState
  }

  override def getControllerState: TControllerState = {
    controllerState
  }
}

trait TControllerState {
  def evaluate(input: String): Option[String]
  def nextState(gameData: GameData): TControllerState
  def getControllerMessage(): String
}

case class InitState(controller: TGameController, gameData: GameData) extends TControllerState {
  override def evaluate(input: String): Option[String] = {
    if (input.isEmpty) return Some("Input can´t be empty")
    val emptyBoard = gameData.initBoard(input)
    if (emptyBoard.isEmpty) return Some("Please enter a valid Input")
    val gameDataWithGameBoard = gameData.copy(board = emptyBoard)
    controller.updateControllerState(nextState(gameDataWithGameBoard))
    None
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
case class PlayerSetupState(controller: TGameController, gameData: GameData) extends TControllerState {
  override def evaluate(input: String): Option[String] = {
    if (input.isEmpty) return Some("Input can´t be empty")
    val gameDataWithPlayer = gameData.copy(players = gameData.initPlayer(input))
    if (gameDataWithPlayer.players.length < 2) {
      controller.updateControllerState(this.copy(gameData = gameDataWithPlayer))
    } else {
      controller.updateControllerState(nextState(gameData = gameDataWithPlayer))
    }
    None
  }

  override def nextState(gameData: GameData): TControllerState = PlayingState(controller, gameData)
  override def getControllerMessage(): String = {
    """
      |Player %d enter your Name:
      |""".stripMargin.format(gameData.players.length + 1)
  }
}

case class PlayingState(controller: TGameController, gameData: GameData) extends TControllerState {
  override def evaluate(input: String): Option[String] = {
    //TODO place stone
    // forfeit
    None
  }

  override def nextState(gameData: GameData): TControllerState = GameOverState(controller)
  override def getControllerMessage(): String = {
    controller.printGameBoard(gameData.board)
  }
}

case class GameOverState(controller: TGameController) extends TControllerState {
  override def evaluate(input: String): Option[String] = {
    //TODO count score
    None
  }

  override def nextState(gameData: GameData): TControllerState = this
  override def getControllerMessage(): String = ???
}
