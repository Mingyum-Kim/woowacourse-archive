package com.anna.tdd.money;

public class Dollar {
    int amount;
    Dollar(int amount) {
        this.amount = amount;
    }

    public void times(int multiplier) {
        amount *=  multiplier;
    }
}
