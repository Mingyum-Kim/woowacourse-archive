package christmas.domain.customer;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import christmas.controller.dto.response.order.OrderResponse;
import christmas.domain.restaurant.Menu;
import christmas.domain.restaurant.MenuItem;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class OrderTest {

    @ParameterizedTest
    @CsvSource({
            "타파스, 2",
            "제로콜라, 10"
    })
    @DisplayName("메뉴 단건에 대해 주문 객체를 생성한다. ")
    void createOrder(String name, int count) {
        // when
        Order order = Order.of(name, count);

        // then
        assertNotNull(order);
        assertEquals(name, order.getMenuItem().getName());
        assertEquals(count, order.getCount().getValue());
    }

    @ParameterizedTest
    @CsvSource({
            "타파스, 2",
            "제로콜라, 10"
    })
    @DisplayName("메뉴 객체를 DTO 객체로 변환한다.")
    void convertOrder(String name, int count) {
        // given
        Order order = Order.of(name, count);

        // when
        OrderResponse orderResponse = order.toResponse();

        // then
        assertNotNull(orderResponse);
        assertEquals(name, orderResponse.name());
        assertEquals(count, orderResponse.count());
    }

    @ParameterizedTest
    @CsvSource({
            "APPETIZER, true",
            "DESSERT, false"
    })
    @DisplayName("특정 메뉴 카테고리에 해당하는 주문인지 확인한다.")
    void checkIfOrderIsIncludedInMenu(Menu menu, boolean expected) {
        // given
        Order order = Order.of("타파스", 2);

        // when
        boolean isIncluded = order.isIncluded(menu);

        // then
        assertEquals(expected, isIncluded);
    }

    @ParameterizedTest
    @CsvSource({
            "6000, true",
            "1000, false"
    })
    @DisplayName("현재 메뉴의 가격이 할인 가격보다 작은 지 확인한다.")
    void checkIfOrderCountIsLessThanDiscountPrice(int discountPrice, boolean expected) {
        // given
        Order order = Order.of("타파스", 2);

        // when
        boolean isLessThan = order.isLessThan(discountPrice);

        // then
        assertEquals(expected, isLessThan);
    }

    @Test
    @DisplayName("특정 메뉴 주문에 대한 금액을 계산한다.")
    void calculateOrderCost() {
        // given
        Order order = Order.of("타파스", 2);

        // when
        int orderCost = order.calculateOrderCost();

        // then
        assertEquals(2 * MenuItem.TAPAS.getCost(), orderCost);
    }


    @ParameterizedTest
    @CsvSource({
            "500, 1000",
            "700, 1400"
    })
    @DisplayName("메뉴의 주문 개수와 입력된 가격을 곱한 결과를 계산한다.")
    void calculateTotalPrice(int price, int expectedTotalPrice) {
        // given
        Order order = Order.of("타파스", 2);

        // when
        int totalPrice = order.calculateTotalPrice(price);

        // then
        assertEquals(expectedTotalPrice, totalPrice);
    }
}
