package domain

import domain.Portfolio.{exchangeRates, keyFor}

case class Portfolio(moneys: Money*) {
  def evaluate(toCurrency: String): Either[String, Money] = {
    val missingExchangeRates: Seq[String] = missingExchangeRatesFor(toCurrency)

    if (missingExchangeRates.nonEmpty)
      Left(
        missingExchangeRates.mkString("Missing exchange rate(s): [", ",", "]")
      )
    else
      Right(
        Money(
          moneys.foldLeft(0d)((acc, money) => acc + convert(money, toCurrency)),
          toCurrency
        )
      )
  }

  private def missingExchangeRatesFor(toCurrency: String) = {
    val missingExchangeRates =
      moneys
        .map(_.currency)
        .filter(_ != toCurrency)
        .distinct
        .map(keyFor(_, toCurrency))
        .filter(!exchangeRates.contains(_))
    missingExchangeRates
  }

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
