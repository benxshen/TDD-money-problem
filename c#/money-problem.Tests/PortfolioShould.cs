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
}