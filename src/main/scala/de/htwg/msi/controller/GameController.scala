package de.htwg.msi.controller

import de.htwg.msi.model.{Field, GameData, Player, PlayerColor}
import de.htwg.msi.util.Constants.alphabetList

class GameController() extends TGameController {
  val gameData: GameData = GameData(Nil, 0, 0, Nil)
  //TODO find way to val this
  var controllerState: TControllerState = InitState(this, gameData)

  override def eval(input: String): Unit = {
    val errorMsg: Option[String] = controllerState.evaluate(input)
    notifyObservers(errorMsg)
  }

  override def printGameBoard(board: List[List[Field]]): String = {
    //TODO make it prettier?
    val header: String = ("0"::alphabetList).slice(0, board.length + 1).mkString(" ")
    val empty: String = (" " :: List.fill(board.length)("-")).mkString(" ")
    val boardAsString: List[String] = board.zipWithIndex.map((row, idx) => alphabetList(idx) + "|" + row.map(field => field.toPrettyString).mkString(" "))
    header + " \r\n" + empty + " \r\n" + boardAsString.mkString("\r\n")
  }

  override def printActions(playerColor: PlayerColor, currentGameData: GameData): String = {
    "\r\n" + currentGameData.availableMovesAsString(playerColor) +
    """
      |Or type forfeit to forfeit
      |""".stripMargin
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
    input match {
      case "forfeit" =>
        controller.updateControllerState(nextState(gameData))
        None
      case _ =>
        if (!gameData.isMoveInputValid(input)) return Some("Input invalid try again")
        val gameDataWithPlacedStone: GameData = gameData.copy(board = gameData.placeStone(input), turn = gameData.turn + 1)
        controller.updateControllerState(this.copy(gameData = gameDataWithPlacedStone))
        None
    }
  }

  override def nextState(gameData: GameData): TControllerState = ForfeitState(controller, gameData)
  override def getControllerMessage(): String = {
    val currentPlayer: Player = gameData.getCurrentPlayer
    controller.printGameBoard(gameData.board) +
      """
        |Player %s enter one of the following:
        |""".stripMargin.format(currentPlayer.name)
      + controller.printActions(currentPlayer.color, gameData)
  }


}

case class ForfeitState(controller: TGameController, gameData: GameData) extends TControllerState {
  override def evaluate(input: String): Option[String] = {
    if (input.isEmpty) return Some("Input can´t be empty")
    input match {
      case "y" | "yes" => {
        controller.updateControllerState(nextState(gameData))
        None
      }
      case "n" | "no" => {
        controller.updateControllerState(PlayingState(controller, gameData))
        None
      }
      case _ => Some("Please type yes or no")
    }
  }

  override def nextState(gameData: GameData): TControllerState = GameOverState(controller, gameData)

  override def getControllerMessage(): String = {
    """
      |Player %s wants to forfeit
      |You have to agree to the forfeit in order to end the game
      |Type yes or no
      |""".stripMargin.format(gameData.getCurrentPlayer.name)
  }
}

case class GameOverState(controller: TGameController, gameData: GameData) extends TControllerState {
  override def evaluate(input: String): Option[String] = {
    None
  }

  override def nextState(gameData: GameData): TControllerState = this
  override def getControllerMessage(): String = {
    val whiteScore = gameData.getScoreOf(PlayerColor.WHITE)
    val blackScore = gameData.getScoreOf(PlayerColor.BLACK)
    val blackPlayer = gameData.players.filter(p => p.color == PlayerColor.BLACK).head
    val whitePlayer = gameData.players.filter(p => p.color == PlayerColor.WHITE).head
    if (whiteScore == blackScore) return
      """
        |Score
        |Player %s: %d
        |Player %s: %d
        |
        |Draw""".stripMargin
    val winningPlayer: Player = if (whiteScore > blackScore) whitePlayer else blackPlayer
    """
      |Score
      |Player %s: %d
      |Player %s: %d
      |
      |Player %s has won
      |""".stripMargin.format(blackPlayer.name, blackScore, whitePlayer.name, whiteScore, winningPlayer.name)
  }
}
