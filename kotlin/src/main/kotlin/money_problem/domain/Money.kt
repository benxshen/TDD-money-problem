package money_problem.domain

data class Money(val amount: Double, val currency: Currency)

fun Money.times(multiplier: Int): Money = copy(amount = amount * multiplier)
fun Money.divide(divisor: Int): Money = copy(amount = amount / divisor)