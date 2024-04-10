package christmas.domain.customer;

import christmas.controller.dto.response.order.OrderResponse;
import christmas.domain.restaurant.Menu;
import christmas.domain.restaurant.MenuItem;

public class Order {
    private MenuItem menuItem;
    private Count count;

    private Order(String name, int count) {
        this.menuItem = MenuItem.findByName(name);
        this.count = Count.valueOf(count);
    }

    public static Order of(String name, int count) {
        return new Order(name, count);
    }

    public OrderResponse toResponse() {
        return new OrderResponse(menuItem.getName(), count.getValue());
    }

    /**
     * 특정 메뉴 카테고리에 해당하는 주문임을 확인하는 메서드
     *
     * @param menu 메뉴 카테고리
     * @return 주문 메뉴가 메뉴 카테고리와 일치하면 true, 그렇지 않으면 false
     */
    public boolean isIncluded(Menu menu) {
        return Menu.contains(menu, menuItem);
    }

    /**
     * 현재 메뉴의 개수가 할인 가격보다 작은 지 확인하는 메서드
     *
     * @param discountPrice 메뉴 당 할인 가격
     * @return 메뉴의 가격이 할인 가격보다 작으면 true, 크거나 같으면 false
     */
    public boolean isLessThan(int discountPrice) {
        return menuItem.getCost() < discountPrice;
    }

    /**
     * 특정 메뉴 주문에 대한 금액을 계산하는 메서드
     *
     * @return 해당 메뉴에 대한 금액
     */
    public int calculateOrderCost() {
        return menuItem.getCost() * count.getValue();
    }

    /**
     * 메뉴의 주문 개수와 입력된 가격을 곱하는 메서드
     *
     * @param price 가격
     * @return 메뉴의 개수만큼 가격을 곱한 결과값
     */
    public int calculateTotalPrice(int price) {
        return price * count.getValue();
    }

    public MenuItem getMenuItem() {
        return menuItem;
    }

    public Count getCount() {
        return count;
    }
}
