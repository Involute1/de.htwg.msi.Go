package de.htwg.msi.controller

import de.htwg.msi.model.GameData
import org.scalatest.matchers.must.Matchers.{be, convertToAnyMustWrapper}
import org.scalatest.matchers.should.Matchers.a
import org.scalatest.wordspec.AnyWordSpec

class InitStateTest extends AnyWordSpec {
  "InitState" should {
    val gameData = GameData(Nil, 0, 0, Nil)
    val initState = InitState()
    "return error Msg for empty string" in {
      initState.evaluate("") must be(Right("Input can`t be empty"))
    }
    "return error Msg for invalid input string" in {
      initState.evaluate("asd") must be(Right("Please enter a valid Input"))
    }
    "return no errorMsg for valid input string" in {
      initState.evaluate("9").left.get mustBe a[PlayerSetupState]
    }
    "return playerSetupState as next State" in {
      initState.nextState(gameData) must be(PlayerSetupState(gameData))
    }
    "return all possible board options as controller Message" in {
      initState.getControllerMessage() must be(
        """
          |Welcome to Go!
          |Please select a Board size:
          |9x9 => type 9
          |10x10 => type 10
          |11x11 => type 11
          |12x12 => type 12
          |13x13 => type 13
          |14x14 => type 14
          |15x15 => type 15
          |16x16 => type 16
          |17x17 => type 17
          |18x18 => type 18
          |19x19 => type 19
          |Or press q to quit
          |""".stripMargin)
    }
  }
}
