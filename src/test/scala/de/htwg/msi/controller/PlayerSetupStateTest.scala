package de.htwg.msi.controller

import de.htwg.msi.model.GameData
import org.scalatest.matchers.should.Matchers.*
import org.scalatest.wordspec.AnyWordSpec


class PlayerSetupStateTest extends AnyWordSpec {
  "PlayerSetupState" should {
    val gameController = GameController()
    val gameData = GameData(Nil, 0, 0, Nil)
    val playerSetupState = PlayerSetupState(gameController, gameData)
    "return error Msg for empty string" in {
      playerSetupState.evaluate("") should be(Some("Input can`t be empty"))
    }
    "return None for valid player name" in {
      playerSetupState.evaluate("player black") should be(None)
    }
    "changes ControllerState to PlayingState" in {
      playerSetupState.nextState(gameData) should be(PlayingState(gameController, gameData))
    }
    "return name input request from first player as controller message" in {
      playerSetupState.getControllerMessage() should be(
        """
          |Player 1 enter your Name:
          |""".stripMargin
      )
    }
  }
}
