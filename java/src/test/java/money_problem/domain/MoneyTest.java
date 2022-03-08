package money_problem.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static money_problem.domain.Currency.EUR;
import static money_problem.domain.Currency.KRW;
import static org.assertj.core.api.Assertions.assertThat;

class MoneyTest {
    @Test
    @DisplayName("10 EUR x 2 = 20 EUR")
    void shouldMultiplyInEuros() {
        var fiveEuros = new Money(10, EUR);
        var twentyEuros = fiveEuros.times(2);

        assertThat(twentyEuros)
                .isEqualTo(new Money(20, EUR));
    }

    @Test
    @DisplayName("4002 KRW / 4 = 1000.5 KRW")
    void shouldDivideInKoreanWons() {
        var originalMoney = new Money(4002, KRW);
        var actualMoneyAfterDivision = originalMoney.divide(4);

        assertThat(actualMoneyAfterDivision)
                .isEqualTo(new Money(1000.5, KRW));
    }
}