class MoneyTest extends org.scalatest.funsuite.AnyFunSuite {
  test("TestMultiplication") {
    val fiver = Dollar(5);
    val tenner = fiver.Times(2);

    assert(tenner.amount === 10)
  }

  case class Dollar(amount: Int) {
    def Times(multiplier: Int): Dollar = Dollar(amount * multiplier)
  }
}