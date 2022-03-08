package money_problem.domain

import money_problem.domain.Currency.KRW
import money_problem.domain.Currency.USD
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class PortfolioTest {
    @Test
    fun `5 USD + 10 USD = 15 USD`() {
        val portfolio = Portfolio(listOf(5.0.dollars(), 10.0.dollars()))

        assertThat(portfolio.evaluate(USD))
            .isEqualTo(15.0.dollars())
    }

    @Test
    fun `5 USD + 10 EUR = 17 USD`() {
        val portfolio = Portfolio(listOf(5.0.dollars(), 10.0.euros()))

        assertThat(portfolio.evaluate(USD))
            .isEqualTo(17.0.dollars())
    }

    @Test
    fun `1 USD + 1100 KRW = 2200 KRW`() {
        val portfolio = Portfolio(listOf(1.0.dollars(), 1_100.0.koreanWons()))

        assertThat(portfolio.evaluate(KRW))
            .isEqualTo(2_200.0.koreanWons())
    }
}