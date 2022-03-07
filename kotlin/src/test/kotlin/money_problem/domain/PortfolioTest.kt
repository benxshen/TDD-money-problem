package money_problem.domain

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
}