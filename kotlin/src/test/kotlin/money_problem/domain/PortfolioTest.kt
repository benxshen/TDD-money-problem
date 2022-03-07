package money_problem.domain

import money_problem.domain.Currency.USD
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class PortfolioTest {
    @Test
    fun `5 USD + 10 USD = 15 USD`() {
        val fiveDollars = Money(5.0, USD)
        val tenDollars = Money(10.0, USD)

        val portfolio = Portfolio(listOf(fiveDollars, tenDollars))

        assertThat(portfolio.evaluate(USD))
            .isEqualTo(Money(15.0, USD))
    }
}