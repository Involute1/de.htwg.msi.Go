package de.htwg.msi.go
package model

class Player(name: String, color: PlayerColor) { }

enum PlayerColor(val shortText: String) {
  case WHITE extends PlayerColor("w")
  case BLACK extends PlayerColor("b")
}
