namespace money_problem.Domain
{
    public class MissingExchangeRatesException : Exception
    {
        public MissingExchangeRatesException(string[] missingCurrencies)
            : base($"Missing exchange rate(s): [{string.Join(",", missingCurrencies)}]")
        {
        }
    }
}