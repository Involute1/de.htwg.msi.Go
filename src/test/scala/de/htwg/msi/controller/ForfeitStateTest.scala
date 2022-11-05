package de.htwg.msi.controller

import de.htwg.msi.model.PlayerColor.{BLACK, WHITE}
import de.htwg.msi.model.{Field, GameData, Player}
import org.scalatest.matchers.should.Matchers.*
import org.scalatest.wordspec.AnyWordSpec


class ForfeitStateTest extends AnyWordSpec {
  "ForfeitState" should {
    val gameData = GameData(Nil, 0, 0, List(Player("Player1", WHITE), Player("Player2", BLACK)))
    val forfeitState = ForfeitState(gameData)
    "return error Msg for empty input" in {
      forfeitState.evaluate("") should be(Right("Input can´t be empty"))
    }
    "return GameOverState after y input" in {
      forfeitState.evaluate("y").left.get shouldBe a[GameOverState]
    }
    "return GameOverState after yes input" in {
      forfeitState.evaluate("yes").left.get shouldBe a[GameOverState]
    }
    "return PlayingState after n input" in {
      forfeitState.evaluate("n").left.get shouldBe a[PlayingState]
    }
    "return PlayingState after no input" in {
      forfeitState.evaluate("no").left.get shouldBe a[PlayingState]
    }
    "return error Msg for invalid input" in {
      forfeitState.evaluate("123") should be(Right("Please type yes or no"))
    }
    "changes ControllerState to GameOverState" in {
      forfeitState.nextState(gameData) should be(GameOverState(gameData))
    }
    "return do you want to forfeit as controller message" in {
      forfeitState.getControllerMessage() should be
      """
        |Player Player1 wants to forfeit
        |You have to agree to the forfeit in order to end the game
        |Type yes or no
        |""".stripMargin
    }
  }
}
