package money_problem.domain

import money_problem.domain.Currency.*
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class PortfolioTest {
    private val bank = bankWithExchangeRate(EUR, USD, 1.2)
        .addExchangeRate(USD, KRW, 1_100.0)

    @Test
    fun `5 USD + 10 USD = 15 USD`() {
        assertThat(
            5.0.dollars()
                .addToPortfolio(10.0.dollars())
                .evaluate(bank, USD)
                .rightUnsafe()
        ).isEqualTo(15.0.dollars())
    }

    @Test
    fun `5 USD + 10 EUR = 17 USD`() {
        assertThat(
            5.0.dollars()
                .addToPortfolio(10.0.euros())
                .evaluate(bank, USD)
                .rightUnsafe()
        ).isEqualTo(17.0.dollars())
    }

    @Test
    fun `1 USD + 1100 KRW = 2200 KRW`() {
        assertThat(
            1.0.dollars()
                .addToPortfolio(1_100.0.koreanWons())
                .evaluate(bank, KRW)
                .rightUnsafe()
        ).isEqualTo(2_200.0.koreanWons())
    }

    @Test
    fun `Return a Left in case of missing exchange rates`() {
        assertThat(
            1.0.dollars()
                .addToPortfolio(1.0.euros())
                .add(1.0.koreanWons())
                .evaluate(bank, KRW)
                .leftUnsafe()
        ).isEqualTo("Missing exchange rate(s): [EUR->KRW]")
    }
}