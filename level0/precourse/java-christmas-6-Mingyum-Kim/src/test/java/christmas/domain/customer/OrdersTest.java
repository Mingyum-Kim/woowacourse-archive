package christmas.domain.customer;

import static christmas.global.exception.ErrorMessage.INVALID_ORDER_ERROR;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import christmas.controller.dto.request.OrderRequest;
import christmas.controller.dto.request.OrdersRequest;
import christmas.domain.restaurant.Menu;
import christmas.domain.restaurant.MenuItem;
import java.util.Arrays;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.EnumSource;

class OrdersTest {
    @Nested
    @DisplayName("할인 계산 테스트")
    class DiscountCalculationTests {

        @ParameterizedTest
        @CsvSource({
                "DESSERT, 1000, 3, 3",
                "MAIN_COURSE, 1000, 3, 3"
        })
        @DisplayName("디저트 및 메인 메뉴에 대해 할인을 적용한다.")
        void calculateDiscountForMenu(Menu menu, int discount, int quantity) {
            // given
            Orders orders = Orders.from(
                    new OrdersRequest(
                            Arrays.asList(
                                    new OrderRequest("타파스", 2),
                                    new OrderRequest(menu.getMenuItems().get(0).getName(), quantity)
                            )
                    )
            );

            // when
            int totalDiscount = orders.calculateTotalDiscountForMenu(discount, menu);

            // then
            int expected = quantity * discount;
            assertEquals(expected, totalDiscount);
        }

        @ParameterizedTest
        @CsvSource({
                "DESSERT, 0",
                "MAIN_COURSE, 0"
        })
        @DisplayName("메뉴가 주문에 없는 경우 할인 가격은 0원이다.")
        void calculatingDiscountForMenuReturnZeroDiscount(Menu menu, int expectedDiscount) {
            // given
            Orders orders = Orders.from(
                    new OrdersRequest(
                            Arrays.asList(
                                    new OrderRequest("타파스", 2)
                            )
                    )
            );

            // when
            int totalDiscount = orders.calculateTotalDiscountForMenu(1000, menu);

            // then
            assertEquals(expectedDiscount, totalDiscount);
        }
    }

    @Nested
    @DisplayName("메뉴 존재 여부 테스트")
    class MenuPresenceTests {

        @ParameterizedTest
        @EnumSource(value = Menu.class, names = {"DESSERT", "MAIN_COURSE"})
        @DisplayName("디저트, 메인 메뉴가 최소 1개 이상인 경우 참을 반환한다.")
        void hasAtLeastForMenuTrueTest(Menu menu) {
            // given
            Orders orders = Orders.from(
                    new OrdersRequest(
                            Arrays.asList(
                                    new OrderRequest("타파스", 2),
                                    new OrderRequest(menu.getMenuItems().get(0).getName(), 2)
                            )
                    )
            );

            // when
            boolean hasAtLeast = orders.hasAtLeastForMenu(menu);

            // then
            assertTrue(hasAtLeast);
        }

        @ParameterizedTest
        @EnumSource(value = Menu.class, names = {"DESSERT", "MAIN_COURSE"})
        @DisplayName("디저트, 메인 메뉴가 없는 경우 거짓을 반환한다.")
        void hasAtLeastForMenuFalseTest(Menu menu) {
            // given
            Orders orders = Orders.from(
                    new OrdersRequest(
                            Arrays.asList(
                                    new OrderRequest("타파스", 2)
                            )
                    )
            );

            // when
            boolean hasAtLeast = orders.hasAtLeastForMenu(menu);

            // then
            assertFalse(hasAtLeast);
        }
    }

    @Test
    @DisplayName("주문 내역을 저장하고 금액을 계산한다.")
    void calculateOrdersCost() {
        // given
        Orders orders = Orders.from(
                new OrdersRequest(
                        Arrays.asList(
                                new OrderRequest("타파스", 2),
                                new OrderRequest("시저샐러드", 3)
                        )
                )
        );

        // when
        int totalCost = orders.calculateOrdersCost();

        // Assert
        int expected = 2 * MenuItem.TAPAS.getCost() + 3 * MenuItem.CAESAR_SALAD.getCost();
        assertEquals(expected, totalCost);
    }

    @Nested
    class ValidatorTests {
        @ParameterizedTest
        @CsvSource({
                "SEAFOOD_PASTA, 11, CHOCOLATE_CAKE, 10",
                "ICE_CREAM, 2, ZERO_COLA, 25"
        })
        @DisplayName("주문 메뉴의 개수가 범위를 벗어난 경우 오류를 반환한다.")
        void validateTotalCount(
                MenuItem menuItem1, int count1,
                MenuItem menuItem2, int count2
        ) {
            // given
            OrdersRequest ordersRequest = new OrdersRequest(
                    Arrays.asList(
                            new OrderRequest(menuItem1.getName(), count1),
                            new OrderRequest(menuItem2.getName(), count2)
                    )
            );

            // when & then
            assertThatThrownBy(() -> Orders.from(ordersRequest))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessageContaining(INVALID_ORDER_ERROR.getMessage());
        }

        @ParameterizedTest
        @CsvSource({
                "SEAFOOD_PASTA, 2, SEAFOOD_PASTA, 3",
                "MUSHROOM_SOUP, 3, MUSHROOM_SOUP, 5"
        })
        @DisplayName("메뉴가 중복된 경우 예외를 반환한다.")
        void validateDuplicatedMenuItems(
                MenuItem menuItem1, int count1,
                MenuItem menuItem2, int count2
        ) {
            // given
            OrdersRequest ordersRequest = new OrdersRequest(
                    Arrays.asList(
                            new OrderRequest(menuItem1.getName(), count1),
                            new OrderRequest(menuItem2.getName(), count2)
                    )
            );

            // when & then
            assertThatThrownBy(() -> Orders.from(ordersRequest))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessageContaining(INVALID_ORDER_ERROR.getMessage());
        }

        @ParameterizedTest
        @CsvSource({
                "ZERO_COLA, 3",
                "CHAMPAGNE, 2"
        })
        @DisplayName("음료만 주문한 경우 예외를 반환한다.")
        void validateOnlyBeverages(
                MenuItem menuItem, int count
        ) {
            // given
            OrdersRequest ordersRequest = new OrdersRequest(
                    Arrays.asList(
                            new OrderRequest(menuItem.getName(), count)
                    )
            );

            // when & then
            assertThatThrownBy(() -> Orders.from(ordersRequest))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessageContaining(INVALID_ORDER_ERROR.getMessage());
        }
    }
}
