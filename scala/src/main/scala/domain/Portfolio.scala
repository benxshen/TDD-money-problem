package domain

case class Portfolio(moneys: Money*) {
  def evaluate(bank: Bank, toCurrency: String): Either[String, Money] = {
    val convertedMoneys = moneys.map(bank.convert(_, toCurrency))
    val lefts = convertedMoneys.collect { case Left(x) => x }

    if (lefts.isEmpty)
      Right(
        Money(
          convertedMoneys
            .collect { case Right(x) => x }
            .foldLeft(0d)((acc, money) => acc + money.amount),
          toCurrency
        )
      )
    else
      Left(lefts.mkString("Missing exchange rate(s): [", ",", "]"))
  }
}
