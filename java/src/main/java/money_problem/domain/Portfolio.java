package money_problem.domain;

import io.vavr.collection.List;

public record Portfolio(Money... moneys) {
    private static final double EURO_TO_USD = 1.2;

    public Money evaluate(Currency currency) {
        return new Money(List.of(moneys)
                .foldLeft(0d, (acc, money) -> acc + convert(money, currency)), currency);
    }

    private static double convert(Money money, Currency currency) {
        return currency == money.currency()
                ? money.amount()
                : money.amount() * EURO_TO_USD;
    }
}