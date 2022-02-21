using System.Linq;

namespace money_problem.Tests
{
    public record Portfolio(params Money[] Moneys)
    {
        public Money Evaluate(Currency currency) =>
            new(Moneys.Aggregate(0d, (acc, money) => acc + money.Amount), currency);
    }
}