using FluentAssertions;
using money_problem.Domain;
using Xunit;

namespace money_problem.Tests;

public class PortfolioShould
{
    [Fact(DisplayName = "5 USD + 10 USD = 15 USD")]
    public void Add()
    {
        var fiveDollars = new Money(5, Currency.USD);
        var tenDollars = new Money(10, Currency.USD);

        var portfolio = new Portfolio(fiveDollars, tenDollars);
        portfolio.Evaluate(Currency.USD)
            .Should()
            .Be(new Money(15, Currency.USD));
    }
}