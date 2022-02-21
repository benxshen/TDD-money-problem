package domain

case class Money(amount: Double, currency: String) {
  def times(multiplier: Int): Money = copy(amount = amount * multiplier)
  def divide(divisor: Int): Money = copy(amount = amount / divisor)
}
