package domain

case class Bank(exchangeRates: Map[String, Double] = Map.empty) {
  private def keyFor(from: String, to: String): String = s"$from->$to"

  def addExchangeRate(from: String, to: String, rate: Double): Bank =
    Bank(exchangeRates + (keyFor(from, to) -> rate))

  def convert(money: Money, currency: String): Either[String, Money] =
    if (canConvert(money.currency, currency)) convertSafely(money, currency)
    else Left(keyFor(money.currency, currency))

  private def canConvert(from: String, to: String): Boolean =
    from == to || exchangeRates.contains(keyFor(from, to))

  private def convertSafely(
      money: Money,
      currency: String
  ): Either[String, Money] =
    Right(
      if (currency == money.currency) money
      else
        Money(
          money.amount * exchangeRates(keyFor(money.currency, currency)),
          currency
        )
    )
}
