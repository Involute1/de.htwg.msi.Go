package de.htwg.msi.go
package model

import model.PlayerColor.*
import org.scalatest.matchers.should.Matchers.*
import org.scalatest.wordspec.AnyWordSpec

class GameDataTest extends AnyWordSpec {
  "GameData" when {
    "it has an empty board" should {
      val gameData = GameData(List(
        List(Field(0, 0), Field(1, 0), Field(2, 0), Field(3, 0)),
        List(Field(0, 1), Field(1, 1), Field(2, 1), Field(3, 1)),
        List(Field(0, 2), Field(1, 2), Field(2, 2), Field(3, 2)),
        List(Field(0, 3), Field(1, 3), Field(2, 3), Field(3, 3))),
        0, 0)
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
        0, 0)
      "not allow that move for black" in {
        gameData.availableMoves(PlayerColor.BLACK) shouldBe empty
      }
      "allow that move for white" in {
        gameData.availableMoves(PlayerColor.WHITE) should contain theSameElementsAs
          List(Field(2, 2))
      }
    }
    "is has multiple chains" should {
      val gameData = GameData(List(
        List(Field(0, 0), Field(1, 0, Some(WHITE)), Field(2, 0, Some(WHITE)), Field(3, 0, Some(WHITE)), Field(4, 0)),
        List(Field(0, 1, Some(WHITE)), Field(1, 1, Some(BLACK)), Field(2, 1, Some(BLACK)), Field(3, 1, Some(BLACK)), Field(4, 1, Some(WHITE))),
        List(Field(0, 2, Some(WHITE)), Field(1, 2, Some(BLACK)), Field(2, 2), Field(3, 2, Some(BLACK)), Field(4, 2, Some(WHITE))),
        List(Field(0, 3, Some(WHITE)), Field(1, 3, Some(BLACK)), Field(2, 3, Some(BLACK)), Field(3, 3, Some(BLACK)), Field(4, 3, Some(WHITE))),
        List(Field(0, 4), Field(1, 4, Some(WHITE)), Field(2, 4, Some(WHITE)), Field(3, 4, Some(WHITE)), Field(4, 4))),
        0, 0)
      "return black chain on findChain" in {
        gameData.findChain(Field(2, 2), BLACK) should be(Chain(Set(Field(1, 1, Some(BLACK)), Field(2, 1, Some(BLACK)), Field(3, 1, Some(BLACK)),
          Field(1, 2, Some(BLACK)), Field(2, 2), Field(3, 2, Some(BLACK)),
          Field(1, 3, Some(BLACK)), Field(2, 3, Some(BLACK)), Field(3, 3, Some(BLACK))), 0))
      }
      "return one white chain on findChain" in {
        gameData.findChain(Field(0, 1, Some(WHITE)), WHITE) should be(Chain(Set(Field(0, 1, Some(WHITE)), Field(0, 2, Some(WHITE)), Field(0, 3, Some(WHITE))), 2))
      }
    }
    "it has a black eye" should {
      val gameData = GameData(List(
        List(Field(0, 0), Field(1, 0, Some(WHITE)), Field(2, 0, Some(WHITE)), Field(3, 0, Some(WHITE)), Field(4, 0, Some(WHITE)), Field(5, 0)),
        List(Field(0, 1), Field(1, 1, Some(WHITE)), Field(2, 1, Some(BLACK)), Field(3, 1, Some(BLACK)), Field(4, 1, Some(BLACK)), Field(5, 1)),
        List(Field(0, 2), Field(1, 2, Some(WHITE)), Field(2, 2, Some(BLACK)), Field(3, 2), Field(4, 2, Some(BLACK)), Field(5, 2)),
        List(Field(0, 3, Some(BLACK)), Field(1, 3, Some(BLACK)), Field(2, 3, Some(BLACK)), Field(3, 3, Some(BLACK)), Field(4, 3), Field(5, 3)),
        List(Field(0, 4), Field(1, 4), Field(2, 4), Field(3, 4), Field(4, 4), Field(5, 4))),
        0, 0)
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
        0, 0)
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
        0, 0)
      "black has 8 points" in {
        gameData.getScoreOf(PlayerColor.BLACK) shouldBe 8
      }

      "white has 12 points" in {
        gameData.getScoreOf(PlayerColor.WHITE) shouldBe 12
      }
    }
  }
}
