using static money_problem.Domain.Currency;

namespace money_problem.Domain
{
    public record Portfolio(params Money[] Moneys)
    {
        private static readonly Dictionary<string, double> ExchangeRates = new()
        {
            {KeyFor(EUR, USD), 1.2},
            {KeyFor(USD, KRW), 1100},
        };

        public Money Evaluate(Currency currency) =>
            new(Moneys.Aggregate(0d, (acc, money) => acc + Convert(money, currency)), currency);

        private static double Convert(Money money, Currency currency) =>
            currency == money.Currency
                ? money.Amount
                : money.Amount * ExchangeRates[KeyFor(money.Currency, currency)];

        private static string KeyFor(Currency from, Currency to) => $"{from}->{to}";
    }
}