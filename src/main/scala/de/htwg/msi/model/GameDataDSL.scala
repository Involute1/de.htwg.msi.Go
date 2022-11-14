package de.htwg.msi.model

import de.htwg.msi.model.PlayerColor.{BLACK, WHITE}
import de.htwg.msi.model.GameData.*

class GameDataDSL(gameData: GameData) {
  //TODO: implicit function like implicit def string2GameData(input) = new GameDataDSL() in main-method

  def sz(input: Int): GameData = {
    gameData.copy(board = gameData.initBoard(input.toString))
  }

  def pw(name: String): GameData = {
    //copy(players = Player(name, PlayerColor.WHITE) :: players)
    ???
  }

  def pb(name: String): GameData = {
    //copy(players = Player(name, PlayerColor.BLACK) :: players)
    ???
  }

  def w(input: String): GameData = ???

  def b(input: String): GameData = {
    // Forfeit in sgf ist einfach ein leerer Zug
    ???
  }

}
