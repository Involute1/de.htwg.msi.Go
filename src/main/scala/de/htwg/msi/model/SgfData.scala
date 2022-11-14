package de.htwg.msi.model

case class SgfData(gameData: SgfGameData, moves: List[Move]) {}

case class SgfGameData(size: Int, pw: String, pb: String) {}

case class Move(player: String, move: String) {}