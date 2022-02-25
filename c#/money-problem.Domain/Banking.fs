﻿namespace money_problem.Domain

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
    let from(amount, currency) : Money = { Amount = amount; Currency = currency  }
    [<CompiledName("Times"); Extension>]
    let times(money, multiplier): Money = { money with Amount = money.Amount * multiplier }
    [<CompiledName("Divide"); Extension>]
    let divide(money, divisor): Money = { money with Amount = money.Amount / divisor }
    
    // Extension methods
    type Double with
        [<CompiledName("Dollars"); Extension>] 
        member money.dollars(): Money = { Amount = money; Currency = USD  }
        [<CompiledName("Euros"); Extension>]
        member money.euros(): Money = { Amount = money; Currency = EUR  }
        [<CompiledName("KoreanWons"); Extension>]
        member money.koreanWons(): Money = { Amount = money; Currency = KRW  }
    
#nowarn "3391"
[<Extension>]
module Bank =
    let private keyFor(fromCurrency: Currency, toCurrency: Currency) = $"{fromCurrency}->{toCurrency}"
    
    [<CompiledName("WithExchangeRate")>]
    let withExchangeRate fromCurrency toCurrency rate = { ExchangeRates = Map.empty.Add(keyFor(fromCurrency, toCurrency), rate) }
    
    [<CompiledName("AddExchangeRate"); Extension>]
    let addExchangeRates(bank: Bank, fromCurrency: Currency, toCurrency: Currency, rate: double) =
        { bank with ExchangeRates = bank.ExchangeRates.Add(keyFor(fromCurrency, toCurrency), rate) }
    
    let private convertSafely(bank: Bank, money: Money, currency: Currency) =
       if(money.Currency = currency) then money
       else Money.from(money.Amount * bank.ExchangeRates.[keyFor(money.Currency, currency)], currency)
       
    [<CompiledName("Convert"); Extension>]
    let convert(bank: Bank,
                money: Money,
                toCurrency: Currency): Either<string, Money> =
        let exchangeKey = keyFor(money.Currency, toCurrency)
        match money.Currency with
        | c when c = toCurrency -> money 
        | _ when bank.ExchangeRates.ContainsKey(exchangeKey) -> convertSafely(bank, money, toCurrency)
        | _ -> exchangeKey
        
        
[<Extension>]
module Portfolio = 
    [<CompiledName("Evaluate"); Extension>]
    let evaluate(portfolio: Portfolio,
                 bank: Bank,
                 currency: Currency): Either<string, Money> =
        let convertedMoneys = portfolio |> List.map(fun money -> Bank.convert(bank, money, currency))
        if(convertedMoneys.Lefts() |> Seq.isEmpty) then
            let foldAmount = convertedMoneys.Rights().Fold(0., fun acc money -> acc + money.Amount)
            Money.from(foldAmount, currency)
        else
            let errorMessage = convertedMoneys.Lefts() |> String.concat ","
            $"Missing exchange rate(s): [{errorMessage}]"
    
    [<CompiledName("AddToPortfolio"); Extension>]
    let addToPortfolio money1 money2: Portfolio = [money1; money2]
    
    [<CompiledName("Add"); Extension>]
    let add(portfolio: Portfolio, money: Money): Portfolio = money :: portfolio