package christmas.domain.customer;

import christmas.controller.dto.request.OrdersRequest;
import christmas.controller.dto.response.order.OrderResponse;
import christmas.domain.restaurant.Menu;
import christmas.global.exception.CustomException;
import christmas.global.exception.ErrorMessage;
import java.util.List;

public class Orders {
    private List<Order> orders;

    private Orders(List<Order> orders) {
        this.orders = Validator.validate(orders);
    }

    public static Orders from(OrdersRequest ordersRequest) {
        return new Orders(convertToOrdersFrom(ordersRequest));
    }

    private static List<Order> convertToOrdersFrom(OrdersRequest ordersRequest) {
        return ordersRequest.orders()
                .stream()
                .map(order -> Order.of(order.name(), order.count()))
                .toList();
    }

    public List<OrderResponse> toResponse() {
        return orders.stream()
                .map(Order::toResponse)
                .toList();
    }

    /**
     * 주문 내역의 전체 금액을 계산하는 메서드
     *
     * @return 주문 내역의 할인 전 총 금액
     */
    public int calculateOrdersCost() {
        return orders.stream()
                .mapToInt(Order::calculateOrderCost)
                .sum();
    }

    /**
     * 주문 내역에서 특정 메뉴 하나 당 할인을 적용하는 메서드
     *
     * @param discount 메뉴 하나 당 할인할 금액
     * @param menu     할인을 적용할 메뉴
     * @return 주문 내역에서 특정 메뉴에 대한 할인 금액의 총합
     */
    public int calculateTotalDiscountForMenu(int discount, Menu menu) {
        return orders.stream()
                .filter(order -> order.isIncluded(menu))
                .mapToInt(order -> calculateTotalDiscount(order, discount))
                .sum();
    }

    /**
     * 하나의 메뉴에 대한 주문 내역에서 할인을 적용하는 메서드
     *
     * @param order    주문 메뉴와 주문한 개수
     * @param discount 메뉴 하나 당 할인 금액
     * @return 메뉴의 가격이 할인 금액보다 작다면 메뉴의 가격의 총합, 크다면 할인 금액의 총합
     */
    private int calculateTotalDiscount(Order order, int discount) {
        if (order.isLessThan(discount)) {
            return order.calculateOrderCost();
        }
        return order.calculateTotalPrice(discount);
    }

    /**
     * 주문 내역에 특정 메뉴가 하나라도 있는 지 확인하는 메서드
     *
     * @param menu 주문 내역에서 찾을 특정 메뉴
     * @return 찾는 메뉴가 하나라도 있으면 true, 그렇지 않으면 false
     */
    public boolean hasAtLeastForMenu(Menu menu) {
        return orders.stream()
                .anyMatch(order ->
                        Menu.contains(menu, order.getMenuItem())
                );
    }

    private static class Validator {
        private static final int MIN_ORDER_COUNT = 1;
        private static final int MAX_ORDER_COUNT = 20;

        private static List<Order> validate(List<Order> orders) {
            validateDuplicatedMenuItems(orders);
            validateOnlyBeverages(orders);
            validateTotalCount(orders);
            return orders;
        }

        private static void validateTotalCount(List<Order> orders) {
            int totalCount = calculateTotalCount(orders);
            if (isInvalidRange(totalCount)) {
                throw CustomException.from(ErrorMessage.INVALID_ORDER_ERROR);
            }
        }

        private static boolean isInvalidRange(int totalCount) {
            return totalCount < MIN_ORDER_COUNT || totalCount > MAX_ORDER_COUNT;
        }

        private static int calculateTotalCount(List<Order> orders) {
            return orders.stream()
                    .map(Order::getCount)
                    .mapToInt(Count::getValue)
                    .sum();
        }

        private static void validateDuplicatedMenuItems(List<Order> orders) {
            if (hasDuplicatedMenuItems(orders)) {
                throw CustomException.from(ErrorMessage.INVALID_ORDER_ERROR);
            }
        }

        private static boolean hasDuplicatedMenuItems(List<Order> orders) {
            return orders.size() != calculateUniqueMenuItems(orders);
        }

        private static int calculateUniqueMenuItems(List<Order> orders) {
            return (int) orders.stream()
                    .map(Order::getMenuItem)
                    .distinct()
                    .count();
        }

        private static void validateOnlyBeverages(List<Order> orders) {
            if (containsOnlyBeverages(orders)) {
                throw CustomException.from(ErrorMessage.INVALID_ORDER_ERROR);
            }
        }

        private static boolean containsOnlyBeverages(List<Order> orders) {
            return hasSingleMenuItem(orders) && isBeverages(orders.get(0));
        }

        private static boolean isBeverages(Order order) {
            return Menu.contains(Menu.BEVERAGE, order.getMenuItem());
        }

        private static boolean hasSingleMenuItem(List<Order> orders) {
            return orders.size() == 1;
        }
    }
}
