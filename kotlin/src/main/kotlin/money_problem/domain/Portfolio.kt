package money_problem.domain

import money_problem.domain.Currency.*

private val exchangeRates = mapOf(
    keyFor(EUR, USD) to 1.2,
    keyFor(USD, KRW) to 1100.0
)

data class Portfolio(val moneys: List<Money>)

@Throws(MissingExchangeRatesException::class)
fun Portfolio.evaluate(toCurrency: Currency): Money {
    val missingExchangeRates = missingExchangeRates(toCurrency)

    if (missingExchangeRates.isNotEmpty())
        throw MissingExchangeRatesException(missingExchangeRates)

    return Money(moneys.fold(0.0) { acc, money -> acc + convert(money, toCurrency) }, toCurrency)
}

private fun Portfolio.missingExchangeRates(toCurrency: Currency): List<String> =
    moneys.asSequence()
        .map { it.currency }
        .filter { it != toCurrency }
        .distinct()
        .map { keyFor(it, toCurrency) }
        .filter { !exchangeRates.containsKey(it) }
        .toList()

private fun convert(money: Money, currency: Currency): Double =
    if (currency == money.currency) money.amount
    else money.amount * exchangeRates[keyFor(money.currency, currency)]!!

private fun keyFor(from: Currency, to: Currency): String = "$from->$to"