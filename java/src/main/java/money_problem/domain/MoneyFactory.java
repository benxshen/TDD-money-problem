package money_problem.domain;

import lombok.experimental.UtilityClass;

@UtilityClass
public class MoneyFactory {
    public static Money dollars(double amount) {
        return new Money(amount, Currency.USD);
    }

    public static Money euros(double amount) {
        return new Money(amount, Currency.EUR);
    }

    public static Money koreanWons(double amount) {
        return new Money(amount, Currency.KRW);
    }
}