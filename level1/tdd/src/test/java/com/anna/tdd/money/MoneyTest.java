package com.anna.tdd.money;

import com.anna.tdd.bank.Bank;
import com.anna.tdd.bank.Expression;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

public class MoneyTest {
    @Test
    public void testSimpleAddition() {
        Money five = Money.dollar(5);
        Money ten = Money.franc(10);
        Expression sum = five.plus(ten);

        Bank bank = new Bank();
        Money reduced = bank.reduce(sum, "USD"); // 연산 결과를 달러로 변환
        Assertions.assertThat(Money.dollar(10)).isEqualTo(reduced);
    }
}
