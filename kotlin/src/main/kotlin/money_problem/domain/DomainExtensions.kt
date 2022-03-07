package money_problem.domain

fun Double.dollars(): Money = Money(this, Currency.USD);
fun Double.euros(): Money = Money(this, Currency.EUR);
fun Double.koreanWons(): Money = Money(this, Currency.KRW);