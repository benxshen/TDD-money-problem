package money_problem.domain

private const val euroToUSD = 1.2

data class Portfolio(val moneys: List<Money>)

fun Portfolio.evaluate(currency: Currency): Money =
    Money(moneys.fold(0.0) { acc, money -> acc + convert(money, currency) }, currency)

private fun convert(money: Money, currency: Currency): Double =
    if (currency == money.currency) money.amount else money.amount * euroToUSD