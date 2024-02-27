package com.anna.tdd.money;

class Money {
    protected int amount;

    public boolean equals(Object object) {
        Money money = (Money) object;
        return money.amount == amount && money.getClass() == this.getClass();
    }
}
