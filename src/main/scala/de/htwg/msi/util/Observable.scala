package de.htwg.msi.util

trait Observer[S] {
  def receiveUpdate(subject: S, errorMsg: Option[String]): Boolean
}

trait Subject[S] {
  this: S =>
  private var observers: List[Observer[S]] = Nil
  def addObserver(observer: Observer[S]): Unit = observers = observer :: observers

  def notifyObservers(errorMsg: Option[String]): Unit = observers.foreach(_.receiveUpdate(this, errorMsg: Option[String]))
}
