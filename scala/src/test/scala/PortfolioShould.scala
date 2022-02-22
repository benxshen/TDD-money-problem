import domain.{Bank, Money, Portfolio}
import org.scalatest.EitherValues

class PortfolioShould
    extends org.scalatest.funsuite.AnyFunSuite
    with EitherValues {
  private val bank = Bank()
    .addExchangeRate("EUR", "USD", 1.2)
    .addExchangeRate("USD", "KRW", 1100)

  test("5 USD + 10 USD = 15 USD") {
    val fiveDollars = Money(5, "USD")
    val tenDollars = Money(10, "USD")
    val portfolio = Portfolio(fiveDollars, tenDollars)

    assert(portfolio.evaluate(bank, "USD").value === Money(15, "USD"))
  }

  test("5 USD + 10 EUR = 17 USD") {
    val fiveDollars = Money(5, "USD")
    val tenEuros = Money(10, "EUR")
    val portfolio = Portfolio(fiveDollars, tenEuros)

    assert(portfolio.evaluate(bank, "USD").value === Money(17, "USD"))
  }

  test("1 USD + 1100 KRW = 2200 KRW") {
    val portfolio = Portfolio(Money(1, "USD"), Money(1100, "KRW"))
    assert(portfolio.evaluate(bank, "KRW").value === Money(2200, "KRW"))
  }

  test("Return a greedy message in case of missing exchange rates") {
    val portfolio = Portfolio(Money(1, "USD"), Money(1, "EUR"), Money(1, "KRW"))
    assert(
      portfolio
        .evaluate(bank, "KRW")
        .left
        .value === "Missing exchange rate(s): [EUR->KRW]"
    )
  }
}
