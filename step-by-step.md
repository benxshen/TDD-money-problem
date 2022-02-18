# Learning Test-Driven Development (TDD)
* Workshop to learn TDD step-by-step from Saleem Siddiqui's book : [Learning Test-Driven Development](https://www.oreilly.com/library/view/learning-test-driven-development/9781098106461/)
* In this md file you will find code samples in C#

## What is TDD
TDD is a technique borne of a set of beliefs about code :  

* Simplicity - the art of maximizing the amount of work *not* done
* Obviousness and clarity are more virtuous than cleverness
* Writing uncluterred code is a key component of being successful

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
```

* Try to run tests :

```shell
dotnet test
```

