package com.woowa.stringcalculator.controller;

import com.woowa.stringcalculator.domain.SumCalculator;
import com.woowa.stringcalculator.view.View;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringCalculator {
    private final View view;

    public StringCalculator(View view) {
        this.view = view;
    }

    public void run() {
        int result = splitAndSum(view.getNumbers());
        view.printResult(result);
    }

    public static int splitAndSum(String input) {
        String separators = getSeparators(input);
        List<Integer> numbers = convertToNumbers(input.split(separators));
        return new SumCalculator(numbers).sum();
    }

    private static String getSeparators(String input) {
        String tokens = ",|:";
        if (input.startsWith("//")) {
            String[] lines = input.split("\\n");
            String custom = lines[0].substring(2);
            tokens = tokens + "|" + custom;
        }
        return tokens;
    }

    private static List<Integer> convertToNumbers(String[] split) {
        try {
            List<Integer> numbers = Arrays.stream(split)
                    .map(Integer::parseInt)
                    .toList();
            validatePositiveNumbers(numbers);
            return numbers;
        } catch (NumberFormatException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    private static void validatePositiveNumbers(List<Integer> numbers) {
        if(hasNegativeNumber(numbers)) {
            throw new RuntimeException("음수를 포함할 수 없습니다.");
        }
    }

    private static boolean hasNegativeNumber(List<Integer> numbers) {
        return !numbers.stream()
                .allMatch(number -> number >= 0);
    }
}
