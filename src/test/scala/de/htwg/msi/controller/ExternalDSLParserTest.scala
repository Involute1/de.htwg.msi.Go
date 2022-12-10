package de.htwg.msi.controller

import de.htwg.msi.model.{Move, SgfData, SgfGameData}
import org.scalatest.matchers.should.Matchers.{convertToAnyShouldWrapper}
import org.scalatest.wordspec.AnyWordSpec

class ExternalDSLParserTest extends AnyWordSpec {
//  "A ExternalDSLParser" when {
//    "ExternalDSLParser" should {
//      val externalDSLParser = new ExternalDSLParser();
//      "return the parsed sgf file as Success on correct sgf file" in {
//        externalDSLParser.parseDSL(
//          """(;GM[1]
//            |FF[4]
//            |SZ[19]
//            |PW[MIss]
//            |PB[petgo3]
//            |BR[7d]
//            |DT[2019-04-03]
//            |PC[The KGS Go Server at http://www.gokgs.com/]
//            |KM[7.50]
//            |RE[B+Resign]
//            |RU[Chinese]
//            |CA[UTF-8]
//            |ST[2]
//            |AP[CGoban:3]
//            |TM[300]
//            |OT[10x20 byo-yomi]
//            |;B[dp];W[pp];B[pd])"""
//            .stripMargin) shouldBe Left(SgfData(SgfGameData(19, "MIss", "petgo3"), List(Move("B", "dp"), Move("W", "pp"), Move("B", "pd"))))
//      }
//      "return the an error message on incorrect sgf file" in {
//        externalDSLParser.parseDSL(
//          """(;GM[1]
//            |FF[4])"""
//            .stripMargin).getOrElse("").replaceAll("[\n|\r]", "").replaceAll(" +", " ") shouldBe """[1.1] failed parsing: string matching regex '[\s\S]*?(?=SZ)' expected but '(' found (;GM[1]^)"""
//      }
//    }
//  }

}
