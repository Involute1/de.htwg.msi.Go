package de.htwg.msi.model

import de.htwg.msi.model
import de.htwg.msi.model.PlayerColor.{BLACK, WHITE}
import de.htwg.msi.util.Constants.alphabetList

import scala.collection.mutable.ListBuffer

case class GameData(board: List[List[Field]], turn: Int, playTime: Int, players: List[Player]) {
  def getCurrentPlayer: Player = {
    if (this.turn % 2 != 0) this.players.head else this.players(1)
  }

  def initBoard(input: String): List[List[Field]] = {
    val boardSize: Option[Int] = isBoardInputValid(input.trim)
    if (boardSize.isEmpty) {
      Nil
    } else {
      List.tabulate(boardSize.get)(y => List.tabulate(boardSize.get)(x => Field(x, y)))
    }
  }

  def isBoardInputValid(input: String): Option[Int] = {
    input match {
      case "9" | "9x9" => Some(9)
      case "10" | "10x10" => Some(10)
      case "11" | "11x11" => Some(11)
      case "12" | "12x12" => Some(12)
      case "13" | "13x13" => Some(13)
      case "14" | "14x14" => Some(14)
      case "15" | "15x15" => Some(15)
      case "16" | "16x16" => Some(16)
      case "17" | "17x17" => Some(17)
      case "18" | "18x18" => Some(18)
      case "19" | "19x19" => Some(19)
      case _ => None
    }
  }

  def initPlayer(input: String): List[Player] = {
    val isPlayerOne = this.players.isEmpty
    val player: Player = Player(input, if (isPlayerOne) PlayerColor.BLACK else PlayerColor.WHITE)
    this.players.::(player)
  }

  def getCoordinatesFromInput(input: String): Option[(Int, Int)] = {
    if (input.length == 2) {
      val xCoordinate: Int = alphabetList.indexOf(input.charAt(0).toUpper.toString)
      val yCoordinate: Int = alphabetList.indexOf(input.charAt(1).toUpper.toString)
      if (xCoordinate < 0 | yCoordinate < 0) return None
      return Some(xCoordinate, yCoordinate)
    }
    None
  }

  def isMoveInputValid(input: String): Boolean = {
    val currentPlayer: Player = getCurrentPlayer
    val fields: List[Field] = availableMoves(currentPlayer.color)
    val coordinates: Option[(Int, Int)] = getCoordinatesFromInput(input)
    if (coordinates.isEmpty) return false
    fields.exists(f => f.xCoordinate == coordinates.get._1 && f.yCoordinate == coordinates.get._2)
  }

  def placeStone(input: String): List[List[Field]] = {
    val currentPlayer: Player = getCurrentPlayer
    val coordinates: (Int, Int) = getCoordinatesFromInput(input).get
    val updatedField: Field = this.board(coordinates._2)(coordinates._1).copy(stoneColor = Some(currentPlayer.color))
    val updatedBoard = this.board.updated(coordinates._2, this.board(coordinates._2).updated(coordinates._1, updatedField))
    removeStones(updatedField, updatedBoard)
  }

  def placeStone(input: String, color: PlayerColor): List[List[Field]] = {
    val coordinates: (Int, Int) = getCoordinatesFromInput(input).get
    val updatedField: Field = this.board(coordinates._2)(coordinates._1).copy(stoneColor = Some(color))
    val updatedBoard = this.board.updated(coordinates._2, this.board(coordinates._2).updated(coordinates._1, updatedField))
    removeStones(updatedField, updatedBoard)
  }

  def removeStones(updatedField: Field, updatedBoard: List[List[Field]]): List[List[Field]] = {
    val fieldsToRemove: List[Field] = getNeighbourFields(updatedField, board)
      .filter(neighbourField => neighbourField.hasStone && neighbourField.stoneColor.get != updatedField.stoneColor.get)
      .map(neighbourField => findChain(neighbourField, neighbourField.stoneColor.get, updatedBoard))
      .filter(chain => chain.liberties == 0)
      .flatMap(chain => chain.set)
    updatedBoard.map(row => row.map(field => {
      if (fieldsToRemove.contains(field)) {
        field.copy(stoneColor = None)
      } else {
        field
      }
    }))
  }

  def availableMoves(stoneColor: PlayerColor): List[Field] = {
    board.flatten.filter(field => {
      !field.hasStone
    }).filter(field => {
      val otherColor: PlayerColor = if (stoneColor.==(PlayerColor.WHITE)) PlayerColor.BLACK else PlayerColor.WHITE
      findChain(field, stoneColor, board).liberties > 0 || getNeighbourFields(field, board).exists(neighbourField => {
        neighbourField.hasStone && neighbourField.stoneColor.get == otherColor && findChain(neighbourField, otherColor, board).liberties == 1
      })
    })
      .sortBy(f => f.xCoordinate)
  }

  def availableMovesAsString(stoneColor: PlayerColor): String = {
    val movesList: List[Field] = availableMoves(stoneColor)
    movesList.map(f => f.toCoordinateString).mkString(",")
  }

  /**
   * Findet eine Chain für ein gegebenes Anfangsfeld. Berechnet außerdem die Freiheiten für die Chain.
   *
   * @param initialField Das Anfangsfeld.
   * @param stoneColor   Die Farbe der Startfeldes, falls hier noch kein Stein liegt.
   * @return die gefundene Chain
   */
  def findChain(initialField: Field, stoneColor: PlayerColor, board: List[List[Field]]): Chain = {
    val chainSet = scala.collection.mutable.Set[Field](initialField)
    val libertiesSet = scala.collection.mutable.Set[Field](initialField)

    var currentFields: scala.collection.mutable.Set[Field] = scala.collection.mutable.Set[Field](initialField)
    var newFields: scala.collection.mutable.Set[Field] = scala.collection.mutable.Set[Field]()
    var liberties = 0

    while {
      newFields = scala.collection.mutable.Set.empty
      currentFields.foreach(field => {
        val neighbours = getNeighbourFields(field, board)
        neighbours.foreach(field => {
          if (!chainSet.contains(field) && field.hasStone && field.stoneColor.get == stoneColor) {
            newFields += field
            chainSet += field
          } else if (!libertiesSet.contains(field) && !field.hasStone) {
            libertiesSet += field
            liberties += 1
          }
        })
      })
      currentFields = newFields
      newFields.nonEmpty
    } do ()
    Chain(Set.empty ++ chainSet, liberties)
  }

  def getNeighbourFields(field: Field, board: List[List[Field]]): List[Field] = {
    val neighbours = ListBuffer[Field]()
    val leftCoordinate = field.xCoordinate - 1
    if (leftCoordinate >= 0) {
      neighbours += board.apply(field.yCoordinate).apply(leftCoordinate)
    }

    val rightCoordinate = field.xCoordinate + 1
    if (rightCoordinate < board.size) {
      neighbours += board.apply(field.yCoordinate).apply(rightCoordinate)
    }

    val topCoordinate = field.yCoordinate + 1
    if (topCoordinate < board.size) {
      neighbours += board.apply(topCoordinate).apply(field.xCoordinate)
    }

    val bottomCoordinate = field.yCoordinate - 1
    if (bottomCoordinate >= 0) {
      neighbours += board.apply(bottomCoordinate).apply(field.xCoordinate)
    }

    neighbours.toList
  }

  def getScoreOf(color: PlayerColor): Int = {
    getFieldsOf(color).size
  }

  def getFieldsOf(color: PlayerColor): List[Field] = {
    board.flatten.filter(field => {
      field.stoneColor.contains(color)
    })
  }

}
