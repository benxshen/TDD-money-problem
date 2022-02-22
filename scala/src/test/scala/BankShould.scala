import domain.{Bank, Money}
import org.scalatest.EitherValues

class BankShould extends org.scalatest.funsuite.AnyFunSuite with EitherValues {
  test("10 EUR -> USD = 12 USD") {
    val bank = Bank().addExchangeRate("EUR", "USD", 1.2)
    assert(bank.convert(Money(10, "EUR"), "USD").value === Money(12, "USD"))
  }

  test("10 EUR -> EUR = 10 EUR") {
    val bank = Bank()
    assert(bank.convert(Money(10, "EUR"), "EUR").value === Money(10, "EUR"))
  }

  test("Return a Left in case of missing exchange rates") {
    val bank = Bank()
    assert(bank.convert(Money(10, "EUR"), "KRW").left.value === "EUR->KRW")
  }
}
