package money_problem.domain;

import io.vavr.collection.List;

public class MissingExchangeRatesException extends Exception {
    public MissingExchangeRatesException(List<String> missingExchangeRates) {
        super(missingExchangeRates.mkString("Missing exchange rate(s): [", ",", "]"));
    }
}
