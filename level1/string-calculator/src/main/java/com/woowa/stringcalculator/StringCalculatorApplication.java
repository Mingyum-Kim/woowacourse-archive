package com.woowa.stringcalculator;

import com.woowa.stringcalculator.controller.StringCalculator;
import com.woowa.stringcalculator.domain.SumCalculator;
import com.woowa.stringcalculator.view.View;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class StringCalculatorApplication {

    public static void main(String[] args) {
        StringCalculator stringCalculator = new StringCalculator(new View());
        stringCalculator.run();
    }

}
