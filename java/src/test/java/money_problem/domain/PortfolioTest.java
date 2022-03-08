package money_problem.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static money_problem.domain.Currency.USD;
import static org.assertj.core.api.Assertions.assertThat;

class PortfolioTest {
    @Test
    @DisplayName("5 USD + 10 USD = 15 USD")
    void shouldAddMoney() {
        var fiveDollars = new Money(5, USD);
        var tenDollars = new Money(10, USD);

        var portfolio = new Portfolio(fiveDollars, tenDollars);
        assertThat(portfolio.evaluate(USD))
                .isEqualTo(new Money(15, USD));
    }
}