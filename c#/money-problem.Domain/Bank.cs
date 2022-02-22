using System.Collections.Immutable;

namespace money_problem.Domain
{
    public record Bank
    {
        private readonly ImmutableDictionary<string, double> _exchangeRates;

        private Bank(ImmutableDictionary<string, double> exchangeRates) => _exchangeRates = exchangeRates;

        public static Bank WithExchangeRates(Currency from, Currency to, double rate) =>
            new Bank(new Dictionary<string, double>().ToImmutableDictionary())
                .AddExchangeRates(from, to, rate);

        private Bank AddExchangeRates(Currency from, Currency to, double rate) =>
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
            var key = KeyFor(from, to);
            if (!_exchangeRates.ContainsKey(key))
                throw new MissingExchangeRatesException(key);
        }
    }
}