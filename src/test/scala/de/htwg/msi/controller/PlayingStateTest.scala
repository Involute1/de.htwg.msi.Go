package de.htwg.msi.controller

import de.htwg.msi.model.PlayerColor.{BLACK, WHITE}
import de.htwg.msi.model.{Field, GameData, Player}
import org.scalatest.matchers.should.Matchers.*
import org.scalatest.wordspec.AnyWordSpec


class PlayingStateTest extends AnyWordSpec {
  "PlayingState" should {
    val gameData = GameData(List(List(Field(0, 0), Field(0, 1)), List(Field(1, 0), Field(1, 1))), 0, 0, List(Player("Player1", WHITE), Player("Player2", BLACK)))
    val playingState = PlayingState(gameData)
    "return ForfeitState after forfeit input" in {
      playingState.evaluate("forfeit").left.get shouldBe a[ForfeitState]
    }
    "return error Msg for invalid move" in {
      playingState.evaluate("123") should be(Right("Input invalid try again"))
    }
    "return PlayingState after valid move" in {
      playingState.evaluate("AA").left.get shouldBe a[PlayingState]
    }
    "changes ControllerState to ForfeitState" in {
      playingState.nextState(gameData) should be(ForfeitState(gameData))
    }
    "return available Moves for the current player as controller message" in {
      playingState.getControllerMessage() should be
      """
        |Player Player1 enter one of the following:
        |
        |AA,AB,BA,BB
        |Or type forfeit to forfeit
        |"""
    }
    "print correct empty board" in {
      playingState.printGameBoard(List(
        List(Field(0, 0), Field(1, 0), Field(2, 0), Field(3, 0)),
        List(Field(0, 1), Field(1, 1), Field(2, 1), Field(3, 1)),
        List(Field(0, 2), Field(1, 2), Field(2, 2), Field(3, 2)),
        List(Field(0, 3), Field(1, 3), Field(2, 3), Field(3, 3)))).replaceAll("\r\n", "") should be(
        """|0   A  B  C  D 
           |    -  -  -  - 
           |A | O  O  O  O
           |B | O  O  O  O
           |C | O  O  O  O
           |D | O  O  O  O""".stripMargin.replaceAll("\n", "").replaceAll("\r", ""))
    }
    "print correct full board" in {
      playingState.printGameBoard(List(
        List(Field(0, 0, Some(WHITE)), Field(1, 0, Some(BLACK)), Field(2, 0, Some(WHITE)), Field(3, 0, Some(WHITE))),
        List(Field(0, 1, Some(BLACK)), Field(1, 1, Some(WHITE)), Field(2, 1, Some(WHITE)), Field(3, 1, Some(BLACK))),
        List(Field(0, 2, Some(WHITE)), Field(1, 2, Some(WHITE)), Field(2, 2, Some(BLACK)), Field(3, 2, Some(WHITE))),
        List(Field(0, 3, Some(WHITE)), Field(1, 3, Some(BLACK)), Field(2, 3, Some(WHITE)), Field(3, 3, Some(WHITE))))).replaceAll("\r\n", "") should be(
        """|0   A  B  C  D 
           |    -  -  -  - 
           |A | w  b  w  w
           |B | b  w  w  b
           |C | w  w  b  w
           |D | w  b  w  w""".stripMargin.replaceAll("\n", "").replaceAll("\r", ""))
    }
  }
}
