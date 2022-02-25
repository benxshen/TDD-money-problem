using FluentAssertions;
using money_problem.Domain;
using Xunit;
using static money_problem.Domain.Currency;

namespace money_problem.Tests
{
    public class BankShould
    {
        private readonly Bank _bank = Bank.WithExchangeRate(EUR, USD, 1.2);

        [Fact(DisplayName = "10 EUR -> USD = 12 USD")]
        public void ConvertEuroToUsd()
        {
            _bank.Convert(10d.Euros(), USD)
                .RightUnsafe()
                .Should()
                .Be(12d.Dollars());
        }

        [Fact(DisplayName = "10 EUR -> EUR = 10 EUR")]
        public void ConvertMoneyInTheSameCurrency()
        {
            _bank.Convert(10d.Euros(), EUR)
                .RightUnsafe()
                .Should()
                .Be(10d.Euros());
        }

        [Fact(DisplayName = "Return a Left in case of missing exchange rates")]
        public void ConvertWithMissingExchangeRateShouldThrowException()
        {
            _bank.Convert(10d.Euros(), KRW)
                .LeftUnsafe()
                .Should()
                .Be("EUR->KRW");
        }

        [Fact(DisplayName = "Conversion with different exchange rates EUR -> USD")]
        public void ConvertWithDifferentExchangeRates()
        {
            _bank.Convert(10d.Euros(), USD)
                .RightUnsafe()
                .Should()
                .Be(12d.Dollars());

            _bank.AddExchangeRate(EUR, USD, 1.3)
                .Convert(10d.Euros(), USD)
                .RightUnsafe()
                .Should()
                .Be(13d.Dollars());
        }
    }
}