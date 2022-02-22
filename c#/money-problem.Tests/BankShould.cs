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
            var bank = Bank.WithExchangeRates(EUR, USD, 1.2);

            bank.Convert(10d.Euros(), USD)
                .Should()
                .Be(12d.Dollars());
        }

        [Fact(DisplayName = "Throw missing exchange rates exception in case of missing exchange rates")]
        public void ConvertWithMissingExchangeRatesShouldThrowException()
        {
            var portfolio = Bank.WithExchangeRates(EUR, USD, 1.2);

            portfolio.Invoking(p => p.Convert(10d.Euros(), KRW))
                .Should()
                .Throw<MissingExchangeRatesException>()
                .WithMessage("Missing exchange rate(s): [EUR->KRW]");
        }
    }
}