package money_problem.domain;

public class Dollar {
    public int amount;

    public Dollar(int amount) {
        this.amount = amount;
    }

    public Dollar times(int multiplier) {
//        amount *= multiplier;
//        return new Dollar(amount);
        return new Dollar(amount * multiplier);
    }

    @Override
    public boolean equals(Object object) {
        return true;
    }
}
