package de.htwg.msi.model

import de.htwg.msi.model
import org.scalatest.matchers.should.Matchers.shouldBe
import org.scalatest.wordspec.AnyWordSpec

class GameDataDSLTest extends AnyWordSpec {
  "GameDataDSL" when {
    val gameData: GameData = GameData(Nil, 0, 0, Nil)
        "its using the internal dsl" should {
          "return a board with size 9 after calling boardSizeIs 9" in {
            implicit val gameDataDSL: GameDataDSL = GameDataDSL()
            val expectedGameData: GameData = GameData(gameData.initBoard("9"), 0, 0, Nil)
            gameDataDSL boardSizeIs 9 shouldBe expectedGameData
          }
          "return a PlayerList with 1 white Player" in {
            implicit val gameDataDSL: GameDataDSL = GameDataDSL()
            val expectedGameData: GameData = GameData(Nil, 0, 0, Player("foo", PlayerColor.WHITE)::Nil)
            gameDataDSL playerWhiteIs "foo" shouldBe expectedGameData
          }
          "return a PlayerList with 1 black Player" in {
            implicit val gameDataDSL: GameDataDSL = GameDataDSL()
            val expectedGameData: GameData = GameData(Nil, 0, 0, Player("foo", PlayerColor.BLACK) :: Nil)
            gameDataDSL playerBlackIs "foo" shouldBe expectedGameData
          }
          "should place a black stone on the board" in {
            implicit val gameDataDSL: GameDataDSL = GameDataDSL()
            var expectedGameData: GameData = GameData(gameData.initBoard("9"), 0, 0, Nil)
            expectedGameData = expectedGameData.copy(board = expectedGameData.placeStone("AA", PlayerColor.BLACK))
            gameDataDSL boardSizeIs 9
            gameDataDSL playerBlackIs "blackFoo"
            gameDataDSL playerWhiteIs "whiteFoo"
            gameDataDSL blackPlaceStoneAt "AA"
            gameDataDSL.gameData.board shouldBe expectedGameData.board
          }
          "should place a white stone on the board" in {
            implicit val gameDataDSL: GameDataDSL = GameDataDSL()
            var expectedGameData: GameData = GameData(gameData.initBoard("9"), 0, 0, Nil)
            expectedGameData = expectedGameData.copy(board = expectedGameData.placeStone("AA", PlayerColor.WHITE))
            gameDataDSL boardSizeIs 9
            gameDataDSL playerBlackIs "blackFoo"
            gameDataDSL playerWhiteIs "whiteFoo"
            gameDataDSL whitePlaceStoneAt "AA"
            gameDataDSL.gameData.board shouldBe expectedGameData.board
          }
          "should black forfeit on empty place" in {
            implicit val gameDataDSL: GameDataDSL = GameDataDSL()
            val expectedGameData: GameData = GameData(gameData.initBoard("9"), 0, 0, Nil)
            gameDataDSL boardSizeIs 9
            gameDataDSL playerBlackIs "blackFoo"
            gameDataDSL playerWhiteIs "whiteFoo"
            gameDataDSL blackPlaceStoneAt ""
            gameDataDSL.gameData.board shouldBe expectedGameData.board
          }
          "should white forfeit on empty place" in {
            implicit val gameDataDSL: GameDataDSL = GameDataDSL()
            val expectedGameData: GameData = GameData(gameData.initBoard("9"), 0, 0, Nil)
            gameDataDSL boardSizeIs 9
            gameDataDSL playerBlackIs "blackFoo"
            gameDataDSL playerWhiteIs "whiteFoo"
            gameDataDSL whitePlaceStoneAt ""
            gameDataDSL.gameData.board shouldBe expectedGameData.board
          }
        }
  }
}
