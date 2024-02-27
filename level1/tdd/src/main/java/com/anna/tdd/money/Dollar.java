package com.anna.tdd.money;

public class Dollar {
    int amount;
    Dollar(int amount) {
        this.amount = amount;
    }

    public Dollar times(int multiplier) {
        return new Dollar(amount * multiplier);
    }
}
