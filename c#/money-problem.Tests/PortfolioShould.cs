using FluentAssertions;
using money_problem.Domain;
using Xunit;

namespace money_problem.Tests;

public class PortfolioShould
{
    [Fact(DisplayName = "5 USD + 10 USD = 15 USD")]
    public void Add()
    {
        var portfolio = new Portfolio(5d.Dollars(), 10d.Dollars());
        portfolio.Evaluate(Currency.USD)
            .Should()
            .Be(15d.Dollars());
    }

    [Fact(DisplayName = "5 USD + 10 EUR = 17 USD")]
    public void AddDollarsAndEuros()
    {
        var portfolio = new Portfolio(5d.Dollars(), 10d.Euros());
        portfolio.Evaluate(Currency.USD)
            .Should()
            .Be(17d.Dollars());
    }

    [Fact(DisplayName = "1 USD + 1100 KRW = 2200 KRW")]
    public void AddDollarsAndKoreanWons()
    {
        var portfolio = new Portfolio(1d.Dollars(), 1100d.KoreanWons());
        portfolio.Evaluate(Currency.KRW)
            .Should()
            .Be(2200d.KoreanWons());
    }

    [Fact(DisplayName = "Throw greedy exception in case of missing exchange rates")]
    public void AddWithMissingExchangeRatesShouldThrowGreedyException()
    {
        var portfolio = new Portfolio(1d.Dollars(), 1d.Euros(), 1d.KoreanWons());
        portfolio.Invoking(p => p.Evaluate(Currency.KRW))
            .Should()
            .Throw<MissingExchangeRatesException>()
            .WithMessage("Missing exchange rate(s): [EUR->KRW]");
    }
}