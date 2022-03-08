package money_problem.domain

import arrow.core.Either

data class Bank(val exchangeRates: Map<String, Double>)

fun bankWithExchangeRate(from: Currency, to: Currency, rate: Double): Bank =
    Bank(emptyMap())
        .addExchangeRate(from, to, rate)

fun Bank.addExchangeRate(from: Currency, to: Currency, rate: Double): Bank =
    copy(exchangeRates = this.exchangeRates.plus(keyFor(from, to) to rate))

private fun Bank.canConvert(from: Currency, to: Currency): Boolean =
    from == to || exchangeRates.containsKey(keyFor(from, to))

private fun keyFor(from: Currency, to: Currency): String = "$from->$to"

fun Bank.convert(money: Money, currency: Currency): Either<String, Money> =
    if (canConvert(money.currency, currency)) convertSafely(money, currency)
    else Either.Left(keyFor(money.currency, currency))

private fun Bank.convertSafely(money: Money, currency: Currency): Either.Right<Money> =
    Either.Right(
        if (money.currency == currency) money
        else Money(money.amount * exchangeRates[keyFor(money.currency, currency)]!!, currency)
    )