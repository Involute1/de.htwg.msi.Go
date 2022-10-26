package de.htwg.msi.model

import de.htwg.msi.model

import scala.collection.mutable.ListBuffer

case class GameData(board: List[List[Field]], turn: Int, playTime: Int, players: List[Player]) {
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

  def availableMoves(stoneColor: PlayerColor): List[Field] = {
    board.flatten.filter(field => {
      !field.hasStone
    }).filter(field => {
      var otherColor: PlayerColor = PlayerColor.WHITE
      if (stoneColor == PlayerColor.WHITE) {
        otherColor = PlayerColor.BLACK
      }

      findChain(field, stoneColor).liberties > 0 || getNeighbourFields(field).exists(neighbourField => {
        neighbourField.stoneColor.get == otherColor && findChain(neighbourField, otherColor).liberties == 1
      })
    })
  }

  /**
   * Findet eine Chain für ein gegebenes Anfangsfeld. Berechnet außerdem die Freiheiten für die Chain.
   *
   * @param initialField Das Anfangsfeld.
   * @param stoneColor   Die Farbe der Startfeldes, falls hier noch kein Stein liegt.
   * @return die gefundene Chain
   */
  def findChain(initialField: Field, stoneColor: PlayerColor): Chain = {
    val chainSet = scala.collection.mutable.Set[Field](initialField)
    val libertiesSet = scala.collection.mutable.Set[Field](initialField)

    var currentFields: scala.collection.mutable.Set[Field] = scala.collection.mutable.Set[Field](initialField)
    var newFields: scala.collection.mutable.Set[Field] = scala.collection.mutable.Set[Field]()
    var liberties = 0

    while {
      newFields = scala.collection.mutable.Set.empty
      currentFields.foreach(field => {
        val neighbours = getNeighbourFields(field)
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

  def getNeighbourFields(field: Field): List[Field] = {
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

  /*
  * Recusive aber mit vielen doppelten Aufrufen von Feldern, wodurch liberties nicht direkt berechnet werden können.

  def findChain(field: Field, stoneColor: PlayerColor, set: scala.collection.mutable.Set[Field]): scala.collection.mutable.Set[Field] = {
    // Geht das irgendwie ohne mutable Set? Und aktuell werden viele Felder doppelt hinzugefügt und erst durchs Set wieder "entfernt", was auch sehr unperformant ist.
    if (set.nonEmpty && (!field.hasStone || field.stoneColor.get != stoneColor)) {
      return scala.collection.mutable.Set[Field]()
    }

    val currentSet = set += field;

    val leftCoordinate = field.xCoordinate - 1;
    if (leftCoordinate >= 0) {
      val leftField = board.apply(field.yCoordinate).apply(leftCoordinate)
      if (!currentSet.contains(leftField)) {
        currentSet ++= findChain(leftField, stoneColor, currentSet);
      }
    }

    val rightCoordinate = field.xCoordinate + 1;
    if (rightCoordinate < board.size) {
      val rightField = board.apply(field.yCoordinate).apply(rightCoordinate)
      if (!currentSet.contains(rightField)) {
        currentSet ++= findChain(rightField, stoneColor, currentSet);
      }
    }

    val topCoordinate = field.yCoordinate + 1;
    if (topCoordinate < board.size) {
      val topField = board.apply(topCoordinate).apply(field.xCoordinate)
      if (!currentSet.contains(topField)) {
        currentSet ++= findChain(topField, stoneColor, currentSet);
      }
    }

    val bottomCoordinate = field.yCoordinate - 1;
    if (bottomCoordinate >= 0) {
      val bottomField = board.apply(bottomCoordinate).apply(field.xCoordinate)
      if (!currentSet.contains(bottomField)) {
        currentSet ++= findChain(bottomField, stoneColor, currentSet);
      }
    }

    currentSet
  }*/

}
