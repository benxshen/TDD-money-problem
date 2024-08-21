package money_problem.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

/*
 * TODO: TDD 待辦清單
 *  - [ ] $5 + 10CHF = $10 (rate of 2:1)
 *  - [V] $5 * 2 = $10
 *  - [ ] Make “amount” private
 *  - [V] 幣別的 times() 讓 amount 倍數增長，這樣正確嗎?  Dollar side-effects?
 *  - [ ] 是否該考慮 Money rounding?
 *  - [V] equals()
 *  - [ ] hashCode()
 *  - [ ] Equal null
 *  - [ ] Equal object
 */
class MoneyTest {
    @Test
    @DisplayName("5 USD x 2 = 10 USD")
    void testMultiplication() {
        Dollar five = new Dollar(5);
        Dollar product = five.times(2);

        assertThat(product.amount).isEqualTo(10);
        assertThat(five.amount).isEqualTo(5);

        product = five.times(3);
        assertThat(product.amount).isEqualTo(15);
    }

    @Test
    @DisplayName("Dollar equality: equals()")
    void testEquality() {
        assertThat(new Dollar(5)).isEqualTo(new Dollar(5));
        assertThat(new Dollar(5)).isNotEqualTo(new Dollar(6));
    }
}
