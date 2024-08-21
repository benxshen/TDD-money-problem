package money_problem.domain;

public class Dollar {
    public int amount;

    public Dollar(int amount) {
        this.amount = amount;
    }

    public Dollar times(int multiplier) {
        return new Dollar(amount * multiplier);
    }

    @Override
    public boolean equals(Object object) {
        // TODO: 強制轉型，這樣好嗎? 可能會有問題吧? 還有，null 沒有判斷?  [Benx 2024-08-21 15:05:53]
        // Kent Beck: 沒有測試，就不要修改程式。先列入 TODO 工作項目
        Dollar dollar= (Dollar) object;
        return amount == dollar.amount;
    }
}
