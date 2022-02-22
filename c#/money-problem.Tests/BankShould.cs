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
                .Should()
                .Be(12d.Dollars());
        }

        [Fact(DisplayName = "10 EUR -> EUR = 10 EUR")]
        public void ConvertMoneyInTheSameCurrency()
        {
            var bank = Bank.WithExchangeRate(EUR, USD, 1.2);

            bank.Convert(10d.Euros(), EUR)
                .Should()
                .Be(10d.Euros());
        }

        [Fact(DisplayName = "Throw missing exchange rate exception in case of missing exchange rates")]
        public void ConvertWithMissingExchangeRateShouldThrowException()
        {
            var portfolio = Bank.WithExchangeRate(EUR, USD, 1.2);

            portfolio.Invoking(p => p.Convert(10d.Euros(), KRW))
                .Should()
                .Throw<MissingExchangeRateException>()
                .WithMessage("EUR->KRW");
        }
    }
}