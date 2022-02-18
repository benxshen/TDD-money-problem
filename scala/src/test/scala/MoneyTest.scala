class MoneyTest extends org.scalatest.funsuite.AnyFunSuite {
  test("5 USD x 2 = 10 USD") {
    val fiveDollars = Money(5, "USD")
    assert(fiveDollars.times(2) === Money(10, "USD"))
  }

  test("10 EUR x 2 = 20 EUR") {
    val tenEuros = Money(10, "EUR")
    assert(tenEuros.times(2) === Money(20, "EUR"))
  }

  test("4002 KRW / 4 = 1000.5 KRW") {
    val originalMoney = Money(4002, "KRW")
    assert(originalMoney.divide(4) === Money(1000.5, "KRW"))
  }

  case class Money(amount: Double, currency: String) {
    def times(multiplier: Int): Money = copy(amount = amount * multiplier)
    def divide(divisor: Int) = copy(amount = amount / divisor)
  }
}
