package de.htwg.msi.controller

import de.htwg.msi.model.PlayerColor.{BLACK, WHITE}
import de.htwg.msi.model.{Field, GameData, Player}
import org.scalatest.matchers.should.Matchers.*
import org.scalatest.wordspec.AnyWordSpec


class PlayingStateTest extends AnyWordSpec {
  "PlayingState" should {
    val gameController = GameController()
    val gameData = GameData(List(List(Field(0, 0), Field(0, 1)), List(Field(1, 0), Field(1, 1))), 0, 0, List(Player("Player1", WHITE), Player("Player2", BLACK)))
    val playingState = PlayingState(gameController, gameData)
    "return ForfeitState after forfeit input" in {
      playingState.evaluate("forfeit").left.get shouldBe a[ForfeitState]
    }
    "return error Msg for invalid move" in {
      playingState.evaluate("123") should be(Right("Input invalid try again"))
    }
    "return PlayingState after valid move" in {
      playingState.evaluate("AA").left.get shouldBe a[PlayingState]
    }
    "changes ControllerState to ForfeitState" in {
      playingState.nextState(gameData) should be(ForfeitState(gameController, gameData))
    }
    "return available Moves for the current player as controller message" in {
      playingState.getControllerMessage() should be
      """
        |Player Player1 enter one of the following:
        |
        |AA,AB,BA,BB
        |Or type forfeit to forfeit
        |"""
    }
  }
}
