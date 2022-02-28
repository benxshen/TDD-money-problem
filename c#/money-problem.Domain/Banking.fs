namespace money_problem.Domain

open System
open System.Runtime.CompilerServices
open LanguageExt
open Microsoft.FSharp.Collections

module Types =
    type Currency =
        | EUR
        | USD
        | KRW

    type Money = { Amount: double; Currency: Currency }
    type Bank = { ExchangeRates: Map<string, double> }
    type Portfolio = Money list

open Types

[<Extension>]
module Money =
    let from (amount, currency) : Money =
        { Amount = amount; Currency = currency }

    [<CompiledName("Times"); Extension>]
    let times (money, multiplier) : Money =
        { money with
              Amount = money.Amount * multiplier }

    [<CompiledName("Divide"); Extension>]
    let divide (money, divisor) : Money =
        { money with
              Amount = money.Amount / divisor }

    [<CompiledName("Dollars"); Extension>]
    let dollars (amount: Double) : Money = { Amount = amount; Currency = USD }

    [<CompiledName("Euros"); Extension>]
    let euros (amount: Double) : Money = { Amount = amount; Currency = EUR }

    [<CompiledName("KoreanWons"); Extension>]
    let koreanWons (amount: Double) : Money = { Amount = amount; Currency = KRW }

#nowarn "3391"

[<Extension>]
module Bank =
    let private keyFor (fromCurrency: Currency, toCurrency: Currency) = $"{fromCurrency}->{toCurrency}"

    [<CompiledName("WithExchangeRate")>]
    let withExchangeRate fromCurrency toCurrency rate =
        { ExchangeRates = Map.empty.Add(keyFor (fromCurrency, toCurrency), rate) }

    [<CompiledName("AddExchangeRate"); Extension>]
    let addExchangeRates (bank: Bank, fromCurrency: Currency, toCurrency: Currency, rate: double) =
        { bank with
              ExchangeRates = bank.ExchangeRates.Add(keyFor (fromCurrency, toCurrency), rate) }

    let private convertSafely (bank: Bank, money: Money, currency: Currency) =
        Money.from (
            money.Amount
            * bank.ExchangeRates.[keyFor (money.Currency, currency)],
            currency
        )

    [<CompiledName("Convert"); Extension>]
    let convert (bank: Bank, money: Money, toCurrency: Currency) : Either<string, Money> =
        let exchangeKey = keyFor (money.Currency, toCurrency)

        match money.Currency with
        | from when from = toCurrency -> money
        | _ when bank.ExchangeRates.ContainsKey(exchangeKey) -> convertSafely (bank, money, toCurrency)
        | _ -> exchangeKey

[<Extension>]
module Portfolio =
    [<CompiledName("Evaluate"); Extension>]
    let evaluate (portfolio: Portfolio, bank: Bank, currency: Currency) : Either<string, Money> =
        let convertedMoneys =
            portfolio
            |> List.map (fun money -> Bank.convert (bank, money, currency))

        if (convertedMoneys.Lefts() |> Seq.isEmpty) then
            let foldAmount =
                convertedMoneys
                    .Rights()
                    .Fold(0., (fun acc money -> acc + money.Amount))

            Money.from (foldAmount, currency)
        else
            let errorMessage =
                convertedMoneys.Lefts() |> String.concat ","

            $"Missing exchange rate(s): [{errorMessage}]"

    [<CompiledName("AddToPortfolio"); Extension>]
    let addToPortfolio money1 money2 : Portfolio = [ money1; money2 ]

    [<CompiledName("Add"); Extension>]
    let add (portfolio: Portfolio, money: Money) : Portfolio = money :: portfolio
