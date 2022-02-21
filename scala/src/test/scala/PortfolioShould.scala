import domain.{Money, Portfolio}

class PortfolioShould extends org.scalatest.funsuite.AnyFunSuite {
  test("5 USD + 10 USD = 15 USD") {
    val fiveDollars = Money(5, "USD")
    val tenDollars = Money(10, "USD")
    val portfolio = Portfolio(fiveDollars, tenDollars)

    assert(portfolio.evaluate("USD") === Money(15, "USD"))
  }

  test("5 USD + 10 EUR = 17 USD") {
    val fiveDollars = Money(5, "USD")
    val tenEuros = Money(10, "EUR")
    val portfolio = Portfolio(fiveDollars, tenEuros)

    assert(portfolio.evaluate("USD") === Money(17, "USD"))
  }

  test("1 USD + 1100 KRW = 2200 KRW") {
    val portfolio = Portfolio(Money(1, "USD"), Money(1100, "KRW"))
    assert(portfolio.evaluate("KRW") === Money(2200, "KRW"))
  }
}
