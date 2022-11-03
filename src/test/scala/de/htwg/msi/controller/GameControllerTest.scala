package de.htwg.msi.controller

import de.htwg.msi.model.PlayerColor.{BLACK, WHITE}
import de.htwg.msi.model.{Field, GameData, PlayerColor}
import org.scalatest.matchers.should.Matchers.*
import org.scalatest.wordspec.AnyWordSpec

class GameControllerTest extends AnyWordSpec {

  "GameController" should {
    val gameController = GameController();
    "print correct empty board" in {
      gameController.printGameBoard(List(
        List(Field(0, 0), Field(1, 0), Field(2, 0), Field(3, 0)),
        List(Field(0, 1), Field(1, 1), Field(2, 1), Field(3, 1)),
        List(Field(0, 2), Field(1, 2), Field(2, 2), Field(3, 2)),
        List(Field(0, 3), Field(1, 3), Field(2, 3), Field(3, 3)))) should be(
        """|0   A  B  C  D 
           |    -  -  -  - 
           |A | O  O  O  O
           |B | O  O  O  O
           |C | O  O  O  O
           |D | O  O  O  O""".stripMargin.trim)
    }
    "print correct full board" in {
      gameController.printGameBoard(List(
        List(Field(0, 0, Some(WHITE)), Field(1, 0, Some(BLACK)), Field(2, 0, Some(WHITE)), Field(3, 0, Some(WHITE))),
        List(Field(0, 1, Some(BLACK)), Field(1, 1, Some(WHITE)), Field(2, 1, Some(WHITE)), Field(3, 1, Some(BLACK))),
        List(Field(0, 2, Some(WHITE)), Field(1, 2, Some(WHITE)), Field(2, 2, Some(BLACK)), Field(3, 2, Some(WHITE))),
        List(Field(0, 3, Some(WHITE)), Field(1, 3, Some(BLACK)), Field(2, 3, Some(WHITE)), Field(3, 3, Some(WHITE))))) should be(
        """|0   A  B  C  D 
           |    -  -  -  - 
           |A | w  b  w  w
           |B | b  w  w  b
           |C | w  w  b  w
           |D | w  b  w  w""".stripMargin.trim)
    }
    "return the updated controllerState" in {
      gameController.updateControllerState(InitState(gameController)) should be(InitState(gameController))
    }
  }
}