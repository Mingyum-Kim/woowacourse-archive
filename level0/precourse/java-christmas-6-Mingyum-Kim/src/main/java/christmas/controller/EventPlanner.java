package christmas.controller;

import christmas.controller.dto.response.order.CustomerResponse;
import christmas.domain.customer.Date;
import christmas.domain.customer.Orders;
import christmas.domain.promotion.EventBadge;
import christmas.domain.promotion.PromotionResults;
import christmas.service.promotion.PromotionHandler;
import christmas.view.InputView;
import christmas.view.OutputView;
import christmas.view.console.ConsoleWriter;
import java.util.function.Supplier;

public class EventPlanner {
    private final InputView inputView;
    private final OutputView outputView;
    private final PromotionHandler promotionHandler;

    public EventPlanner(InputView inputView,
                        OutputView outputView,
                        PromotionHandler promotionHandler
    ) {
        this.inputView = inputView;
        this.outputView = outputView;
        this.promotionHandler = promotionHandler;
        outputView.start();
    }

    public void run() {
        Date date = retry(() -> {
            return Date.from(inputView.requestDate());
        });
        Orders orders = retry(() -> {
            return Orders.from(retry(inputView::requestOrders));
        });
        response(date, orders, promotionHandler.process(date, orders));
    }

    private static <T> T retry(Supplier<T> supplier) {
        while (true) {
            try {
                return supplier.get();
            } catch (IllegalArgumentException e) {
                ConsoleWriter.printlnMessage(e.getMessage());
            }
        }
    }

    private void response(Date date,
                          Orders orders,
                          PromotionResults promotionResults
    ) {
        outputView.printOrders(new CustomerResponse(date.getDate(), orders.toResponse()));
        outputView.printTotalCost(orders.calculateOrdersCost());

        outputView.printGiftMenu(promotionResults.toGiftsResponse());
        outputView.printBenefits(promotionResults.toBenefitsResponse());

        int totalBenefits = promotionResults.calculateTotalBenefits();
        outputView.printTotalBenefits(totalBenefits);

        outputView.printPayment(promotionResults.calculatePayment(orders));

        outputView.printEventBadge(EventBadge.findByTotalBenefits(totalBenefits));
    }
}
