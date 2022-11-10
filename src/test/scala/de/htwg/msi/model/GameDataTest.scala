package de.htwg.msi.model

import de.htwg.msi.model.PlayerColor
import de.htwg.msi.model.PlayerColor.{BLACK, WHITE}
import org.scalatest.matchers.should.Matchers.{shouldBe, *}
import org.scalatest.wordspec.AnyWordSpec

import scala.collection.immutable

class GameDataTest extends AnyWordSpec {
  "GameData" when {
    "it has an empty board" should {
      val gameData = GameData(List(
        List(Field(0, 0), Field(1, 0), Field(2, 0), Field(3, 0)),
        List(Field(0, 1), Field(1, 1), Field(2, 1), Field(3, 1)),
        List(Field(0, 2), Field(1, 2), Field(2, 2), Field(3, 2)),
        List(Field(0, 3), Field(1, 3), Field(2, 3), Field(3, 3))),
        0, 0, Nil)
      "return all fields as available moves for black" in {
        gameData.availableMoves(PlayerColor.BLACK) should contain theSameElementsAs
          List(Field(0, 0), Field(1, 0), Field(2, 0), Field(3, 0),
            Field(0, 1), Field(1, 1), Field(2, 1), Field(3, 1),
            Field(0, 2), Field(1, 2), Field(2, 2), Field(3, 2),
            Field(0, 3), Field(1, 3), Field(2, 3), Field(3, 3))
      }
      "return all fields as available moves for white" in {
        gameData.availableMoves(PlayerColor.WHITE) should contain theSameElementsAs
          List(Field(0, 0), Field(1, 0), Field(2, 0), Field(3, 0),
            Field(0, 1), Field(1, 1), Field(2, 1), Field(3, 1),
            Field(0, 2), Field(1, 2), Field(2, 2), Field(3, 2),
            Field(0, 3), Field(1, 3), Field(2, 3), Field(3, 3))
      }

      "it results in a score of zero for both players" in {
        gameData.getScoreOf(PlayerColor.BLACK) shouldBe 0
        gameData.getScoreOf(PlayerColor.WHITE) shouldBe 0
      }

    }
    "a suicide is possible for black" should {
      val gameData = GameData(List(
        List(Field(0, 0, Some(WHITE)), Field(1, 0, Some(WHITE)), Field(2, 0, Some(WHITE)), Field(3, 0, Some(WHITE)), Field(4, 0, Some(WHITE))),
        List(Field(0, 1, Some(WHITE)), Field(1, 1, Some(BLACK)), Field(2, 1, Some(BLACK)), Field(3, 1, Some(BLACK)), Field(4, 1, Some(WHITE))),
        List(Field(0, 2, Some(WHITE)), Field(1, 2, Some(BLACK)), Field(2, 2), Field(3, 2, Some(BLACK)), Field(4, 2, Some(WHITE))),
        List(Field(0, 3, Some(WHITE)), Field(1, 3, Some(BLACK)), Field(2, 3, Some(BLACK)), Field(3, 3, Some(BLACK)), Field(4, 3, Some(WHITE))),
        List(Field(0, 4, Some(WHITE)), Field(1, 4, Some(WHITE)), Field(2, 4, Some(WHITE)), Field(3, 4, Some(WHITE)), Field(4, 4, Some(WHITE)))),
        0, 0, Nil)
      "not allow that move for black" in {
        gameData.availableMoves(PlayerColor.BLACK) shouldBe empty
      }
      "allow that move for white" in {
        gameData.availableMoves(PlayerColor.WHITE) should contain theSameElementsAs
          List(Field(2, 2))
      }
    }
    "is has multiple chains" should {
      val gameData = GameData(Nil, 0, 0, Nil)
      "return black chain on findChain" in {
        gameData.findChain(Field(2, 2), BLACK, List(
          List(Field(0, 0), Field(1, 0, Some(WHITE)), Field(2, 0, Some(WHITE)), Field(3, 0, Some(WHITE)), Field(4, 0)),
          List(Field(0, 1, Some(WHITE)), Field(1, 1, Some(BLACK)), Field(2, 1, Some(BLACK)), Field(3, 1, Some(BLACK)), Field(4, 1, Some(WHITE))),
          List(Field(0, 2, Some(WHITE)), Field(1, 2, Some(BLACK)), Field(2, 2), Field(3, 2, Some(BLACK)), Field(4, 2, Some(WHITE))),
          List(Field(0, 3, Some(WHITE)), Field(1, 3, Some(BLACK)), Field(2, 3, Some(BLACK)), Field(3, 3, Some(BLACK)), Field(4, 3, Some(WHITE))),
          List(Field(0, 4), Field(1, 4, Some(WHITE)), Field(2, 4, Some(WHITE)), Field(3, 4, Some(WHITE)), Field(4, 4)))) should be(Chain(Set(Field(1, 1, Some(BLACK)), Field(2, 1, Some(BLACK)), Field(3, 1, Some(BLACK)),
          Field(1, 2, Some(BLACK)), Field(2, 2), Field(3, 2, Some(BLACK)),
          Field(1, 3, Some(BLACK)), Field(2, 3, Some(BLACK)), Field(3, 3, Some(BLACK))), 0))
      }
      "return one white chain on findChain" in {
        gameData.findChain(Field(0, 1, Some(WHITE)), WHITE, List(
          List(Field(0, 0), Field(1, 0, Some(WHITE)), Field(2, 0, Some(WHITE)), Field(3, 0, Some(WHITE)), Field(4, 0)),
          List(Field(0, 1, Some(WHITE)), Field(1, 1, Some(BLACK)), Field(2, 1, Some(BLACK)), Field(3, 1, Some(BLACK)), Field(4, 1, Some(WHITE))),
          List(Field(0, 2, Some(WHITE)), Field(1, 2, Some(BLACK)), Field(2, 2), Field(3, 2, Some(BLACK)), Field(4, 2, Some(WHITE))),
          List(Field(0, 3, Some(WHITE)), Field(1, 3, Some(BLACK)), Field(2, 3, Some(BLACK)), Field(3, 3, Some(BLACK)), Field(4, 3, Some(WHITE))),
          List(Field(0, 4), Field(1, 4, Some(WHITE)), Field(2, 4, Some(WHITE)), Field(3, 4, Some(WHITE)), Field(4, 4)))) should be(Chain(Set(Field(0, 1, Some(WHITE)), Field(0, 2, Some(WHITE)), Field(0, 3, Some(WHITE))), 2))
      }
    }
    "it has a black eye" should {
      val gameData = GameData(List(
        List(Field(0, 0), Field(1, 0, Some(WHITE)), Field(2, 0, Some(WHITE)), Field(3, 0, Some(WHITE)), Field(4, 0, Some(WHITE)), Field(5, 0)),
        List(Field(0, 1), Field(1, 1, Some(WHITE)), Field(2, 1, Some(BLACK)), Field(3, 1, Some(BLACK)), Field(4, 1, Some(BLACK)), Field(5, 1)),
        List(Field(0, 2), Field(1, 2, Some(WHITE)), Field(2, 2, Some(BLACK)), Field(3, 2), Field(4, 2, Some(BLACK)), Field(5, 2)),
        List(Field(0, 3, Some(BLACK)), Field(1, 3, Some(BLACK)), Field(2, 3, Some(BLACK)), Field(3, 3, Some(BLACK)), Field(4, 3), Field(5, 3)),
        List(Field(0, 4), Field(1, 4), Field(2, 4), Field(3, 4), Field(4, 4), Field(5, 4))),
        0, 0, Nil)
      "allow that move for black" in {
        gameData.availableMoves(BLACK) should contain(Field(3, 2))
      }
      "not allow that move for white" in {
        gameData.availableMoves(WHITE) should not contain Field(3, 2)
      }
    }
    "it has a fake eye" should {
      val gameData = GameData(List(
        List(Field(0, 0), Field(1, 0, Some(BLACK)), Field(2, 0), Field(3, 0, Some(BLACK)), Field(4, 0, Some(WHITE))),
        List(Field(0, 1, Some(BLACK)), Field(1, 1, Some(BLACK)), Field(2, 1, Some(BLACK)), Field(3, 1, Some(WHITE)), Field(4, 1, Some(WHITE))),
        List(Field(0, 2, Some(WHITE)), Field(1, 2, Some(WHITE)), Field(2, 2, Some(WHITE)), Field(3, 2), Field(4, 2)),
        List(Field(0, 3), Field(1, 3), Field(2, 3), Field(3, 3, Some(WHITE)), Field(4, 3))),
        0, 0, Nil)
      "allow that move for black" in {
        gameData.availableMoves(BLACK) should contain(Field(3, 2))
      }
      "allow that move eye for white" in {
        gameData.availableMoves(WHITE) should contain(Field(3, 2))
      }
    }

    "there is a continued game" should {
      val gameData = GameData(List(
        List(Field(0, 0), Field(1, 0, Some(WHITE)), Field(2, 0, Some(WHITE)), Field(3, 0, Some(WHITE)), Field(4, 0)),
        List(Field(0, 1, Some(WHITE)), Field(1, 1, Some(BLACK)), Field(2, 1, Some(BLACK)), Field(3, 1, Some(BLACK)), Field(4, 1, Some(WHITE))),
        List(Field(0, 2, Some(WHITE)), Field(1, 2, Some(BLACK)), Field(2, 2), Field(3, 2, Some(BLACK)), Field(4, 2, Some(WHITE))),
        List(Field(0, 3, Some(WHITE)), Field(1, 3, Some(BLACK)), Field(2, 3, Some(BLACK)), Field(3, 3, Some(BLACK)), Field(4, 3, Some(WHITE))),
        List(Field(0, 4), Field(1, 4, Some(WHITE)), Field(2, 4, Some(WHITE)), Field(3, 4, Some(WHITE)), Field(4, 4))),
        3, 0, List(Player("Player1", WHITE), Player("Player2", BLACK)))
      "black has 8 points" in {
        gameData.getScoreOf(PlayerColor.BLACK) shouldBe 8
      }

      "white has 12 points" in {
        gameData.getScoreOf(PlayerColor.WHITE) shouldBe 12
      }

      "return the correct current player" in {
        gameData.getCurrentPlayer should be(Player("Player1", WHITE))
      }
      "return the coordinates for a valid input aa" in {
        gameData.getCoordinatesFromInput("aa") shouldBe Some(0, 0)
      }
      "return None for an input asd which is too long" in {
        gameData.getCoordinatesFromInput("asd") shouldBe None
      }
      "return None for an invalid input ää" in {
        gameData.getCoordinatesFromInput("ää") shouldBe None
      }

      "return the updated board after a placed stone" in {
        gameData.placeStone("aa") shouldBe List(
          List(Field(0, 0, Some(WHITE)), Field(1, 0, Some(WHITE)), Field(2, 0, Some(WHITE)), Field(3, 0, Some(WHITE)), Field(4, 0)),
          List(Field(0, 1, Some(WHITE)), Field(1, 1, Some(BLACK)), Field(2, 1, Some(BLACK)), Field(3, 1, Some(BLACK)), Field(4, 1, Some(WHITE))),
          List(Field(0, 2, Some(WHITE)), Field(1, 2, Some(BLACK)), Field(2, 2), Field(3, 2, Some(BLACK)), Field(4, 2, Some(WHITE))),
          List(Field(0, 3, Some(WHITE)), Field(1, 3, Some(BLACK)), Field(2, 3, Some(BLACK)), Field(3, 3, Some(BLACK)), Field(4, 3, Some(WHITE))),
          List(Field(0, 4), Field(1, 4, Some(WHITE)), Field(2, 4, Some(WHITE)), Field(3, 4, Some(WHITE)), Field(4, 4)))
      }

      "remove all necessary stones after a move" in {
        gameData.placeStone("cc") shouldBe List(
          List(Field(0, 0), Field(1, 0, Some(WHITE)), Field(2, 0, Some(WHITE)), Field(3, 0, Some(WHITE)), Field(4, 0)),
          List(Field(0, 1, Some(WHITE)), Field(1, 1), Field(2, 1), Field(3, 1), Field(4, 1, Some(WHITE))),
          List(Field(0, 2, Some(WHITE)), Field(1, 2), Field(2, 2, Some(WHITE)), Field(3, 2), Field(4, 2, Some(WHITE))),
          List(Field(0, 3, Some(WHITE)), Field(1, 3), Field(2, 3), Field(3, 3), Field(4, 3, Some(WHITE))),
          List(Field(0, 4), Field(1, 4, Some(WHITE)), Field(2, 4, Some(WHITE)), Field(3, 4, Some(WHITE)), Field(4, 4)))
      }
    }

    "it is empty" should {
      val gameData = GameData(Nil, 0, 0, Nil)
      "return Nil for a invalid input as an initialized board" in {
        gameData.initBoard("asd") shouldBe Nil
      }
      "return a empty board for a valid input as an initialized board" in {
        gameData.initBoard("9").length shouldBe 9
      }
      "return a Some Value for a valid input of 9" in {
        gameData.isBoardInputValid("9") shouldBe Some(9)
      }
      "return a Some Value for a valid input of 9x9" in {
        gameData.isBoardInputValid("9x9") shouldBe Some(9)
      }
      "return a Some Value for a valid input of 10" in {
        gameData.isBoardInputValid("10") shouldBe Some(10)
      }
      "return a Some Value for a valid input of 10x10" in {
        gameData.isBoardInputValid("10x10") shouldBe Some(10)
      }
      "return a Some Value for a valid input of 11" in {
        gameData.isBoardInputValid("11") shouldBe Some(11)
      }
      "return a Some Value for a valid input of 11x11" in {
        gameData.isBoardInputValid("11x11") shouldBe Some(11)
      }
      "return a Some Value for a valid input of 12" in {
        gameData.isBoardInputValid("12") shouldBe Some(12)
      }
      "return a Some Value for a valid input of 12x12" in {
        gameData.isBoardInputValid("12x12") shouldBe Some(12)
      }
      "return a Some Value for a valid input of 13" in {
        gameData.isBoardInputValid("13") shouldBe Some(13)
      }
      "return a Some Value for a valid input of 13x13" in {
        gameData.isBoardInputValid("13x13") shouldBe Some(13)
      }
      "return a Some Value for a valid input of 14" in {
        gameData.isBoardInputValid("14") shouldBe Some(14)
      }
      "return a Some Value for a valid input of 14x14" in {
        gameData.isBoardInputValid("14x14") shouldBe Some(14)
      }
      "return a Some Value for a valid input of 15" in {
        gameData.isBoardInputValid("15") shouldBe Some(15)
      }
      "return a Some Value for a valid input of 15x15" in {
        gameData.isBoardInputValid("15x15") shouldBe Some(15)
      }
      "return a Some Value for a valid input of 16" in {
        gameData.isBoardInputValid("16") shouldBe Some(16)
      }
      "return a Some Value for a valid input of 16x16" in {
        gameData.isBoardInputValid("16x16") shouldBe Some(16)
      }
      "return a Some Value for a valid input of 17" in {
        gameData.isBoardInputValid("17") shouldBe Some(17)
      }
      "return a Some Value for a valid input of 17x17" in {
        gameData.isBoardInputValid("17x17") shouldBe Some(17)
      }
      "return a Some Value for a valid input of 18" in {
        gameData.isBoardInputValid("18") shouldBe Some(18)
      }
      "return a Some Value for a valid input of 18x18" in {
        gameData.isBoardInputValid("18x18") shouldBe Some(18)
      }
      "return a Some Value for a valid input of 19" in {
        gameData.isBoardInputValid("19") shouldBe Some(19)
      }
      "return a Some Value for a valid input of 19x19" in {
        gameData.isBoardInputValid("19x19") shouldBe Some(19)
      }
      "return a None for an invalid input of asd" in {
        gameData.isBoardInputValid("asd") shouldBe None
      }

      "add first player with color black" in {
        gameData.initPlayer("Player1") shouldBe List(Player("Player1", BLACK))
      }
      "add second with color white" in {
        val gameDataFirstPlayer = gameData.copy(players = gameData.initPlayer("Player1"))
        gameDataFirstPlayer.initPlayer("Player2") shouldBe List(Player("Player2", WHITE), Player("Player1", BLACK))
      }
    }
    "its using the dsl" should {
      "return a board and two players after calling dslGameData sz 9 pw Player1 pb Player 2" in {
        val gameData = GameData(Nil, 0, 0, Nil)
        gameData sz 9 pw "Player1" pb "Player2" shouldBe GameData(List(List(Field(0, 0, None), Field(1, 0, None), Field(2, 0, None), Field(3, 0, None), Field(4, 0, None), Field(5, 0, None), Field(6, 0, None), Field(7, 0, None), Field(8, 0, None)), List(Field(0, 1, None), Field(1, 1, None), Field(2, 1, None), Field(3, 1, None), Field(4, 1, None), Field(5, 1, None), Field(6, 1, None), Field(7, 1, None), Field(8, 1, None)), List(Field(0, 2, None), Field(1, 2, None), Field(2, 2, None), Field(3, 2, None), Field(4, 2, None), Field(5, 2, None), Field(6, 2, None), Field(7, 2, None), Field(8, 2, None)), List(Field(0, 3, None), Field(1, 3, None), Field(2, 3, None), Field(3, 3, None), Field(4, 3, None), Field(5, 3, None), Field(6, 3, None), Field(7, 3, None), Field(8, 3, None)), List(Field(0, 4, None), Field(1, 4, None), Field(2, 4, None), Field(3, 4, None), Field(4, 4, None), Field(5, 4, None), Field(6, 4, None), Field(7, 4, None), Field(8, 4, None)), List(Field(0, 5, None), Field(1, 5, None), Field(2, 5, None), Field(3, 5, None), Field(4, 5, None), Field(5, 5, None), Field(6, 5, None), Field(7, 5, None), Field(8, 5, None)), List(Field(0, 6, None), Field(1, 6, None), Field(2, 6, None), Field(3, 6, None), Field(4, 6, None), Field(5, 6, None), Field(6, 6, None), Field(7, 6, None), Field(8, 6, None)), List(Field(0, 7, None), Field(1, 7, None), Field(2, 7, None), Field(3, 7, None), Field(4, 7, None), Field(5, 7, None), Field(6, 7, None), Field(7, 7, None), Field(8, 7, None)), List(Field(0, 8, None), Field(1, 8, None), Field(2, 8, None), Field(3, 8, None), Field(4, 8, None), Field(5, 8, None), Field(6, 8, None), Field(7, 8, None), Field(8, 8, None))), 0, 0, List(Player("Player2", BLACK), Player("Player1", WHITE)))
      }

      "set a stone afer a valid move by black" in {
        val gameData = GameData(List(List(Field(0, 0, None), Field(1, 0, None), Field(2, 0, None), Field(3, 0, None), Field(4, 0, None), Field(5, 0, None), Field(6, 0, None), Field(7, 0, None), Field(8, 0, None)), List(Field(0, 1, None), Field(1, 1, None), Field(2, 1, None), Field(3, 1, None), Field(4, 1, None), Field(5, 1, None), Field(6, 1, None), Field(7, 1, None), Field(8, 1, None)), List(Field(0, 2, None), Field(1, 2, None), Field(2, 2, None), Field(3, 2, None), Field(4, 2, None), Field(5, 2, None), Field(6, 2, None), Field(7, 2, None), Field(8, 2, None)), List(Field(0, 3, None), Field(1, 3, None), Field(2, 3, None), Field(3, 3, None), Field(4, 3, None), Field(5, 3, None), Field(6, 3, None), Field(7, 3, None), Field(8, 3, None)), List(Field(0, 4, None), Field(1, 4, None), Field(2, 4, None), Field(3, 4, None), Field(4, 4, None), Field(5, 4, None), Field(6, 4, None), Field(7, 4, None), Field(8, 4, None)), List(Field(0, 5, None), Field(1, 5, None), Field(2, 5, None), Field(3, 5, None), Field(4, 5, None), Field(5, 5, None), Field(6, 5, None), Field(7, 5, None), Field(8, 5, None)), List(Field(0, 6, None), Field(1, 6, None), Field(2, 6, None), Field(3, 6, None), Field(4, 6, None), Field(5, 6, None), Field(6, 6, None), Field(7, 6, None), Field(8, 6, None)), List(Field(0, 7, None), Field(1, 7, None), Field(2, 7, None), Field(3, 7, None), Field(4, 7, None), Field(5, 7, None), Field(6, 7, None), Field(7, 7, None), Field(8, 7, None)), List(Field(0, 8, None), Field(1, 8, None), Field(2, 8, None), Field(3, 8, None), Field(4, 8, None), Field(5, 8, None), Field(6, 8, None), Field(7, 8, None), Field(8, 8, None))), 0, 0, List(Player("Player2", BLACK), Player("Player1", WHITE)))
        // TODO: aa kann sicher irgendwie ohne die Anführungstriche gemacht werden (siehe class Key aus Markos Beispiel Internal DSL und ihre Verwendung)
        gameData b "aa" shouldBe GameData(List(List(Field(0, 0, Some(BLACK)), Field(1, 0, None), Field(2, 0, None), Field(3, 0, None), Field(4, 0, None), Field(5, 0, None), Field(6, 0, None), Field(7, 0, None), Field(8, 0, None)), List(Field(0, 1, None), Field(1, 1, None), Field(2, 1, None), Field(3, 1, None), Field(4, 1, None), Field(5, 1, None), Field(6, 1, None), Field(7, 1, None), Field(8, 1, None)), List(Field(0, 2, None), Field(1, 2, None), Field(2, 2, None), Field(3, 2, None), Field(4, 2, None), Field(5, 2, None), Field(6, 2, None), Field(7, 2, None), Field(8, 2, None)), List(Field(0, 3, None), Field(1, 3, None), Field(2, 3, None), Field(3, 3, None), Field(4, 3, None), Field(5, 3, None), Field(6, 3, None), Field(7, 3, None), Field(8, 3, None)), List(Field(0, 4, None), Field(1, 4, None), Field(2, 4, None), Field(3, 4, None), Field(4, 4, None), Field(5, 4, None), Field(6, 4, None), Field(7, 4, None), Field(8, 4, None)), List(Field(0, 5, None), Field(1, 5, None), Field(2, 5, None), Field(3, 5, None), Field(4, 5, None), Field(5, 5, None), Field(6, 5, None), Field(7, 5, None), Field(8, 5, None)), List(Field(0, 6, None), Field(1, 6, None), Field(2, 6, None), Field(3, 6, None), Field(4, 6, None), Field(5, 6, None), Field(6, 6, None), Field(7, 6, None), Field(8, 6, None)), List(Field(0, 7, None), Field(1, 7, None), Field(2, 7, None), Field(3, 7, None), Field(4, 7, None), Field(5, 7, None), Field(6, 7, None), Field(7, 7, None), Field(8, 7, None)), List(Field(0, 8, None), Field(1, 8, None), Field(2, 8, None), Field(3, 8, None), Field(4, 8, None), Field(5, 8, None), Field(6, 8, None), Field(7, 8, None), Field(8, 8, None))), 1, 0, List(Player("Player2", BLACK), Player("Player1", WHITE)))
      }

      "set a stone afer a valid move by white" in {
        val gameData = GameData(List(List(Field(0, 0, None), Field(1, 0, None), Field(2, 0, None), Field(3, 0, None), Field(4, 0, None), Field(5, 0, None), Field(6, 0, None), Field(7, 0, None), Field(8, 0, None)), List(Field(0, 1, None), Field(1, 1, None), Field(2, 1, None), Field(3, 1, None), Field(4, 1, None), Field(5, 1, None), Field(6, 1, None), Field(7, 1, None), Field(8, 1, None)), List(Field(0, 2, None), Field(1, 2, None), Field(2, 2, None), Field(3, 2, None), Field(4, 2, None), Field(5, 2, None), Field(6, 2, None), Field(7, 2, None), Field(8, 2, None)), List(Field(0, 3, None), Field(1, 3, None), Field(2, 3, None), Field(3, 3, None), Field(4, 3, None), Field(5, 3, None), Field(6, 3, None), Field(7, 3, None), Field(8, 3, None)), List(Field(0, 4, None), Field(1, 4, None), Field(2, 4, None), Field(3, 4, None), Field(4, 4, None), Field(5, 4, None), Field(6, 4, None), Field(7, 4, None), Field(8, 4, None)), List(Field(0, 5, None), Field(1, 5, None), Field(2, 5, None), Field(3, 5, None), Field(4, 5, None), Field(5, 5, None), Field(6, 5, None), Field(7, 5, None), Field(8, 5, None)), List(Field(0, 6, None), Field(1, 6, None), Field(2, 6, None), Field(3, 6, None), Field(4, 6, None), Field(5, 6, None), Field(6, 6, None), Field(7, 6, None), Field(8, 6, None)), List(Field(0, 7, None), Field(1, 7, None), Field(2, 7, None), Field(3, 7, None), Field(4, 7, None), Field(5, 7, None), Field(6, 7, None), Field(7, 7, None), Field(8, 7, None)), List(Field(0, 8, None), Field(1, 8, None), Field(2, 8, None), Field(3, 8, None), Field(4, 8, None), Field(5, 8, None), Field(6, 8, None), Field(7, 8, None), Field(8, 8, None))), 0, 0, List(Player("Player2", BLACK), Player("Player1", WHITE)))
        // TODO: aa kann sicher irgendwie ohne die Anführungstriche gemacht werden (siehe class Key aus Markos Beispiel Internal DSL und ihre Verwendung)
        gameData w "aa" shouldBe GameData(List(List(Field(0, 0, Some(WHITE)), Field(1, 0, None), Field(2, 0, None), Field(3, 0, None), Field(4, 0, None), Field(5, 0, None), Field(6, 0, None), Field(7, 0, None), Field(8, 0, None)), List(Field(0, 1, None), Field(1, 1, None), Field(2, 1, None), Field(3, 1, None), Field(4, 1, None), Field(5, 1, None), Field(6, 1, None), Field(7, 1, None), Field(8, 1, None)), List(Field(0, 2, None), Field(1, 2, None), Field(2, 2, None), Field(3, 2, None), Field(4, 2, None), Field(5, 2, None), Field(6, 2, None), Field(7, 2, None), Field(8, 2, None)), List(Field(0, 3, None), Field(1, 3, None), Field(2, 3, None), Field(3, 3, None), Field(4, 3, None), Field(5, 3, None), Field(6, 3, None), Field(7, 3, None), Field(8, 3, None)), List(Field(0, 4, None), Field(1, 4, None), Field(2, 4, None), Field(3, 4, None), Field(4, 4, None), Field(5, 4, None), Field(6, 4, None), Field(7, 4, None), Field(8, 4, None)), List(Field(0, 5, None), Field(1, 5, None), Field(2, 5, None), Field(3, 5, None), Field(4, 5, None), Field(5, 5, None), Field(6, 5, None), Field(7, 5, None), Field(8, 5, None)), List(Field(0, 6, None), Field(1, 6, None), Field(2, 6, None), Field(3, 6, None), Field(4, 6, None), Field(5, 6, None), Field(6, 6, None), Field(7, 6, None), Field(8, 6, None)), List(Field(0, 7, None), Field(1, 7, None), Field(2, 7, None), Field(3, 7, None), Field(4, 7, None), Field(5, 7, None), Field(6, 7, None), Field(7, 7, None), Field(8, 7, None)), List(Field(0, 8, None), Field(1, 8, None), Field(2, 8, None), Field(3, 8, None), Field(4, 8, None), Field(5, 8, None), Field(6, 8, None), Field(7, 8, None), Field(8, 8, None))), 1, 0, List(Player("Player2", BLACK), Player("Player1", WHITE)))
      }
    }
  }
}
