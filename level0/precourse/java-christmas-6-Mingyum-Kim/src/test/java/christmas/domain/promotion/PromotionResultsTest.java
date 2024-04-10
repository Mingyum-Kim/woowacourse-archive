package christmas.domain.promotion;

import static org.junit.jupiter.api.Assertions.assertEquals;

import christmas.controller.dto.request.OrderRequest;
import christmas.controller.dto.request.OrdersRequest;
import christmas.domain.customer.Count;
import christmas.domain.customer.Orders;
import christmas.domain.restaurant.MenuItem;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class PromotionResultsTest {

    @Test
    @DisplayName("할인 및 선물 금액을 제외한 총 결제 금액을 계산한다.")
    void calculatePayment() {
        // given
        List<PromotionResult> promotionResults = Arrays.asList(
                PromotionResult.of(Promotion.CHRISTMAS, Discount.from(1000)),
                PromotionResult.of(Promotion.GIFT, Gift.of(MenuItem.CHAMPAGNE, Count.valueOf(1)))
        );
        PromotionResults promotionResultsInstance = PromotionResults.from(promotionResults);

        Orders orders = Orders.from(
                new OrdersRequest(
                        Arrays.asList(
                                new OrderRequest("타파스", 2),
                                new OrderRequest("아이스크림", 3),
                                new OrderRequest("티본스테이크", 4)
                        )
                )
        );

        int expected = orders.calculateOrdersCost() - 1000;

        // when
        int payment = promotionResultsInstance.calculatePayment(orders);

        // then
        assertEquals(expected, payment);
    }

    @Test
    @DisplayName("할인 및 선물 총 혜택 금액을 계산한다.")
    void calculateTotalBenefits() {
        // given
        List<PromotionResult> promotionResults = Arrays.asList(
                PromotionResult.of(Promotion.CHRISTMAS, Discount.from(1000)),
                PromotionResult.of(Promotion.GIFT, Gift.of(MenuItem.CHAMPAGNE, Count.valueOf(1)))
        );
        PromotionResults promotionResultsInstance = PromotionResults.from(promotionResults);

        // when
        int totalBenefits = promotionResultsInstance.calculateTotalBenefits();

        // then
        assertEquals(26000, totalBenefits);
    }
}
