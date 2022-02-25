import domain.{Bank, Money}
import org.scalatest.EitherValues

class BankShould extends org.scalatest.funsuite.AnyFunSuite with EitherValues {
  private val bank: Bank = Bank().addExchangeRate("EUR", "USD", 1.2)

  test("10 EUR -> USD = 12 USD") {
    assert(bank.convert(Money(10, "EUR"), "USD").value === Money(12, "USD"))
  }

  test("10 EUR -> EUR = 10 EUR") {
    assert(bank.convert(Money(10, "EUR"), "EUR").value === Money(10, "EUR"))
  }

  test("Return a Left in case of missing exchange rates") {
    assert(bank.convert(Money(10, "EUR"), "KRW").left.value === "EUR->KRW")
  }

  test("Conversion with different exchange rates EUR -> USD") {
    assert(bank.convert(Money(10, "EUR"), "USD").value === Money(12, "USD"))
    assert(
      bank
        .addExchangeRate("EUR", "USD", 1.3)
        .convert(Money(10, "EUR"), "USD")
        .value === Money(13, "USD")
    )
  }
}
