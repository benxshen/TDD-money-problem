package money_problem.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

/*
 * TODO: TDD 待辦清單
 *  - [ ] $5 + 10CHF = $10 (rate of 2:1)
 *  - [V] $5 * 2 = $10
 *  - [ ] Make “amount” private
 *  - [>] 幣別的 times() 讓 amount 倍數增長，這樣正確嗎?  Dollar side-effects?
 *  - [ ] 是否該考慮 Money rounding?
 */
class MoneyTest {
    @Test
    @DisplayName("5 USD x 2 = 10 USD")
    void testMultiplication() {
        Dollar five = new Dollar(5);
        five.times(2);

        assertThat(five.amount).isEqualTo(10);

        five.times(3);
        assertThat(five.amount).isEqualTo(15);  // 執行結果 expected: 15 but was: 30  => 第一次調用 times() 方法後，five 不再是五，而是變成了十。確實存在 side-effect 問題
    }
}
