# Learning Test-Driven Development (TDD)
* Workshop to learn TDD step-by-step from Saleem Siddiqui's book : [Learning Test-Driven Development](https://www.oreilly.com/library/view/learning-test-driven-development/9781098106461/)
* In this md file you will find code samples in C#

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