package money_problem.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static money_problem.domain.Currency.*;
import static money_problem.domain.DomainExtensions.*;
import static org.assertj.core.api.Assertions.assertThat;

class PortfolioTest {
    private final Bank bank = Bank.withExchangeRate(EUR, USD, 1.2)
            .addExchangeRate(USD, KRW, 1100d);

    @Test
    @DisplayName("5 USD + 10 USD = 15 USD")
    void shouldAddMoney() {
        assertThat(portfolioFrom(dollars(5), dollars(10))
                .evaluate(bank, USD)
                .get())
                .isEqualTo(dollars(15));
    }

    @Test
    @DisplayName("5 USD + 10 EUR = 17 USD")
    void shouldAddMoneyInDollarsAndEuros() {
        assertThat(portfolioFrom(dollars(5), euros(10))
                .evaluate(bank, USD)
                .get())
                .isEqualTo(dollars(17));
    }

    @Test
    @DisplayName("1 USD + 1100 KRW = 2200 KRW")
    void shouldAddMoneyInDollarsAndKoreanWons() {
        assertThat(portfolioFrom(dollars(1), koreanWons(1100))
                .evaluate(bank, KRW)
                .get())
                .isEqualTo(koreanWons(2200));
    }

    @Test
    @DisplayName("return a left when missing exchange rates")
    void shouldReturnALeftOnMissingExchangeRate() {
        assertThat(portfolioFrom(dollars(1), euros(1))
                .add(koreanWons(1))
                .evaluate(bank, KRW)
                .getLeft())
                .isEqualTo("Missing exchange rate(s): [EUR->KRW]");
    }
}