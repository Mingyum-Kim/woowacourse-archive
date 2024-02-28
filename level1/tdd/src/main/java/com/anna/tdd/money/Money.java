package com.anna.tdd.money;

public class Money {
    protected final int amount;
    protected String currency;

    public Money(final int amount, final String currency) {
        this.amount = amount;
        this.currency = currency;
    }

    public static Dollar dollar(final int amount) {
        return new Dollar(amount, "USD");
    }

    public static Franc franc(final int amount) {
        return new Franc(amount, "CHF");
    }

    public Money times(int multiplier) {
        return new Money(amount * multiplier, currency);
    }

    public boolean equals(Object object) {
        Money money = (Money) object;
        return money.amount == amount && currency().equals(money.currency());
    }

    public String currency() {
        return currency;
    }
}
