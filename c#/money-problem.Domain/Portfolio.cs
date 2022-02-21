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

        public Money Evaluate(Currency toCurrency)
        {
            CheckExchangeRates(toCurrency);
            return new Money(Moneys.Aggregate(0d, (acc, money) => acc + Convert(money, toCurrency)), toCurrency);
        }

        private void CheckExchangeRates(Currency toCurrency)
        {
            var missingExchangeRates =
                Moneys.Select(m => m.Currency)
                    .Where(c => c != toCurrency)
                    .Distinct()
                    .Select(c => KeyFor(c, toCurrency))
                    .Where(key => !ExchangeRates.ContainsKey(key))
                    .ToArray();

            if (missingExchangeRates.Any())
                throw new MissingExchangeRatesException(missingExchangeRates);
        }

        private static double Convert(Money money, Currency currency) =>
            currency == money.Currency
                ? money.Amount
                : money.Amount * ExchangeRates[KeyFor(money.Currency, currency)];

        private static string KeyFor(Currency from, Currency to) => $"{from}->{to}";
    }
}