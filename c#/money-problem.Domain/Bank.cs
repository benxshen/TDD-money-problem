using System.Collections.Immutable;

namespace money_problem.Domain
{
    public record Bank
    {
        private readonly ImmutableDictionary<string, double> _exchangeRates;

        private Bank(ImmutableDictionary<string, double> exchangeRates) => _exchangeRates = exchangeRates;

        public static Bank WithExchangeRate(Currency from, Currency to, double rate) =>
            new Bank(new Dictionary<string, double>().ToImmutableDictionary())
                .AddExchangeRate(from, to, rate);

        public Bank AddExchangeRate(Currency from, Currency to, double rate) =>
            new(_exchangeRates.Add(KeyFor(from, to), rate));

        private static string KeyFor(Currency from, Currency to) => $"{from}->{to}";

        public Money Convert(Money money, Currency currency)
        {
            CheckExchangeRates(money.Currency, currency);
            return currency == money.Currency
                ? money
                : new Money(money.Amount * _exchangeRates[KeyFor(money.Currency, currency)], currency);
        }

        private void CheckExchangeRates(Currency from, Currency to)
        {
            if (from == to) return;
            var key = KeyFor(from, to);
            if (!_exchangeRates.ContainsKey(key))
                throw new MissingExchangeRateException(key);
        }
    }
}