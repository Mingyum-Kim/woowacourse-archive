package com.woowa.stringcalculator.view;

import java.util.Scanner;

public class View {
    public String getNumbers() {
        Scanner scanner = new Scanner(System.in);
        return scanner.nextLine();
    }

    public void printResult(int result) {
        System.out.println(result);
    }
}
