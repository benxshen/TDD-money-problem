import domain._

class MoneyShould extends org.scalatest.funsuite.AnyFunSuite {
  test("10 EUR x 2 = 20 EUR") {
    val tenEuros = Money(10, "EUR")
    assert(tenEuros.times(2) === Money(20, "EUR"))
  }

  test("4002 KRW / 4 = 1000.5 KRW") {
    val originalMoney = Money(4002, "KRW")
    assert(originalMoney.divide(4) === Money(1000.5, "KRW"))
  }
}
