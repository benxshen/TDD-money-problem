package money_problem.domain

import money_problem.domain.Currency.*
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class MoneyTest {
    @Test
    fun `5 USD x 2 = 10 USD`() {
        val fiveDollars = Money(5.0, USD)
        assertThat(fiveDollars.times(2))
            .isEqualTo(Money(10.0, USD))
    }

    @Test
    fun `10 EUR x 2 = 20 EUR`() {
        val tenEuros = Money(10.0, EUR)
        assertThat(tenEuros.times(2))
            .isEqualTo(Money(20.0, EUR))
    }

    @Test
    fun `4002 KRW divided by 4 = 1000,5 KRW`() {
        val originalMoney = Money(4002.0, KRW)
        assertThat(originalMoney.divide(4))
            .isEqualTo(Money(1000.5, KRW))
    }
}