package money_problem.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class MoneyTest {
    @Test
    @DisplayName("5 USD x 2 = 10 USD")
    void shouldMultiply() {
        Dollar fiver = new Dollar(5);
        Dollar tenner = fiver.times(2);

        assertThat(tenner.amount()).isEqualTo(10);
    }
}