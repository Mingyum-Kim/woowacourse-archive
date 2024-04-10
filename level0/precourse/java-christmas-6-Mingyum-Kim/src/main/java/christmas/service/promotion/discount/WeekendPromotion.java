package christmas.service.promotion.discount;

import christmas.domain.customer.Date;
import christmas.domain.customer.Orders;
import christmas.domain.promotion.Discount;
import christmas.domain.promotion.Promotion;
import christmas.domain.promotion.PromotionResult;
import christmas.domain.restaurant.Menu;
import christmas.service.promotion.PromotionService;
import java.util.Optional;

public class WeekendPromotion implements PromotionService<Optional<PromotionResult<Discount>>> {
    private static final int DISCOUNT_PRICE_PER_MENU = 2_023;

    @Override
    public Optional<PromotionResult<Discount>> apply(Date date, Orders orders) {
        if (isQualified(date, orders)) {
            return Optional.of(
                    PromotionResult.of(
                            Promotion.WEEKEND,
                            Discount.from(calculateDiscount(orders))
                    )
            );
        }
        return Optional.empty();
    }

    private boolean isQualified(Date date, Orders orders) {
        return date.isWeekend() && orders.hasAtLeastForMenu(Menu.MAIN_COURSE);
    }

    private int calculateDiscount(Orders orders) {
        return orders.calculateTotalDiscountForMenu(
                DISCOUNT_PRICE_PER_MENU,
                Menu.MAIN_COURSE
        );
    }
}
