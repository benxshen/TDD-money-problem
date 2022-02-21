package domain

case class Portfolio(moneys: Money*) {
  private val euroToUsd = 1.2

  def evaluate(currency: String): Money =
    Money(
      moneys.foldLeft(0d)((acc, money) => acc + convert(money, currency)),
      currency
    )

  def convert(money: Money, currency: String): Double =
    if (currency == money.currency) money.amount
    else money.amount * euroToUsd
}
