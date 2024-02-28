package com.anna.tdd.money;

public class Money {
    private final int amount;
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

    public boolean equals(Object object) {
        Money money = (Money) object;
        return money.amount == amount && money.getClass().equals(getClass());
    }

    public String currency() {
        return currency;
    }
}
