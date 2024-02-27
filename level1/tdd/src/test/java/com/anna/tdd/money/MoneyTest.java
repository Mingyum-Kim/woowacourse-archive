package com.anna.tdd.money;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class MoneyTest {
    @Test
    void testSimpleAddition() {
        Money sum = Money.dollar(5).plus(Money.dollar(5));
        Assertions.assertEquals(Money.dollar(10), sum);
    }
}
