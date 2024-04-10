package christmas.domain.promotion;

import christmas.controller.dto.response.gift.GiftResponse;
import christmas.domain.customer.Count;
import christmas.domain.restaurant.MenuItem;

public class Gift extends Benefit {
    private MenuItem menuItem;
    private Count count;

    private Gift(MenuItem menuItem, Count count) {
        this.menuItem = menuItem;
        this.count = count;
    }

    public static Gift of(MenuItem menuItem, Count count) {
        return new Gift(menuItem, count);
    }

    public GiftResponse toGiftResponse() {
        return new GiftResponse(
                this.menuItem.getName(),
                this.count.getValue()
        );
    }

    /**
     * 증정 내역의 혜택을 계산하는 메서드
     *
     * @return 증정품의 개당 가격과 총 개수를 곱한 결과값
     */
    public int getPrice() {
        return this.menuItem.getCost() * this.count.getValue();
    }
}
