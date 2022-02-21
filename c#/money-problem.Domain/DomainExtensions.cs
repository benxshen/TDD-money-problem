namespace money_problem.Domain;

public static class DomainExtensions
{
    public static Money Dollars(this double amount) => new(amount, Currency.USD);
    public static Money Euros(this double amount) => new(amount, Currency.EUR);
    public static Money KoreanWons(this double amount) => new(amount, Currency.KRW);
}