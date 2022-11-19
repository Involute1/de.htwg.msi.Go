package de.htwg.msi.model

import de.htwg.msi.model.PlayerColor.{BLACK, WHITE}
import de.htwg.msi.model.GameData.*

class GameDataDSL(gameData: GameData) {
  def sz(input: Int): GameData = {
    gameData.copy(board = gameData.initBoard(input.toString))
  }

  def boardSizeIs(input: Int): GameData = {
    sz(input)
  }

  def pw(name: String): GameData = {
    gameData.copy(players = Player(name, PlayerColor.WHITE) :: gameData.players)
  }

  def playerWhiteIs(name: String): GameData = {
    pw(name)
  }

  def pb(name: String): GameData = {
    gameData.copy(players = Player(name, PlayerColor.BLACK) :: gameData.players)
  }

  def playerBlackIs(name: String): GameData = {
    pb(name)
  }

  def whitePlaceStoneAt(input: String): GameData = {
    w(input)
  }

  def w(input: String): GameData = {
    // Forfeit in sgf ist einfach ein leerer Zug
    if (input.isEmpty) return gameData.copy(turn = gameData.turn + 1)
    if (!gameData.isMoveInputValid(input)) return gameData
    gameData.copy(board = gameData.placeStone(input, WHITE), turn = gameData.turn + 1)
  }

  def blackPlaceStoneAt(input: String): GameData = {
    b(input)
  }

  def b(input: String): GameData = {
    // Forfeit in sgf ist einfach ein leerer Zug
    if (input.isEmpty) return gameData.copy(turn = gameData.turn + 1)
    if (!gameData.isMoveInputValid(input)) return gameData
    gameData.copy(board = gameData.placeStone(input, BLACK), turn = gameData.turn + 1)
  }

}
