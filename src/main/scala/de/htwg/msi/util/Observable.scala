package de.htwg.msi.util

trait Observer[S] {
  def receiveUpdate(subject: S): Boolean
}

trait Subject[S] {
  this: S =>
  private var observers: List[Observer[S]] = Nil
  def addObserver(observer: Observer[S]): Unit = observers = observer :: observers

  def notifyObservers(): Unit = observers.foreach(_.receiveUpdate(this))
}
