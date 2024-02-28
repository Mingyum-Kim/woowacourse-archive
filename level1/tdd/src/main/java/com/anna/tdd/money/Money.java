package com.anna.tdd.money;

import com.anna.tdd.bank.Expression;

public class Money implements Expression {
    protected final int amount;
    protected String currency;

    public Money(final int amount, final String currency) {
        this.amount = amount;
        this.currency = currency;
    }

    public static Money dollar(final int amount) {
        return new Money(amount, "USD");
    }

    public static Money franc(final int amount) {
        return new Money(amount, "CHF");
    }

    public Money times(int multiplier) {
        return new Money(amount * multiplier, currency);
    }

    public Expression plus(final Money addend) {
        return new Money(amount + addend.amount, currency);
    }

    public boolean equals(Object object) {
        Money money = (Money) object;
        return money.amount == amount && currency().equals(money.currency());
    }

    public String currency() {
        return currency;
    }
}
