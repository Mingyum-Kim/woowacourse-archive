package christmas;

import camp.nextstep.edu.missionutils.Console;
import christmas.controller.EventPlanner;
import christmas.service.promotion.PromotionHandler;
import christmas.view.InputView;
import christmas.view.OutputView;

public class Application {
    public static void main(String[] args) {
        EventPlanner eventPlanner = new EventPlanner(
                new InputView(),
                new OutputView(),
                new PromotionHandler()
        );
        eventPlanner.run();
        Console.close();
    }
}
