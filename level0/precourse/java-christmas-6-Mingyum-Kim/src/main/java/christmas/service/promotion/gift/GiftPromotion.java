package christmas.service.promotion.gift;

import christmas.domain.customer.Count;
import christmas.domain.customer.Date;
import christmas.domain.customer.Orders;
import christmas.domain.promotion.Gift;
import christmas.domain.promotion.Promotion;
import christmas.domain.promotion.PromotionResult;
import christmas.domain.restaurant.MenuItem;
import christmas.service.promotion.PromotionService;
import java.util.Optional;

public class GiftPromotion implements PromotionService<Optional<PromotionResult<Gift>>> {
    private static final MenuItem GIFT_ITEM = MenuItem.CHAMPAGNE;
    private static final int GIFT_COUNT = 1;
    private static final int THRESHOLD = 120_000;

    @Override
    public Optional<PromotionResult<Gift>> apply(Date date, Orders orders) {
        if (isQualified(orders)) {
            return Optional.of(
                    PromotionResult.of(
                            Promotion.GIFT,
                            Gift.of(GIFT_ITEM, Count.valueOf(GIFT_COUNT))
                    )
            );
        }
        return Optional.empty();
    }

    private boolean isQualified(Orders orders) {
        return orders.calculateOrdersCost() >= THRESHOLD;
    }
}
