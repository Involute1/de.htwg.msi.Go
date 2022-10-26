package de.htwg.msi.model

case class Chain(set: Set[Field], liberties: Int) {

  override def equals(obj: Any): Boolean = {
    obj match {
      case chain: Chain => chain.liberties == liberties && chain.set == set
      case _ => false
    }
  }

}
