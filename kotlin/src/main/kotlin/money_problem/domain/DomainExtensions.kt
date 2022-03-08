package money_problem.domain

fun Double.dollars(): Money = Money(this, Currency.USD)
fun Double.euros(): Money = Money(this, Currency.EUR)
fun Double.koreanWons(): Money = Money(this, Currency.KRW)
fun Money.addToPortfolio(money: Money): Portfolio = Portfolio(listOf(this, money))
fun Portfolio.add(money: Money): Portfolio = this.copy(moneys = this.moneys.plus(money))