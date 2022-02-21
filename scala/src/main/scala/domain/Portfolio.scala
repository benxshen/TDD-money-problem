package domain

import domain.Portfolio.{exchangeRates, keyFor}

case class Portfolio(moneys: Money*) {
  def evaluate(currency: String): Money =
    Money(
      moneys.foldLeft(0d)((acc, money) => acc + convert(money, currency)),
      currency
    )

  def convert(money: Money, currency: String): Double =
    if (currency == money.currency) money.amount
    else money.amount * exchangeRates(keyFor(money.currency, currency))
}

object Portfolio {
  private val exchangeRates: Map[String, Double] = Map(
    (keyFor("EUR", "USD"), 1.2),
    (keyFor("USD", "KRW"), 1100)
  )

  private def keyFor(from: String, to: String): String = s"$from->$to"
}
