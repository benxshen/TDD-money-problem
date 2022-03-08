package money_problem.domain;

import io.vavr.collection.HashMap;
import io.vavr.collection.List;
import io.vavr.collection.Map;
import lombok.SneakyThrows;

import static money_problem.domain.Currency.*;

public class Portfolio {
    private final List<Money> moneys;
    private final Map<String, Double> exchangeRates = HashMap.of(keyFor(EUR, USD), 1.2, keyFor(USD, KRW), 1100d);

    public Portfolio(Money... moneys) {
        this(List.of(moneys));
    }

    private Portfolio(List<Money> moneys) {
        this.moneys = moneys;
    }

    public Portfolio add(Money money) {
        return new Portfolio(moneys.append(money));
    }

    public Money evaluate(Currency currency) {
        return new Money(moneys.foldLeft(0d, (acc, money) -> acc + convert(money, currency)), currency);
    }

    private double convert(Money money, Currency toCurrency) {
        checkExchangeRates(toCurrency);

        return toCurrency == money.currency()
                ? money.amount()
                : money.amount() * exchangeRates.getOrElse(keyFor(money.currency(), toCurrency), 0d);
    }

    @SneakyThrows
    private void checkExchangeRates(Currency toCurrency) {
        var missingExchangeRates = moneys.map(Money::currency)
                .filter(c -> c != toCurrency)
                .distinct()
                .map(c -> keyFor(c, toCurrency))
                .filter(key -> !exchangeRates.containsKey(key));

        if (missingExchangeRates.nonEmpty())
            throw new MissingExchangeRatesException(missingExchangeRates);
    }

    private static String keyFor(Currency from, Currency to) {
        return from + "->" + to;
    }
}