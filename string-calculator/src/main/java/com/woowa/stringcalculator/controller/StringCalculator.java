package com.woowa.stringcalculator.controller;

import com.woowa.stringcalculator.view.View;

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

    }
}
