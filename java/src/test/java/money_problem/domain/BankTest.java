package money_problem.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static money_problem.domain.Currency.*;
import static money_problem.domain.DomainExtensions.dollars;
import static money_problem.domain.DomainExtensions.euros;
import static org.assertj.core.api.Assertions.assertThat;

class BankTest {
    private final Bank bank = Bank.withExchangeRate(EUR, USD, 1.2);

    @Test
    @DisplayName("10 EUR -> USD = 12 USD")
    void shouldConvertEuroToUsd() {
        assertThat(bank.convert(euros(10), USD)
                .get())
                .isEqualTo(dollars(12));
    }

    @Test
    @DisplayName("10 EUR -> EUR = 10 EUR")
    void shouldConvertInSameCurrency() {
        assertThat(bank.convert(euros(10), EUR)
                .get())
                .isEqualTo(euros(10));
    }

    @Test
    @DisplayName("Return a left in case of missing exchange rate")
    void shouldReturnALeftOnMissingExchangeRate() {
        assertThat(bank.convert(euros(10), KRW)
                .getLeft())
                .isEqualTo("EUR->KRW");
    }

    @Test
    @DisplayName("Conversion with different exchange rates EUR to USD")
    void shouldConvertWithDifferentExchangeRates() {
        assertThat(bank.convert(euros(10), USD)
                .get())
                .isEqualTo(dollars(12));

        assertThat(bank.addExchangeRate(EUR, USD, 1.3)
                .convert(euros(10), USD)
                .get())
                .isEqualTo(dollars(13));
    }
}