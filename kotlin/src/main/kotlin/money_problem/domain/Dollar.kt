package money_problem.domain

data class Dollar(val amount: Int)

fun Dollar.times(multiplier: Int): Dollar = Dollar(amount * multiplier)