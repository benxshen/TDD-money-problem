# Learning Test-Driven Development (TDD)
* Workshop to learn TDD step-by-step from [Saleem Siddiqui](https://www.linkedin.com/in/ssiddiqui/) 's book : 
  * [Learning Test-Driven Development](https://www.oreilly.com/library/view/learning-test-driven-development/9781098106461/)
    * The book explains the different concepts in 3 languages : Go, Python, JS
    * Thanks Saleem for your great sharing
* You will find other languages in this repository : `C#`, `scala`
  * I have chosen to make a step-by-step guide from the book in `C#`
  
[![img/Learning-Test-Driven-Development.jpg](img/Learning-Test-Driven-Development.jpg)](https://www.oreilly.com/library/view/learning-test-driven-development/9781098106461/)

## What is TDD
TDD is a technique borne of a set of beliefs about code :  

* Simplicity - the art of maximizing the amount of work *not* done
* Obviousness and clarity are more virtuous than cleverness
* Writing uncluttered code is a key component of being successful

`Test-Driven Development is a way of managing fear during programming - Kant Beck`

### Designing and structuring code
* TDD is not fundamentally about testing code
* Its purpose : `improve the design and structure of the code`
	* The Unit Tests that we end up with are an added bonus
	* Primary benefit : simplicity of design we get

### A bias toward simplicity
* In software we can measure simplicity :
	* Fewer lines of code per feature
	* Lower Cyclomatic Complexity
	* Fewer side effects
	* Smaller runtime / memory requirements
* TDD forces us to craft the simplest thing that works
* Virtue isn't mystical :
	* Using TDD won't cut by half :
		* your development time
		* the lines of code
		* defect count
	* It will allow you to arrest the temptation to introduce artificial / contrived complexity

### Increased Confidence
TDD increases our confidence in our code :

* Each new test flexes the system in new and previously untested ways
* Over time : the tests suite guards us against regression failures

## Part 1 - Getting Started
### Chapter 1 - The money problem
#### Building block of TDD
A 3-phase process :

* ***Red*** : We write a failing test
	* Including possible compilation failures
	* We run the test suite to verify the failing test
* ***Green*** : We write **just enough production code** to make the test green
	* We run the test suite to verify this
* ***Refactor*** : We remove any code smells
	* Duplication, hardcoded values, improper use of language idioms, ...
	* If we break any test during this phase :
		* Prioritize getting back to green before exiting this phase

![TDD phases](img/tdd.png)

### What's the problem ?
We have  to builkd a spreadsheet to manage money in more than one currency : perhaps to manage a stock portfolio ?

| Stock | Stock exchange | Shares | Share Price | Total |
|---|---|---|---|---|
| IBM | NASDAQ | 100 | 124 USD | 12400 USD |
| BMW | DAX | 400 | 75 EUR | 30000 EUR |
| Samsung | KSE | 300 | 68000 KRW | 20400000 KRW |

To build it, we'd need to do simple arithmetic operations on numbers :

```text
5 USD x 2 = 10 USD
4002 KRW / 4 = 1000.5 KRW

// convert
5 USD + 10 EUR = 17 USD
1 USD + 1100 KRW = 2200 KRW
```

List of Features to implement :

```text
5 USD x 2 = 10 USD
10 EUR x 2 = 20 EUR
4002 KRW / 4 = 1000.5 KRW
5 USD + 10 EUR = 17 USD
1 USD + 1100 KRW = 2200 KRW
```

### Our first failing test
* Create a new dotnet project called `money-problem` :

```shell
dotnet new xunit -n "money-problem"
dotnet test
```

* Write our first test for
  * `5 USD x 2 = 10 USD`
  * This code does not compile
  * Congrats here is our failing test

```c#
public class TestMoney
{
    [Fact]
    public void TestMultiplication()
    {
        var fiver = new Dollar(5);
        var tenner = fiver.Times(2);

        tenner.Amount.Should().Be(10);
    }
}
```

### Going for Green
* We wrote our tests as we would expect them to work
* Start with the smallest bit of code that sets up on the path to progress
  * Add an abstraction `Dollar`
  * Use IDE for that

```c#
public record Dollar(int Amount)
{
    public Dollar Times(int multiplier) => new(10);
}
```

### Cleaning Up
* Just enough code to make the test pass
* Remove hardcoded values

```c#
public record Dollar(int Amount)
{
    public Dollar Times(int multiplier) => new(Amount * multiplier);
}
```

### Chapter 2 - Multi-currency Money
* Second item in our feature list : `10 EUR x 2 = 20 EUR`
* We need a more general concept than `Dollar`
    * Money for example 

#### Let's write our new test
```c#
[Fact]
public void TestMultiplicationInEuro()
{
    var tenEuros = new Money(10, "EUR");
    var twentyEuros = tenEuros.Times(2);

    twentyEuros.Amount.Should().Be(20);
    twentyEuros.Currency.Should().Be("EUR");
}
```

#### Make it green
```c#
public record Money(int Amount, string Currency)
{
    public Money Times(int multiplier) => this with { Amount = Amount * multiplier };
}
```

#### DRY - Remove duplication
* We now have 2 classes that could be simplified

```c#
public record Money(int Amount, string Currency)
{
    public Money Times(int multiplier) => this with { Amount = Amount * multiplier };
}

public record Dollar(int Amount)
{
    public Dollar Times(int multiplier) => new(Amount * multiplier);
}
```

* What else ?
  * We repeat "EUR" in our tests
  * Create a struct representing the supported currencies

```c#
namespace money_problem
{
    public class TestMoney
    {
        [Fact]
        public void MultiplicationInUsd()
        {
            var fiver = new Money(5, Currency.USD);
            var tenner = fiver.Times(2);

            tenner.Amount.Should().Be(10);
            tenner.Currency.Should().Be(Currency.USD);
        }
    
        [Fact]
        public void MultiplicationInEuro()
        {
            var tenEuros = new Money(10, Currency.EUR);
            var twentyEuros = tenEuros.Times(2);

            twentyEuros.Amount.Should().Be(20);
            twentyEuros.Currency.Should().Be(Currency.EUR);
        }
    }

    public record Money(int Amount, Currency Currency)
    {
        public Money Times(int multiplier) => this with { Amount = Amount * multiplier };
    }

    public enum Currency { EUR , USD }
}
```

#### Divide and Conquer
* Allow division on `Money` : `4002 KRW / 4 = 1000.5 KRW`
* Write the test

```c#
[Fact(DisplayName = "4002 KRW / 4 = 1000.5 KRW")]
public void Division()
{
    var originalMoney = new Money(4002, Currency.KRW);
    var actualMoneyAfterDivision = originalMoney.Divide(4);

    actualMoneyAfterDivision.Should().Be(new Money(1000.5, Currency.KRW));
}
```

#### Make it green
* We need to change the type of Amount -> in double now
* We must add a new Currency

```c#
public record Money(double Amount, Currency Currency)
{
    public Money Times(int multiplier) => this with { Amount = Amount * multiplier };
    public Money Divide(int divisor) => this with {Amount = Amount / divisor};
}

public enum Currency { EUR , USD, KRW }
```

* Using `DisplayName` in the `Fact Attribute` allows us to have this in our test results
* ![Display name of your tests](img/displayName.png)

#### Cleaning Up
* Remove duplication
  * We assert always the same stuff

```c#
[Fact(DisplayName = "5 USD x 2 = 10 USD")]
public void MultiplicationInUsd()
{
    var fiver = new Money(5, Currency.USD);
    var tenner = fiver.Times(2);

    tenner.Amount.Should().Be(10);
    tenner.Currency.Should().Be(Currency.USD);
}

[Fact(DisplayName = "10 EUR x 2 = 20 EUR")]
public void MultiplicationInEuro()
{
    var tenEuros = new Money(10, Currency.EUR);
    var twentyEuros = tenEuros.Times(2);

    twentyEuros.Amount.Should().Be(20);
    twentyEuros.Currency.Should().Be(Currency.EUR);
}

[Fact(DisplayName = "4002 KRW / 4 = 1000.5 KRW")]
public void Division()
{
    var originalMoney = new Money(4002, Currency.KRW);
    var actualMoneyAfterDivision = originalMoney.Divide(4);

    actualMoneyAfterDivision.Amount.Should().Be(1000.5);
    actualMoneyAfterDivision.Currency.Should().Be(Currency.KRW);
}
```

* We can use record value equality to simplify assertions

```c#
[Fact(DisplayName = "5 USD x 2 = 10 USD")]
public void MultiplicationInUsd()
{
    var fiveDollars = new Money(5, Currency.USD);
    fiveDollars.Times(2)
        .Should()
        .Be(new Money(10, Currency.USD));
}

[Fact(DisplayName = "10 EUR x 2 = 20 EUR")]
public void MultiplicationInEuro()
{
    var tenEuros = new Money(10, Currency.EUR);
    tenEuros.Times(2)
        .Should()
        .Be(new Money(20, Currency.EUR));
}

[Fact(DisplayName = "4002 KRW / 4 = 1000.5 KRW")]
public void Division()
{
    var originalMoney = new Money(4002, Currency.KRW);
    originalMoney.Divide(4)
        .Should()
        .Be(new Money(1000.5, Currency.KRW));
}
```

#### Where we are ?
* We built a second feature : `division`
* We changed our design to deal with numbers with fractions
* We have a couple of passing tests
* We have introduced a `Money` entity to deal with various currencies
* We cleaned our code along the way

```text
✅ 5 USD x 2 = 10 USD 
✅ 10 EUR x 2 = 20 EUR
✅ 4002 KRW / 4 = 1000.5 KRW
5 USD + 10 EUR = 17 USD
1 USD + 1100 KRW = 2200 KRW
```

### Chapter 3 - Portfolio
* We can multiply and divide amounts in any one currency by numbers
* Now we need to add amounts in multiple currencies

#### Designing our Next Test
`5 USD + 10 EUR = 17 USD`  
* TDD plays nicely with software design
  * TDD gives us an opportunity to pause after each RGR cycle and design our code intentionally
* We realize with this feature that "adding dollars to dollars results in dollars" is an oversimplification
* Adding `Money` entities in different currencies gives us a `Portfolio
  * That can be expressed in any one currency
* We just introduced a new concept
  * Let's reflect this reality in our domain model

* Let's start with a test to add 2 `Money` entities in the same currency : `5 USD + 10 USD = 15 USD`

* Create our first `Portfolio` test :
```c#
[Fact(DisplayName = "5 USD + 10 USD = 15 USD")]
public void Addition()
{
    var fiveDollars = new Money(5, Currency.USD);
    var tenDollars = new Money(10, Currency.USD);
    
    // Declare an empty Portfolio
    var portfolio = new Portfolio();
    // Add multiple Money in it
    portfolio.Add(fiveDollars, tenDollars);
    // Evaluate the Portfolio in a given currency
    portfolio.Evaluate(Currency.USD)
        .Should()
        .Be(new Money(15, Currency.USD));
}
```

* Use our IDE to implement missing methods amd make our test green

```c#
public class Portfolio
{
    private readonly List<Money> _moneys = new();
    public void Add(params Money[] moneys) => _moneys.AddRange(moneys);
    public Money Evaluate(Currency currency) => new(15, Currency.USD);
}
```

* Refactor
  * Where is the duplication / code smells ?
  * Let's work on the hardcode values : 15 / USD
    * We can simply sums up moneys

```c#
public class Portfolio
{
    private readonly List<Money> _moneys = new();
    public void Add(params Money[] moneys) => _moneys.AddRange(moneys);
    public Money Evaluate(Currency currency) =>
        new(_moneys.Aggregate(0d, (acc, money) => acc + money.Amount), currency);
}
```

* Do we really need to have a state in Portfolio ?
  * Let's refactor it to only have Pure functions and immutable data structure
  * It's a design choice

```c#
[Fact(DisplayName = "5 USD + 10 USD = 15 USD")]
public void Addition()
{
    var fiveDollars = new Money(5, Currency.USD);
    var tenDollars = new Money(10, Currency.USD);

    var portfolio = new Portfolio(fiveDollars, tenDollars);
    portfolio.Evaluate(Currency.USD)
        .Should()
        .Be(new Money(15, Currency.USD));
}

public record Portfolio(params Money[] Moneys)
{
    public Money Evaluate(Currency currency) =>
        new(Moneys.Aggregate(0d, (acc, money) => acc + money.Amount), currency);
}
```

#### Where we are ?
* We started to tackle the problem of adding different representations of `Money`
  * This requires introduction of exchange rates
* We used a divide-and-conquer strategy to 
  * Add 2 `Money entities` (through constructor here)
  * And evaluate in the same currency
* We can notice that our code is growing
  * We need to restructure it : separate our tests from production code

```text
✅ 5 USD x 2 = 10 USD 
✅ 10 EUR x 2 = 20 EUR
✅ 4002 KRW / 4 = 1000.5 KRW
✅ 5 USD + 10 USD = 15 USD
5 USD + 10 EUR = 17 USD
1 USD + 1100 KRW = 2200 KRW
```

## Part 2 - Modularization
### Chapter 4 - Separation of Concerns
* Our source code has grown
* Let's spend some time organizing it

#### Test and Production Code
We have written 2 types of code :
1. Code that `solves` our `Money` problem
  * Including `Money` / `Portfolio`
  * We call this `production code`
2. Code that `verifies the problem is correctly solved`
   * Including all the tests and the code needed to support these tests
   * We call this `test code`

`Test code depends on production code, however there should be no dependency in the other direction`

#### Packaging and Deployment
`Test code should be packaged separately from production code so that they can be deployed independently via CI/CD pipeline`
* Modularization
  * Let's separate the test code from the production code
* This means :
  * Test and production code should be in separate files
    * Allows to read / edit / focus on test or production code independently
  * The code should use namespaces to clearly identify which entities belong together
    * A namespace may be called a "module" or "package'
  * Add explicit `import` in our test code

#### Removing redundancy
* We have had 2 multiplication tests
  * They test the same functionality
  * In contrast we have only one test for division
  * `Should we keep both the multiplication tests ?`

#### Checklist for cleaning tests :
- [ ] Would we have the same code coverage if we delete a test ?
- [ ] Does one of the tests verify a significant edge case ?
- [ ] Do the different tests provide unique value as a `living documentation` ?

#### Update our feature list
```text
✅ 5 USD x 2 = 10 USD 
✅ 10 EUR x 2 = 20 EUR
✅ 4002 KRW / 4 = 1000.5 KRW
✅ 5 USD + 10 USD = 15 USD
Separate test code from production code
Remove redundant tests
5 USD + 10 EUR = 17 USD
1 USD + 1100 KRW = 2200 KRW
```

The steps of separation of concerns vary from language to language. Saleem has dedicated 3 chapters in his boook on this topic :
* Chapter 5, "Packages and Modules in Go"
* Chapter 6, "Modules in Javascript"
* Chapter 7, "Modules in Python"

I propose here how I would do that in C#.

### Chapter "custom" Projects and namespaces in C#
#### Splitting Our Code into Classes
* Start our separation of concerns by splitting our file into distinct Classes
```
money-problem
│─── Currency.cs
│─── Money.cs    
│─── Portfolio.cs
│─── MoneyShould.cs
│─── PortfolioShould.cs
```

#### Separate Production code and Test Code
* Let's isolate our Test code by creating a new Project for `production code`
* To do it, we have some prerequisites :
  * Create an empty solution `money-problem`
  * Create a folder for `money-problem.Tests`
    * Move actual files in this folder
  * Add the existing project `money-problem.Tests` to the solution
* Create a new project (`Class Library`)
  * Name it `money-problem.Domain`
  * Move `Domain entities` to it
  * Our tests are now `Red`
    * Add dependency to the `Domain` project in the `Tests` project
    * Dependency is unidirectional so we can not reference test objects from our `production code`

```
money-problem   
│─── money-problem.Domain
│        │─── Currency.cs
│        │─── Money.cs    
│        │─── Portfolio.cs
│─── money-problem.Tests
│        │─── MoneyShould.cs
│        │─── PortfolioShould.cs
```

#### Fix our tests
* Our tests are not compiling anymore
* We need to fix `usings` in our tests : 
```c#
using money_problem.Domain;
```

#### Remove Redundancy in Tests
* We have 2 tests on multiplication
  * The 2 tests test the same functionality and does not provide any added-value according to our check-list :

- [X] Would we have the same code coverage if we delete a tests ?
- [ ] Does one of the tests verify a significant edge case ?
- [ ] Do the different tests provide unique value as a `living documentation` ?

* Delete the `MultiplyInDollars`
  * Rename the tests to represent the features under tests :
  * Add / Divide / Multiply

#### Update our feature list
```text
✅ 5 USD x 2 = 10 USD 
✅ 10 EUR x 2 = 20 EUR
✅ 4002 KRW / 4 = 1000.5 KRW
✅ 5 USD + 10 USD = 15 USD
✅ Separate test code from production code
✅ Remove redundant tests
5 USD + 10 EUR = 17 USD
1 USD + 1100 KRW = 2200 KRW
```

## Part 3 - Features and Redesign
### Chapter 8 - Evaluating a Portfolio
`Money itself isn't lost or made. It's simply transfered from one perception to another. Like magic. - Gordon Gekko, Wall Street`

#### Mixing Money
Heterogeneous combination of currencies demands that we create a new abstraction : conversion of money from one currency to another :
```text
- Conversion always relates a pair of currencies
- Conversion is from one currency to another with a well-defined exchange rate
- The two exchange rates between a pair of currencies may or may not be arithmetical reciprocals of each other
    - Exchange Rate from EUR to USD may or may not be the mathematical reciprocal of USD to EUR
- It is possible for a currency to have no defined exchange rate to another currency
    - Inconvertible currencies : economical, political, ... reasons    
```

* Add our next test :

```c#
[Fact(DisplayName = "5 USD + 10 EUR = 17 USD")]
public void AddDollarsAndEuros()
{
    var fiveDollars = new Money(5, Currency.USD);
    var tenEuros = new Money(10, Currency.EUR);

    var portfolio = new Portfolio(fiveDollars, tenEuros);
    portfolio.Evaluate(Currency.USD)
        .Should()
        .Be(new Money(17, Currency.USD));
}
```

* We need an exchange rate from EUR to USD
  * We hardcode it for now (with a const)
* We add a `Convert` method :

```c#
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
```

#### Remove redundancy
* A cool feature of C# is the ability to declare and use extension methods on primitive types
* In our tests we instantiate a lot of `Money` objects
    * Let's instantiate them in a more fluent way thanks to an extension method on `double`
        * Ex : `2.Dollars()`
    * It allows us to create true business DSL

```c#
public static class DomainExtensions
{
    public static Money Dollars(this double amount) => new(amount, Currency.USD);
    public static Money Euros(this double amount) => new(amount, Currency.EUR);
    public static Money KoreanWons(this double amount) => new(amount, Currency.KRW);
}
```

* Let's use it in our tests

```c#
[Fact(DisplayName = "5 USD + 10 EUR = 17 USD")]
public void AddDollarsAndEuros()
{
    var portfolio = new Portfolio(5d.Dollars(), 10d.Euros());
    portfolio.Evaluate(Currency.USD)
        .Should()
        .Be(17d.Dollars());
}
```

#### Where we are
```text
✅ 5 USD x 2 = 10 USD 
✅ 10 EUR x 2 = 20 EUR
✅ 4002 KRW / 4 = 1000.5 KRW
✅ 5 USD + 10 USD = 15 USD
✅ Separate test code from production code
✅ Remove redundant tests
✅ 5 USD + 10 EUR = 17 USD
1 USD + 1100 KRW = 2200 KRW
Determine exchange rate based on the currencies involved (from -> to)
Allow exchange rates to be modified
```

### Chapter 9 - Currencies, Currencies, Everywhere
```text
1 USD + 1100 KRW = 2200 KRW
Determine exchange rate based on the currencies involved (from -> to)
```

* We can use this table to determine exchange rates :

| From | To   | Rate    |
|------|------|---------|
| EUR  | USD  | 1.2     |
| USD  | EUR  | 0.82    |
| USD  | KRW  | 1100    |
| KRW  | EUR  | 0.0009  |
| EUR  | KRW  | 1344    |
| KRW  | EUR  | 0.00073 |

* Let's write our next test :
  * It fails with this message : `Expected portfolio.Evaluate(Currency.KRW) to be 2200 KRW, but found 1101.2 KRW.`
    * It takes the EUR to USD change rate
```c#
[Fact(DisplayName = "1 USD + 1100 KRW = 2200 KRW")]
public void AddDollarsAndKoreanWons()
{
    var portfolio = new Portfolio(1d.Dollars(), 1100d.KoreanWons());
    portfolio.Evaluate(Currency.KRW)
        .Should()
        .Be(2200d.KoreanWons());
} 
```
* Let's introduce a `Dictionary` to store exchange rates
  * Add the 2 entries we need now (EUR -> USD, USD -> KRW)
      * We use a function `KeyFor` that makes it easy to generate a key for a currency pair (from -> to)
  * We use it in the convert method
```c#
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
```

* What happens if we try to evaluate in a currency without `exchangeRates` ?
    * Remove all entries from our `Dictionary`
    * It fails with the message : `System.Collections.Generic.KeyNotFoundException: The given key 'USD->KRW' was not present in the dictionary.`
* We need to improve error handling in our code
  * Let's add it in our feature list

#### Where we are
```text
✅ 5 USD x 2 = 10 USD 
✅ 10 EUR x 2 = 20 EUR
✅ 4002 KRW / 4 = 1000.5 KRW
✅ 5 USD + 10 USD = 15 USD
✅ Separate test code from production code
✅ Remove redundant tests
✅ 5 USD + 10 EUR = 17 USD
✅ 1 USD + 1100 KRW = 2200 KRW
✅ Determine exchange rate based on the currencies involved (from -> to)
Improve error handling when exchange rates are unspecified
Allow exchange rates to be modified
```

### Chapter 10 - Error Handling
`What error drives our eyes and ears amiss ? - William Shakespeare

#### Error Wish List
```text
- The Evaluate method should signal an explicit error when one or more necessary exchange rates ares missing
- The error message should be "greedy" - indicate all the missing exchange rates
- To prevent error from being ignored by the caller : no valid Money should be returned when an error happens due to missing exchange rates
```

* We will use exception here in case of failure
  * We will use other data structure later (`Either<string,Money>` for example)
```c#
[Fact(DisplayName = "Throw greedy exception in case of missing exchange rates")]
public void AddWithMissingExchangeRatesShouldThrowGreedyException()
{
    var portfolio = new Portfolio(1d.Dollars(), 1d.Euros(), 1d.KoreanWons());
    portfolio.Invoking(p => p.Evaluate(Currency.KRW))
        .Should()
        .Throw<MissingExchangeRatesException>()
        .WithMessage("Missing exchange rate(s): [EUR->KRW]");
}  
```

* Create the `MissingExchangeRatesException` class
```c#
public class MissingExchangeRatesException : Exception
{
    public MissingExchangeRatesException()
    {
    }
} 
```
* Add a `CheckExchangeRates` method that will throw a `MissingExchangeRatesException` in case of missing Exchange rates 
```c#
public Money Evaluate(Currency toCurrency)
{
    CheckExchangeRates(toCurrency);
    return new Money(Moneys.Aggregate(0d, (acc, money) => acc + Convert(money, toCurrency)), toCurrency);


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
```
* Improve our `MissingExchangeRatesException` to create a descriptive message :
```c#
public class MissingExchangeRatesException : Exception
{
    public MissingExchangeRatesException(string[] missingExchangeRates)
        : base($"Missing exchange rate(s): [{string.Join(",", missingExchangeRates)}]")
    {
    }
}
```
* Our test is green now

#### Improve Portfolio instantiation
* Extension methods can make our future refactoring easiest
```c#
public static Portfolio AddToPortfolio(this Money money1, Money money2) => new(money1, money2);
public static Portfolio AddToPortfolio(this Portfolio portfolio, Money money) => new(portfolio.Moneys.Append(money).ToArray());

// Impact in the tests 
[Fact(DisplayName = "Throw greedy exception in case of missing exchange rates")]
public void AddWithMissingExchangeRatesShouldThrowGreedyException()
{
    var portfolio = 1d.Dollars()
        .AddToPortfolio(1d.Euros())
        .AddToPortfolio(1d.KoreanWons());

    portfolio.Invoking(p => p.Evaluate(Currency.KRW))
        .Should()
        .Throw<MissingExchangeRatesException>()
        .WithMessage("Missing exchange rate(s): [EUR->KRW]");
} 
```

#### Where we are
* We have added error handling
  * Portfolio evaluation is not simple anymore
    * Clumsy code to check if we have missing exchange rates
  * Let's add a new feature in our list
```text
✅ 5 USD x 2 = 10 USD 
✅ 10 EUR x 2 = 20 EUR
✅ 4002 KRW / 4 = 1000.5 KRW
✅ 5 USD + 10 USD = 15 USD
✅ Separate test code from production code
✅ Remove redundant tests
✅ 5 USD + 10 EUR = 17 USD
✅ 1 USD + 1100 KRW = 2200 KRW
✅ Determine exchange rate based on the currencies involved (from -> to)
✅ Improve error handling when exchange rates are unspecified
Improve the implementation of exchange rates
Allow exchange rates to be modified
```
### Chapter 11 - Banking on Redesign
`On the whole it's worth evolving your design as your needs grow... - Martin Fowler`

* Our `Portfolio` does too much work
  * Its primary job is to be a repository of `Money` entities
  * Not to manage Exchange Rates
* Our software program has grown with our needs
  * It is worth improving our design and looking for a better abstraction
* A principle of Domain Driven Design is continuous learning
  * We are missing a key entity
  * `What is the name of the real-world institution that helps us exchange money ?`

#### Bank concept
* What should be its responsibility ?
  * Hold exchange rates
  * Convert money between currencies

#### Dependency Injection
* We have identified a new entity
  * How should the dependencies between `Bank` and the other two existing entities look ?
  ![Bank class diagram](img/bank-class-diagram.png)
  * The dependency of `Portfolio` on `Bank` is kept to a minimum
    * `Bank` is provided as a parameter to the `Evaluate` method
    * We do `method injection`

#### Putting It All Together
* Let's write a test to convert one `Money` object into another :
    * We choose to make an immutable data structure as well
```c#
[Fact(DisplayName = "10 EUR -> USD = 12 USD")]
public void ConvertEuroToUsd()
{
    // WithExchangeRates will be our Factory method
    var bank = Bank.WithExchangeRates(EUR, USD, 1.2);

    bank.Convert(10d.Euros(), USD)
        .Should()
        .Be(12d.Dollars());
}
```
* We write the minimum to pass the test
  * Initialize an empty Dictionary
  * Forming a key to store the exchange rate
  * Create the convert method that returns `Money`
```c#
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

    public Money Convert(Money money, Currency currency) =>
        currency == money.Currency
            ? money
            : new Money(money.Amount * _exchangeRates[KeyFor(money.Currency, currency)], currency);
} 
```
* We now need to keep existing behavior
  * Throws a greedy exception on missing exchange rates
* Let's write a new test
```c#
[Fact(DisplayName = "Throw missing exchange rates exception in case of missing exchange rates")]
public void ConvertWithMissingExchangeRatesShouldThrowException()
{
    var portfolio = Bank.WithExchangeRates(EUR, USD, 1.2);

    portfolio.Invoking(p => p.Convert(10d.Euros(), KRW))
        .Should()
        .Throw<MissingExchangeRatesException>()
        .WithMessage("Missing exchange rate(s): [EUR->KRW]");
}
```
* Implement the minimum to pass the test
```c#
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
```
