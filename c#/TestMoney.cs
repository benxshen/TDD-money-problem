using FluentAssertions;
using Xunit;

namespace money_problem;

public class TestMoney
{
    [Fact]
    public void TestMultiplication()
    {
        var fiver = new Dollar(5);
        var tenner = fiver.Times(2);

        tenner.Amount.Should().Be(10);
    }
}

public record Dollar(int Amount)
{
    public Dollar Times(int multiplier) => new(Amount * multiplier);
}