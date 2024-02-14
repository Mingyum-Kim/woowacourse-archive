package com.woowa.stringcalculator.domain;

import java.util.List;

public class SumCalculator {
    private List<Integer> numbers;

    public SumCalculator(List<Integer> numbers) {
        this.numbers = numbers;
    }

    public int sum() {
        if(numbers.isEmpty()) {
            return 0;
        }
        return numbers.stream()
                .mapToInt(i -> i)
                .sum();
    }
}
