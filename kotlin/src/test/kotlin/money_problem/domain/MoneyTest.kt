package money_problem.domain

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class MoneyTest {
    @Test
    fun `10 EUR x 2 = 20 EUR`() {
        assertThat(
            10.0.euros()
                .times(2)
        ).isEqualTo(20.0.euros())
    }

    @Test
    fun `4002 KRW divided by 4 = 1000,5 KRW`() {
        assertThat(4002.0.koreanWons().divide(4))
            .isEqualTo(1000.5.koreanWons())
    }
}