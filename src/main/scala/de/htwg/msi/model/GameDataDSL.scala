package de.htwg.msi.model

import de.htwg.msi.model.PlayerColor.{BLACK, WHITE}
import de.htwg.msi.model.GameData.*

class GameDataDSL() {
  
  var gameData: GameData = GameData(Nil, 0, 0, Nil)
  def sz(input: Int): GameData = {
    gameData = gameData.copy(board = gameData.initBoard(input.toString))
    gameData
  }

  def boardSizeIs(input: Int): GameData = {
    sz(input)
  }

  def pw(name: String): GameData = {
    gameData = gameData.copy(players = Player(name, PlayerColor.WHITE) :: gameData.players)
    gameData
  }

  def playerWhiteIs(name: String): GameData = {
    pw(name)
  }

  def pb(name: String): GameData = {
    gameData = gameData.copy(players = Player(name, PlayerColor.BLACK) :: gameData.players)
    gameData
  }

  def playerBlackIs(name: String): GameData = {
    pb(name)
  }

  def whitePlaceStoneAt(input: String): GameData = {
    w(input)
  }

  def w(input: String): GameData = {
    // Forfeit in sgf ist einfach ein leerer Zug
    if (input.isEmpty) {
      gameData = gameData.copy(turn = gameData.turn + 1)
      return gameData
    }
    if (!gameData.isMoveInputValid(input)) return gameData
    gameData = gameData.copy(board = gameData.placeStone(input, WHITE), turn = gameData.turn + 1)
    gameData
  }

  def blackPlaceStoneAt(input: String): GameData = {
    b(input)
  }

  def b(input: String): GameData = {
    // Forfeit in sgf ist einfach ein leerer Zug
    if (input.isEmpty) {
      gameData = gameData.copy(turn = gameData.turn + 1)
      return gameData
    }
    if (!gameData.isMoveInputValid(input)) return gameData
    gameData = gameData.copy(board = gameData.placeStone(input, BLACK), turn = gameData.turn + 1)
    gameData
  }

}
