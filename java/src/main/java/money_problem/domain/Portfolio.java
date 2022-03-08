package money_problem.domain;

import io.vavr.collection.HashMap;
import io.vavr.collection.List;
import io.vavr.collection.Map;

import static money_problem.domain.Currency.*;

public class Portfolio {
    private final List<Money> moneys;
    private final Map<String, Double> exchangeRates = HashMap.of(keyFor(EUR, USD), 1.2, keyFor(USD, KRW), 1100d);

    public Portfolio(Money... moneys) {
        this.moneys = List.of(moneys);
    }

    public Money evaluate(Currency currency) {
        return new Money(moneys.foldLeft(0d, (acc, money) -> acc + convert(money, currency)), currency);
    }

    private double convert(Money money, Currency currency) {
        return currency == money.currency()
                ? money.amount()
                : money.amount() * exchangeRates.getOrElse(keyFor(money.currency(), currency), 0d);
    }

    private static String keyFor(Currency from, Currency to) {
        return from + "->" + to;
    }
}