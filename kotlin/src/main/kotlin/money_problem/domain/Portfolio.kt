package money_problem.domain

data class Portfolio(val moneys: List<Money>)

fun Portfolio.evaluate(currency: Currency): Money =
    Money(moneys.fold(0.0) { acc, money -> acc + money.amount }, currency)
