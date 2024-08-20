package money_problem.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class MoneyTest {
    @Test
    @DisplayName("5 USD x 2 = 10 USD")
    void testMultiplication() {
        Dollar five = new Dollar(5);
        five.times(2);

        assertThat(five.amount).isEqualTo(10);
    }
}