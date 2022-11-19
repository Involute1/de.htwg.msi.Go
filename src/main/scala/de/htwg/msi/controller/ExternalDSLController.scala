package de.htwg.msi.controller

import de.htwg.msi.model.*
import de.htwg.msi.util.Constants.alphabetList

import scala.io.Source
import scala.util.parsing.combinator.RegexParsers

case class ExternalDSLController(externalDSLParser: ExternalDSLParser, sgfData: SgfData) extends TGameController {

  override def eval(input: String): Either[TGameController, String] = {

    val inputFile = Source.fromFile(input).mkString
    externalDSLParser.parseDSL(inputFile).fold(
      sgfData => Left(this.copy(sgfData = sgfData)),
      e => Right(e)
    )
  }

  override def getControllerState: TControllerState = {
    ExternalDSLState()
  }
}