package de.htwg.msi.controller

import de.htwg.msi.model.GameData
import org.scalatest.matchers.must.Matchers.convertToAnyMustWrapper
import org.scalatest.wordspec.AnyWordSpec

class ExternalDSLStateTest extends AnyWordSpec {
  "ExternalDSLState" should {
    val externalDSLState = new ExternalDSLState()
    "return itself on evaluate" in {
      externalDSLState.evaluate("Any") mustBe Left(externalDSLState)
    }
    "return itself on nextState" in {
      externalDSLState.nextState(GameData(Nil, 0, 0, Nil)) mustBe externalDSLState
    }
    "return a controller message" in {
      externalDSLState.getControllerMessage() mustBe
        """
          |Please provide a sgf file for parsing:
          |""".stripMargin
    }
  }
}
