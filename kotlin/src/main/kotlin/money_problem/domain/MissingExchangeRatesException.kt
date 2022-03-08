package money_problem.domain

class MissingExchangeRatesException(missingExchangeRates: List<String>) :
    Exception("Missing exchange rate(s): [${missingExchangeRates.joinToString(",")}]") {}