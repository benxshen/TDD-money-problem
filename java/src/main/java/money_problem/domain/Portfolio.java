package money_problem.domain;

import io.vavr.collection.List;
import io.vavr.control.Either;

import static io.vavr.control.Either.left;
import static io.vavr.control.Either.right;

public class Portfolio {
    private final List<Money> moneys;

    public Portfolio(Money... moneys) {
        this(List.of(moneys));
    }

    private Portfolio(List<Money> moneys) {
        this.moneys = moneys;
    }

    public Portfolio add(Money money) {
        return new Portfolio(moneys.append(money));
    }

    public Either<String, Money> evaluate(Bank bank, Currency toCurrency) {
        var convertedMoneys = moneys.map(m -> bank.convert(m, toCurrency));
        var lefts = convertedMoneys.filter(Either::isLeft).map(Either::getLeft);

        return lefts.isEmpty()
                ? right(new Money
                (
                        convertedMoneys.map(e -> e.getOrElse(new Money(0, toCurrency)))
                                .map(Money::amount)
                                .sum().doubleValue(),
                        toCurrency
                ))
                : left(lefts.mkString("Missing exchange rate(s): [", ",", "]"));
    }
}