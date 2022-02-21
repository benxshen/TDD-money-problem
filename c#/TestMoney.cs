using System.Linq;
using FluentAssertions;
using Xunit;

namespace money_problem
{
    public class TestMoney
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

    public class TestPortfolio
    {
        [Fact(DisplayName = "5 USD + 10 USD = 15 USD")]
        public void Addition()
        {
            var fiveDollars = new Money(5, Currency.USD);
            var tenDollars = new Money(10, Currency.USD);

            var portfolio = new Portfolio(fiveDollars, tenDollars);
            portfolio.Evaluate(Currency.USD)
                .Should()
                .Be(new Money(15, Currency.USD));
        }
    }

    public record Portfolio(params Money[] Moneys)
    {
        public Money Evaluate(Currency currency) =>
            new(Moneys.Aggregate(0d, (acc, money) => acc + money.Amount), currency);
    }

    public record Money(double Amount, Currency Currency)
    {
        public Money Times(int multiplier) => this with {Amount = Amount * multiplier};

        public Money Divide(int divisor) => this with {Amount = Amount / divisor};
        public override string ToString() => $"{Amount} {Currency}";
    }

    public enum Currency
    {
        EUR,
        USD,
        KRW
    }
}