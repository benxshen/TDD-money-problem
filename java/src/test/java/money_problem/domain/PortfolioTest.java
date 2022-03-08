package money_problem.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static money_problem.domain.Currency.KRW;
import static money_problem.domain.Currency.USD;
import static money_problem.domain.DomainExtensions.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

class PortfolioTest {
    @Test
    @DisplayName("5 USD + 10 USD = 15 USD")
    void shouldAddMoney() {
        assertThat(portfolioFrom(dollars(5), dollars(10))
                .evaluate(USD))
                .isEqualTo(dollars(15));
    }

    @Test
    @DisplayName("5 USD + 10 EUR = 17 USD")
    void shouldAddMoneyInDollarsAndEuros() {
        assertThat(portfolioFrom(dollars(5), euros(10))
                .evaluate(USD))
                .isEqualTo(dollars(17));
    }

    @Test
    @DisplayName("1 USD + 1100 KRW = 2200 KRW")
    void shouldAddMoneyInDollarsAndKoreanWons() {
        assertThat(portfolioFrom(dollars(1), koreanWons(1100))
                .evaluate(KRW))
                .isEqualTo(koreanWons(2200));
    }

    @Test
    @DisplayName("Throw greedy exception in case of missing exchange rates")
    void shouldThrowAGreedyExceptionOnMissingExchangeRate() {
        assertThatExceptionOfType(MissingExchangeRatesException.class)
                .isThrownBy(() -> portfolioFrom(dollars(1), euros(1))
                        .add(koreanWons(1))
                        .evaluate(KRW))
                .withMessage("Missing exchange rate(s): [EUR->KRW]");
    }
}