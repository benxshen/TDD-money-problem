package money_problem.domain;

import io.vavr.collection.HashMap;
import io.vavr.collection.Map;
import io.vavr.control.Either;

import static io.vavr.control.Either.left;
import static io.vavr.control.Either.right;

public class Bank {
    private final Map<String, Double> exchangeRates;

    private Bank(Map<String, Double> exchangeRates) {
        this.exchangeRates = exchangeRates;
    }

    public static Bank withExchangeRate(Currency from, Currency to, double rate) {
        return new Bank(HashMap.empty())
                .addExchangeRate(from, to, rate);
    }

    public Bank addExchangeRate(Currency from, Currency to, double rate) {
        return new Bank(exchangeRates.put(keyFor(from, to), rate));
    }

    private static String keyFor(Currency from, Currency to) {
        return from + "->" + to;
    }

    public Either<String, Money> convert(Money money, Currency currency) {
        return canConvert(money.currency(), currency)
                ? convertSafely(money, currency)
                : left(keyFor(money.currency(), currency));
    }

    private Either<String, Money> convertSafely(Money money, Currency currency) {
        return right(
                currency == money.currency()
                        ? money
                        : new Money(money.amount() * exchangeRates.getOrElse(keyFor(money.currency(), currency), 0d), currency)
        );
    }

    private boolean canConvert(Currency from, Currency to) {
        return from == to || exchangeRates.containsKey(keyFor(from, to));
    }
}