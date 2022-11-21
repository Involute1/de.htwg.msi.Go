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
    "try to read the content of the provided sgf file and return an error msg on failure" in {
      externalDSLController
        .eval("src/test/resources/sgf/error.sgf")
        .getOrElse("").replaceAll("[\n|\r]", "").replaceAll(" +", " ") shouldBe """[1.1] failed parsing: string matching regex '[\s\S]*?(?=SZ)' expected but 'Z' found Z[19]^)"""
    }
    "return ExternalDSLState as state" in {
      externalDSLController.getControllerState shouldBe a[ExternalDSLState]
    }
  }
}
