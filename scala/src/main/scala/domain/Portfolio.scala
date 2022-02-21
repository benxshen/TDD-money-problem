package domain

case class Portfolio(moneys: Money*) {
  def Evaluate(currency: String): Money =
    Money(moneys.foldLeft(0d)((acc, money) => acc + money.amount), currency)
}
