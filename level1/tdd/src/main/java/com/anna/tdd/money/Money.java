package com.anna.tdd.money;

class Money {
    protected int amount;

    public static Dollar dollar(int amount) {
        return new Dollar(amount);
    }

    public static Franc franc(int amount) {
        return new Franc(amount);
    }

    public boolean equals(Object object) {
        Money money = (Money) object;
        return money.amount == amount && money.getClass() == this.getClass();
    }
}
