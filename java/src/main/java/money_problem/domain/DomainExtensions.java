package money_problem.domain;

import lombok.experimental.UtilityClass;

@UtilityClass
public class DomainExtensions {
    public static Money dollars(double amount) {
        return new Money(amount, Currency.USD);
    }

    public static Money euros(double amount) {
        return new Money(amount, Currency.EUR);
    }

    public static Money koreanWons(double amount) {
        return new Money(amount, Currency.KRW);
    }

    public static Portfolio portfolioFrom(Money money1, Money money2) {
        return new Portfolio(money1, money2);
    }
}