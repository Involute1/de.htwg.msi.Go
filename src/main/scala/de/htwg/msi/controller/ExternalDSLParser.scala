package de.htwg.msi.controller

import de.htwg.msi.model.*
import de.htwg.msi.util.Constants.alphabetList

import scala.util.parsing.combinator.RegexParsers

class ExternalDSLParser extends RegexParsers {

  def parseDSL(input: String): Either[SgfData, String] = {
    parseAll(sgfDataParser, input) match {
      case Success(t, _) => Left(t)
      case NoSuccess(msg, next) =>
        val pos = next.pos
        Right(s"[$pos] failed parsing: $msg \n \n ${pos.longString})")
    }
  }

  private def sgfDataParser: Parser[SgfData] = {
    rep(uselessText) ~
      sgfGameData ~
      rep(uselessText) ~
      ";" ~ rep1sep(move, ';') ~ ")" ^^ {
      case _ ~ gameData ~ _ ~ _ ~ moves ~ _ =>
        SgfData(gameData, moves)
    }
  }

  private def sgfGameData: Parser[SgfGameData] = {
    "SZ[" ~ integer ~ "]" ~
      rep(uselessText) ~
      "PW[" ~ playerName ~ "]" ~
      "PB[" ~ playerName ~ "]" ^^ {
      case _ ~ i ~ _ ~ _ ~ _ ~ pw ~ _ ~ _ ~ pb ~ _ => SgfGameData(i, pw, pb)
    }
  }

  private def uselessText: Parser[String] = {
    """.+\s?""".r ^^ (identity)
  }

  private def integer: Parser[Int] = {
    """\d+""".r ^^ (_.toInt)
  }

  private def playerName: Parser[String] = {
    """^.*?(?=])""".r ^^ {
      case name => name.substring(0, name.length() - 1)
    }
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