package com.anna.tdd.money;

public class Money {
    private final int amount;

    public Money(final int amount) {
        this.amount = amount;
    }

    public static Dollar dollar(final int amount) {
        return new Dollar(amount);
    }

    public Money plus(Money addend) {
        return new Money(amount + addend.amount);
    }
}
