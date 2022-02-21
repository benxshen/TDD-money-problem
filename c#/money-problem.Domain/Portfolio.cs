namespace money_problem.Domain
{
    public record Portfolio(params Money[] Moneys)
    {
        private const double EuroToUsd = 1.2;

        public Money Evaluate(Currency currency) =>
            new(Moneys.Aggregate(0d, (acc, money) => acc + Convert(money, currency)), currency);

        private static double Convert(Money money, Currency currency) =>
            currency == money.Currency
                ? money.Amount
                : money.Amount * EuroToUsd;
    }
}