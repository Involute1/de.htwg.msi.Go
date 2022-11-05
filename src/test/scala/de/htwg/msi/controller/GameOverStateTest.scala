package de.htwg.msi.controller

import de.htwg.msi.model.PlayerColor.{BLACK, WHITE}
import de.htwg.msi.model.{Field, GameData, Player}
import org.scalatest.matchers.should.Matchers.*
import org.scalatest.wordspec.AnyWordSpec


class GameOverStateTest extends AnyWordSpec {
  "GameOverState" should {
    val gameData = GameData(List(List(Field(0, 0, Some(WHITE)), Field(0, 1, Some(BLACK))), List(Field(1, 0, Some(WHITE)), Field(1, 1, Some(BLACK)))), 0, 0, List(Player("Player1", WHITE), Player("Player2", BLACK)))
    val gameOverState = GameOverState(gameData)
    "return error Msg for empty input" in {
      gameOverState.evaluate("") should be(Right("Input can´t be empty"))
    }
    "return InitState after new input" in {
      gameOverState.evaluate("new").left.get shouldBe a[InitState]
    }
    "return InitState after New input" in {
      gameOverState.evaluate("New").left.get shouldBe a[InitState]
    }
    "return error Msg for invalid input" in {
      gameOverState.evaluate("123") should be(Right("Please type New to start a new game"))
    }
    "changes ControllerState to GameOverState" in {
      gameOverState.nextState(gameData) should be(gameOverState)
    }
    "return a draw message in case of a draw as controller message" in {
      gameOverState.getControllerMessage() should be
      """
        |Score
        |Player Player1: 2
        |Player Player2: 2
        |
        |Draw
        |
        |Type New to start a new game""".stripMargin
    }
    "return a draw message in case of a winner as controller message" in {
      val gameDataWinner = GameData(List(List(Field(0, 0, Some(BLACK)), Field(0, 1, Some(BLACK))), List(Field(1, 0, Some(BLACK)), Field(1, 1, Some(BLACK)))), 0, 0, List(Player("Player1", WHITE), Player("Player2", BLACK)))
      val gameOverStateWinner = GameOverState(gameDataWinner)
      gameOverStateWinner.getControllerMessage() should be
      """
        |Score
        |Player Player1: 0
        |Player Player2: 4
        |
        |Player Player1 has won
        |
        |Type New to start a new game""".stripMargin
    }
  }
}
