namespace money_problem.Domain
{
    public record Portfolio(params Money[] Moneys)
    {
        public Money Evaluate(
            Bank bank,
            Currency toCurrency)
        {
            var failures = new List<MissingExchangeRateException>();
            var evaluatedPortfolio = new Money(
                Moneys.Aggregate(0d, (acc, money) =>
                {
                    var convertedMoney = 0d;

                    try
                    {
                        convertedMoney = bank.Convert(money, toCurrency).Amount;
                    }
                    catch (MissingExchangeRateException e)
                    {
                        failures.Add(e);
                    }

                    return acc + convertedMoney;
                }),
                toCurrency);

            return failures.Count == 0
                ? evaluatedPortfolio
                : throw new MissingExchangeRatesException(failures.Select(e => e.Message));
        }
    }
}