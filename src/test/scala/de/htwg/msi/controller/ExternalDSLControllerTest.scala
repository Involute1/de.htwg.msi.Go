package de.htwg.msi.controller

import de.htwg.msi.model.{Move, SgfData, SgfGameData}
import org.scalatest.matchers.should.Matchers.*
import org.scalatest.wordspec.AnyWordSpec

class ExternalDSLControllerTest extends AnyWordSpec {
  "ExternalDSLController" should {
    val externalDSLController = ExternalDSLController(ExternalDSLParser(), SgfData(SgfGameData(0, "", ""), Nil));
    "read the content of the provided sgf file and parse it" in {
      externalDSLController.eval("src/test/resources/sgf/2019-04-03-47.sgf") shouldBe Left(externalDSLController.copy(sgfData = SgfData(SgfGameData(19, "MIss", "petgo3"), List(Move("B", "pp"), Move("W", "dp"), Move("B", "dd")))))
    }
  }
}
