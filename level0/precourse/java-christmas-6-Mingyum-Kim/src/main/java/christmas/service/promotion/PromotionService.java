package christmas.service.promotion;

import christmas.domain.customer.Date;
import christmas.domain.customer.Orders;

public interface PromotionService<T> {
    public abstract T apply(Date date, Orders orders);
}
