package money_problem.domain;

import io.vavr.collection.List;

public record Portfolio(Money... moneys) {
    public Money evaluate(Currency currency) {
        return new Money(List.of(moneys)
                .foldLeft(0d, (acc, money) -> acc + money.amount()), currency);
    }
}