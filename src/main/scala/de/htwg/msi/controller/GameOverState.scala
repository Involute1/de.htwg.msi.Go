package de.htwg.msi.controller

import de.htwg.msi.controller.{InitState, TControllerState, TGameController}
import de.htwg.msi.model.{GameData, Player, PlayerColor}

case class GameOverState(controller: TGameController, gameData: GameData) extends TControllerState {
  override def evaluate(input: String): Either[TControllerState, String] = {
    if (input.isEmpty) return Right("Input can´t be empty")
    input match {
      case "New" | "new" => Left(InitState(controller))
      case _ => Right("Please type New to start a new game")
    }
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
        |Draw
        |
        |Type New to start a new game""".stripMargin.format(blackPlayer.name, blackScore, whitePlayer.name, whiteScore)
    val winningPlayer: Player = if (whiteScore > blackScore) whitePlayer else blackPlayer
    """
      |Score
      |Player %s: %d
      |Player %s: %d
      |
      |Player %s has won
      |
      |Type New to start a new game""".stripMargin.format(blackPlayer.name, blackScore, whitePlayer.name, whiteScore, winningPlayer.name)
  }
}