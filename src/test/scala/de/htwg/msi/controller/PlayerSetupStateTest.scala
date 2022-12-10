package de.htwg.msi.controller

import de.htwg.msi.model.GameData
import org.scalatest.matchers.should.Matchers.not.be
import org.scalatest.matchers.should.Matchers.{a, convertToAnyShouldWrapper}
import org.scalatest.wordspec.AnyWordSpec


class PlayerSetupStateTest extends AnyWordSpec {
//  "PlayerSetupState" should {
//    val gameData = GameData(Nil, 0, 0, Nil)
//    val playerSetupState = PlayerSetupState(gameData)
//    "return error Msg for empty string" in {
//      playerSetupState.evaluate("") should be(Right("Input can`t be empty"))
//    }
//    "return PlayerSetupState after first valid player name" in {
//      playerSetupState.evaluate("player black").left.get shouldBe a[PlayerSetupState]
//    }
//    "return PlayingState after second valid player name" in {
//      val firstPlayerSetupState = playerSetupState.evaluate("player black")
//      firstPlayerSetupState.left.get.evaluate("player white").left.get shouldBe a[PlayingState]
//    }
//    "changes ControllerState to PlayingState" in {
//      playerSetupState.nextState(gameData) should be(PlayingState(gameData))
//    }
//    "return name input request from first player as controller message" in {
//      playerSetupState.getControllerMessage() should be(
//        """
//          |Player 1 enter your Name:
//          |""".stripMargin
//      )
//    }
//    "return name input request from second player as controller message" in {
//      val firstPlayerSetupState = playerSetupState.evaluate("player black")
//      firstPlayerSetupState.left.get.getControllerMessage() should be(
//        """
//          |Player 2 enter your Name:
//          |""".stripMargin
//      )
//    }
//  }
}
