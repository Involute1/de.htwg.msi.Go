package de.htwg.msi.controller

import de.htwg.msi.model.{Move, SgfData, SgfGameData}
import de.htwg.msi.util.Constants.alphabetList

import scala.util.parsing.combinator.RegexParsers

class ExternalDSLParser extends RegexParsers with Serializable{

  def parseDSL(input: String): Either[SgfData, String] = {
    parseAll(sgfDataParser, input) match {
      case Success(t, _) => Left(t)
      case NoSuccess(msg, next) =>
        val pos = next.pos
        Right(s"[$pos] failed parsing: $msg \n \n ${pos.longString})")
    }
  }

  private def sgfDataParser: Parser[SgfData] = {
      sgfGameData ~
        uselessText(";") ~
      ";" ~ rep1sep(move, ';') ~ ")" ^^ {
      case gameData ~ _ ~ _ ~ moves ~ _ =>
        SgfData(gameData, moves)
    }
  }

  private def sgfGameData: Parser[SgfGameData] = {
    uselessText("SZ") ~
    "SZ[" ~ integer ~ "]" ~
      uselessText("PW") ~
      "PW[" ~ playerName ~ "]" ~
      uselessText("PB") ~
      "PB[" ~ playerName ~ "]" ^^ {
      case _ ~ _ ~ i ~ _ ~ _ ~ _ ~ pw ~ _ ~ _ ~ _ ~ pb ~ _ => SgfGameData(i, pw, pb)
    }
  }

  private def uselessText(before: String): Parser[String] = {
    ("""[\s\S]*?(?="""+before+")").r ^^ (identity)
  }

  private def integer: Parser[Int] = {
    """\d+""".r ^^ (_.toInt)
  }

  private def playerName: Parser[String] = {
    """^.*?(?=])""".r ^^ (identity)
  }

  private def move: Parser[Move] = {
    player ~ "[" ~ moveField ~ "]" ^^ {
      case p ~ _ ~ f ~ _ => Move(p, f)
    }
  }

  private def player: Parser[String] = {
    """[B|W]""".r ^^ (_.trim)
  }

  private def moveField: Parser[String] = {
    """[a-s]{2}""".r ^^ (_.trim)
  }
}
