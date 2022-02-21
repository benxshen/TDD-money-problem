using FluentAssertions;
using Xunit;

namespace money_problem.Tests
{
    public class MoneyShould
    {
        [Fact(DisplayName = "5 USD x 2 = 10 USD")]
        public void MultiplicationInUsd()
        {
            var fiveDollars = new Money(5, Currency.USD);
            fiveDollars.Times(2)
                .Should()
                .Be(new Money(10, Currency.USD));
        }

        [Fact(DisplayName = "10 EUR x 2 = 20 EUR")]
        public void MultiplicationInEuro()
        {
            var tenEuros = new Money(10, Currency.EUR);
            tenEuros.Times(2)
                .Should()
                .Be(new Money(20, Currency.EUR));
        }

        [Fact(DisplayName = "4002 KRW / 4 = 1000.5 KRW")]
        public void Division()
        {
            var originalMoney = new Money(4002, Currency.KRW);
            originalMoney.Divide(4)
                .Should()
                .Be(new Money(1000.5, Currency.KRW));
        }
    }
}