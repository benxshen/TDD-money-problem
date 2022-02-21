namespace money_problem.Domain
{
    public record Portfolio(params Money[] Moneys)
    {
        public Money Evaluate(Currency currency) =>
            new(Moneys.Aggregate(0d, (acc, money) => acc + money.Amount), currency);
    }
}