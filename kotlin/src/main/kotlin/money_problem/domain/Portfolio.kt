package money_problem.domain

import arrow.core.Either

data class Portfolio(val moneys: List<Money>)

fun Portfolio.evaluate(
    bank: Bank,
    toCurrency: Currency
): Either<String, Money> {
    val convertedMoneys = moneys.map { bank.convert(it, toCurrency) }
    val lefts = convertedMoneys.lefts()

    return if (lefts.isEmpty())
        Either.Right(
            Money(
                convertedMoneys
                    .rights()
                    .fold(0.0) { acc, money -> acc + money.amount }, toCurrency
            )
        )
    else Either.Left(
        lefts.joinToString(separator = ",", prefix = "Missing exchange rate(s): [", postfix = "]")
    )
}