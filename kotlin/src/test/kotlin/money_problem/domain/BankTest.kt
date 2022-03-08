package money_problem.domain

import money_problem.domain.Currency.*
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class BankTest {
    private val bank = bankWithExchangeRate(EUR, USD, 1.2)

    @Test
    fun `10 EUR to USD = 12 USD`() {
        assertThat(
            bank.convert(10.0.euros(), USD)
                .rightUnsafe()
        ).isEqualTo(12.0.dollars())
    }

    @Test
    fun `10 EUR to EUR = 10 EUR`() {
        assertThat(
            bank.convert(10.0.euros(), EUR)
                .rightUnsafe()
        ).isEqualTo(10.0.euros())
    }

    @Test
    fun `Return a Left in case of missing exchange rates`() {
        assertThat(
            bank.convert(10.0.euros(), KRW)
                .leftUnsafe()
        ).isEqualTo("EUR->KRW")
    }

    @Test
    fun `Conversion with different exchange rates EUR to USD`() {
        assertThat(
            bank.convert(10.0.euros(), USD)
                .rightUnsafe()
        ).isEqualTo(12.0.dollars())

        assertThat(
            bank.addExchangeRate(EUR, USD, 1.3)
                .convert(10.0.euros(), USD)
                .rightUnsafe()
        ).isEqualTo(13.0.dollars())

    }
}