package de.htwg.msi.model

import de.htwg.msi.util.Constants.alphabetList

case class Field(xCoordinate: Int, yCoordinate: Int, stoneColor: Option[PlayerColor] = None) {
  def hasStone: Boolean = {
    stoneColor.isDefined
  }

  override def equals(obj: Any): Boolean = {
    obj match {
      case field: Field => field.xCoordinate == xCoordinate && field.yCoordinate == yCoordinate && field.stoneColor == stoneColor
      case _ => false
    }

  }

  def toPrettyString: String = {
    if (this.hasStone) {
      this.stoneColor.get.shortText
    } else {
      "O"
    }
  }

  def toCoordinateString: String = {
    alphabetList(this.xCoordinate) + alphabetList(this.yCoordinate)
  }
}
