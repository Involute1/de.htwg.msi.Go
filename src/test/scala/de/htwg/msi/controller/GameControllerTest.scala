package de.htwg.msi.controller

import de.htwg.msi.model.PlayerColor.{BLACK, WHITE}
import de.htwg.msi.model.{Field, GameData, PlayerColor}
import org.scalatest.matchers.should.Matchers.*
import org.scalatest.wordspec.AnyWordSpec

class GameControllerTest extends AnyWordSpec {

  "GameController" should {
    val gameController = GameController()

    "return the current controllerState" in {
      gameController.getControllerState should be(InitState())
    }

    "notify observers with error msg on invalid message" in {
      gameController.eval("") shouldBe a[Unit]
    }

    "notify observers with None and update controller State on valid message" in {
      gameController.eval("9") shouldBe a[Unit]
    }
  }
}