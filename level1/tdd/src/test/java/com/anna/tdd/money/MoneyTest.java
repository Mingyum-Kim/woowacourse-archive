package com.anna.tdd.money;

import static org.junit.jupiter.api.Assertions.assertFalse;

import org.junit.jupiter.api.Test;

public class MoneyTest {
    @Test
    void testEquality() {
        assertFalse(new Dollar(5).equals(new Franc(5)));
    }
}
