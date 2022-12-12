package de.htwg.msi.model

import org.scalatest.matchers.must.Matchers.convertToAnyMustWrapper
import org.scalatest.wordspec.AnyWordSpec

class GameDataDSLTest extends AnyWordSpec {
  "GameDataDSL" when {
    val gameData: GameData = GameData(Nil, 0, 0, Nil)
    "its using the internal dsl" should {
      "return a board with size 9 after calling boardSizeIs 9" in {
        implicit val gameDataDSL: GameDataDSL = new GameDataDSL()
        val expectedGameData: GameData = GameData(gameData.initBoard("9"), 0, 0, Nil)
        gameDataDSL boardSizeIs 9 mustBe expectedGameData
      }
      "return a PlayerList with 1 white Player" in {
        implicit val gameDataDSL: GameDataDSL = new GameDataDSL()
        val expectedGameData: GameData = GameData(Nil, 0, 0, Player("foo", PlayerColor.WHITE) :: Nil)
        gameDataDSL playerWhiteIs "foo" mustBe expectedGameData
      }
      "return a PlayerList with 1 black Player" in {
        implicit val gameDataDSL: GameDataDSL = new GameDataDSL()
        val expectedGameData: GameData = GameData(Nil, 0, 0, Player("foo", PlayerColor.BLACK) :: Nil)
        gameDataDSL playerBlackIs "foo" mustBe expectedGameData
      }
      "must place a black stone on the board" in {
        implicit val gameDataDSL: GameDataDSL = new GameDataDSL()
        var expectedGameData: GameData = GameData(gameData.initBoard("9"), 0, 0, Nil)
        expectedGameData = expectedGameData.copy(board = expectedGameData.placeStone("AA", PlayerColor.BLACK))
        gameDataDSL boardSizeIs 9
        gameDataDSL playerBlackIs "blackFoo"
        gameDataDSL playerWhiteIs "whiteFoo"
        gameDataDSL blackPlaceStoneAt "AA"
        gameDataDSL.gameData.board mustBe expectedGameData.board
      }
      "must place a white stone on the board" in {
        implicit val gameDataDSL: GameDataDSL = new GameDataDSL()
        var expectedGameData: GameData = GameData(gameData.initBoard("9"), 0, 0, Nil)
        expectedGameData = expectedGameData.copy(board = expectedGameData.placeStone("AA", PlayerColor.WHITE))
        gameDataDSL boardSizeIs 9
        gameDataDSL playerBlackIs "blackFoo"
        gameDataDSL playerWhiteIs "whiteFoo"
        gameDataDSL whitePlaceStoneAt "AA"
        gameDataDSL.gameData.board mustBe expectedGameData.board
      }
      "must black forfeit on empty place" in {
        implicit val gameDataDSL: GameDataDSL = new GameDataDSL()
        val expectedGameData: GameData = GameData(gameData.initBoard("9"), 0, 0, Nil)
        gameDataDSL boardSizeIs 9
        gameDataDSL playerBlackIs "blackFoo"
        gameDataDSL playerWhiteIs "whiteFoo"
        gameDataDSL blackPlaceStoneAt ""
        gameDataDSL.gameData.board mustBe expectedGameData.board
      }
      "must white forfeit on empty place" in {
        implicit val gameDataDSL: GameDataDSL = new GameDataDSL()
        val expectedGameData: GameData = GameData(gameData.initBoard("9"), 0, 0, Nil)
        gameDataDSL boardSizeIs 9
        gameDataDSL playerBlackIs "blackFoo"
        gameDataDSL playerWhiteIs "whiteFoo"
        gameDataDSL whitePlaceStoneAt ""
        gameDataDSL.gameData.board mustBe expectedGameData.board
      }
    }
  }
}
