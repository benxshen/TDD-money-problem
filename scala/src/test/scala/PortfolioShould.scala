import domain.{Money, Portfolio}

class PortfolioShould extends org.scalatest.funsuite.AnyFunSuite {
  test("5 USD + 10 USD = 15 USD") {
    val fiveDollars = Money(5, "USD")
    val tenDollars = Money(10, "USD")
    val portfolio = Portfolio(fiveDollars, tenDollars)

    assert(portfolio.Evaluate("USD") === Money(15, "USD"))
  }
}
