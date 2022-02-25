using System;
using FluentAssertions;
using money_problem.Domain;
using Xunit;
using static money_problem.Domain.Types.Currency;

namespace money_problem.Tests
{
    public class PortfolioShould
    {
        private readonly Types.Bank _bank =
            Bank.WithExchangeRate(EUR, USD, 1.2)
                .AddExchangeRate(USD, KRW, 1100);

        [Fact(DisplayName = "5 USD + 10 USD = 15 USD")]
        public void Add()
        {
            var portfolio = 5d.Dollars().AddToPortfolio(10d.Dollars());
            portfolio.Evaluate(_bank, USD)
                .RightUnsafe()
                .Should()
                .Be(15d.Dollars());
        }

        [Fact(DisplayName = "5 USD + 10 EUR = 17 USD")]
        public void AddDollarsAndEuros()
        {
            var portfolio = 5d.Dollars().AddToPortfolio(10d.Euros());
            portfolio.Evaluate(_bank, USD)
                .RightUnsafe()
                .Should()
                .Be(17d.Dollars());
        }

        [Fact(DisplayName = "1 USD + 1100 KRW = 2200 KRW")]
        public void AddDollarsAndKoreanWons()
        {
            var portfolio = 1d.Dollars().AddToPortfolio(1100d.KoreanWons());
            portfolio.Evaluate(_bank, KRW)
                .RightUnsafe()
                .Should()
                .Be(2200d.KoreanWons());
        }

        [Fact(DisplayName = "Return a Left in case of missing exchange rates")]
        public void AddWithMissingExchangeRatesShouldReturnALeft()
        {
            var portfolio = 1d.Dollars()
                .AddToPortfolio(1d.Euros())
                .Add(1d.KoreanWons());

            portfolio.Evaluate(_bank, KRW)
                .LeftUnsafe()
                .Should()
                .Be("Missing exchange rate(s): [EUR->KRW]");
        }
    }
}