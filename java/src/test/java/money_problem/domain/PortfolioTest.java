package money_problem.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static money_problem.domain.Currency.USD;
import static money_problem.domain.MoneyFactory.dollars;
import static money_problem.domain.MoneyFactory.euros;
import static org.assertj.core.api.Assertions.assertThat;

class PortfolioTest {
    @Test
    @DisplayName("5 USD + 10 USD = 15 USD")
    void shouldAddMoney() {
        assertThat(new Portfolio(dollars(5), dollars(10))
                .evaluate(USD))
                .isEqualTo(dollars(15));
    }

    @Test
    @DisplayName("5 USD + 10 EUR = 17 USD")
    void shouldAddMoneyInDollarsAndEuros() {
        assertThat(new Portfolio(dollars(5), euros(10))
                .evaluate(USD))
                .isEqualTo(dollars(17));
    }
}