package de.htwg.msi.model

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
}
