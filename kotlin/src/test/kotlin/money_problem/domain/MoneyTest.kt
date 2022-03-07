package money_problem.domain

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class MoneyTest {
    @Test
    fun `5 USD x 2 = 10 USD`() {
        val fiver = Dollar(5)
        val tenner = fiver.times(2)

        assertThat(tenner.amount).isEqualTo(10)
    }
}