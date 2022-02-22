using FluentAssertions;
using money_problem.Domain;
using Xunit;
using static money_problem.Domain.Currency;

namespace money_problem.Tests
{
    public class BankShould
    {
        [Fact(DisplayName = "10 EUR -> USD = 12 USD")]
        public void ConvertEuroToUsd()
        {
            var bank = Bank.WithExchangeRate(EUR, USD, 1.2);

            bank.Convert(10d.Euros(), USD)
                .RightUnsafe()
                .Should()
                .Be(12d.Dollars());
        }

        [Fact(DisplayName = "10 EUR -> EUR = 10 EUR")]
        public void ConvertMoneyInTheSameCurrency()
        {
            var bank = Bank.WithExchangeRate(EUR, USD, 1.2);

            bank.Convert(10d.Euros(), EUR)
                .RightUnsafe()
                .Should()
                .Be(10d.Euros());
        }

        [Fact(DisplayName = "Return a Left in case of missing exchange rates")]
        public void ConvertWithMissingExchangeRateShouldThrowException()
        {
            var bank = Bank.WithExchangeRate(EUR, USD, 1.2);

            bank.Convert(10d.Euros(), KRW)
                .LeftUnsafe()
                .Should()
                .Be("EUR->KRW");
        }
    }
}