using FluentAssertions;
using money_problem.Domain;
using Xunit;

namespace money_problem.Tests
{
    public class MoneyShould
    {
        [Fact(DisplayName = "10 EUR x 2 = 20 EUR")]
        public void Multiply()
        {
            var tenEuros = new Money(10, Currency.EUR);
            tenEuros.Times(2)
                .Should()
                .Be(new Money(20, Currency.EUR));
        }

        [Fact(DisplayName = "4002 KRW / 4 = 1000.5 KRW")]
        public void Divide()
        {
            var originalMoney = new Money(4002, Currency.KRW);
            originalMoney.Divide(4)
                .Should()
                .Be(new Money(1000.5, Currency.KRW));
        }
    }
}