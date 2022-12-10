package de.htwg.msi.controller

import de.htwg.msi.model.PlayerColor.{BLACK, WHITE}
import de.htwg.msi.model.{Field, GameData, PlayerColor}
import org.scalatest.matchers.should.Matchers.not.be
import org.scalatest.matchers.should.Matchers.{convertToAnyShouldWrapper}
import org.scalatest.wordspec.AnyWordSpec

class GameControllerTest extends AnyWordSpec {

//  "GameController" should {
//    val gameController = GameController(InitState())
//
//    "return the current controllerState" in {
//      gameController.getControllerState should be(InitState())
//    }
//
//    "notify observers with error msg on invalid message" in {
//      gameController.eval("") should be(Right("Input can`t be empty"))
//    }
//
//    "notify observers with None and update controller State on valid message" in {
//      gameController.eval("9") should be(Left(GameController(PlayerSetupState(GameData(List(List(Field(0, 0, None), Field(1, 0, None), Field(2, 0, None), Field(3, 0, None), Field(4, 0, None), Field(5, 0, None), Field(6, 0, None), Field(7, 0, None), Field(8, 0, None)), List(Field(0, 1, None), Field(1, 1, None), Field(2, 1, None), Field(3, 1, None), Field(4, 1, None), Field(5, 1, None), Field(6, 1, None), Field(7, 1, None), Field(8, 1, None)), List(Field(0, 2, None), Field(1, 2, None), Field(2, 2, None), Field(3, 2, None), Field(4, 2, None), Field(5, 2, None), Field(6, 2, None), Field(7, 2, None), Field(8, 2, None)), List(Field(0, 3, None), Field(1, 3, None), Field(2, 3, None), Field(3, 3, None), Field(4, 3, None), Field(5, 3, None), Field(6, 3, None), Field(7, 3, None), Field(8, 3, None)), List(Field(0, 4, None), Field(1, 4, None), Field(2, 4, None), Field(3, 4, None), Field(4, 4, None), Field(5, 4, None), Field(6, 4, None), Field(7, 4, None), Field(8, 4, None)), List(Field(0, 5, None), Field(1, 5, None), Field(2, 5, None), Field(3, 5, None), Field(4, 5, None), Field(5, 5, None), Field(6, 5, None), Field(7, 5, None), Field(8, 5, None)), List(Field(0, 6, None), Field(1, 6, None), Field(2, 6, None), Field(3, 6, None), Field(4, 6, None), Field(5, 6, None), Field(6, 6, None), Field(7, 6, None), Field(8, 6, None)), List(Field(0, 7, None), Field(1, 7, None), Field(2, 7, None), Field(3, 7, None), Field(4, 7, None), Field(5, 7, None), Field(6, 7, None), Field(7, 7, None), Field(8, 7, None)), List(Field(0, 8, None), Field(1, 8, None), Field(2, 8, None), Field(3, 8, None), Field(4, 8, None), Field(5, 8, None), Field(6, 8, None), Field(7, 8, None), Field(8, 8, None))), 0, 0, List())))))
//    }
//  }
}
