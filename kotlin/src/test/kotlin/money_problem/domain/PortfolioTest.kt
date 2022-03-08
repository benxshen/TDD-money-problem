package money_problem.domain

import money_problem.domain.Currency.KRW
import money_problem.domain.Currency.USD
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatExceptionOfType
import org.junit.jupiter.api.Test

class PortfolioTest {
    @Test
    fun `5 USD + 10 USD = 15 USD`() {
        assertThat(
            5.0.dollars()
                .addToPortfolio(10.0.dollars())
                .evaluate(USD)
        ).isEqualTo(15.0.dollars())
    }

    @Test
    fun `5 USD + 10 EUR = 17 USD`() {
        assertThat(
            5.0.dollars()
                .addToPortfolio(10.0.euros())
                .evaluate(USD)
        ).isEqualTo(17.0.dollars())
    }

    @Test
    fun `1 USD + 1100 KRW = 2200 KRW`() {
        assertThat(
            1.0.dollars()
                .addToPortfolio(1_100.0.koreanWons())
                .evaluate(KRW)
        ).isEqualTo(2_200.0.koreanWons())
    }

    @Test
    fun `Throw greedy exception in case of missing exchange rates`() {
        assertThatExceptionOfType(MissingExchangeRatesException::class.java)
            .isThrownBy {
                1.0.dollars()
                    .addToPortfolio(1.0.euros())
                    .add(1.0.koreanWons())
                    .evaluate(KRW)
            }.withMessage("Missing exchange rate(s): [EUR->KRW]")
    }
}