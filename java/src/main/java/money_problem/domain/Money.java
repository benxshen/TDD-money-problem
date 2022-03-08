package money_problem.domain;

public record Money(double amount, Currency currency) {
    public Money times(int multiplier) {
        return new Money(amount * multiplier, currency);
    }

    public Money divide(int divisor) {
        return new Money(amount / divisor, currency);
    }
}