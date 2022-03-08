package money_problem.domain;

public record Dollar(int amount) {
    public Dollar times(int multiplier) {
        return new Dollar(amount * multiplier);
    }
}
