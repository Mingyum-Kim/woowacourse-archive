package christmas.domain.restaurant;

import static christmas.global.exception.ErrorMessage.INVALID_ORDER_ERROR;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;

class MenuItemTest {
    @Nested
    @DisplayName("MenuItem 조회 테스트")
    class FindByNameTests {

        @ParameterizedTest
        @EnumSource(MenuItem.class)
        @DisplayName("유효한 이름으로 메뉴를 찾는다.")
        void findValidMenuByName(MenuItem menuItem) {
            // given
            String name = menuItem.getName();

            // when
            MenuItem foundMenu = MenuItem.findByName(name);

            // then
            assertNotNull(foundMenu);
            assertEquals(menuItem, foundMenu);
        }

        @Test
        @DisplayName("유효하지 않은 이름으로 메뉴를 찾는 경우 예외를 던진다.")
        void findInvalidMenuByName() {
            // given
            String invalidName = "InvalidMenu";

            // when, then
            assertThatThrownBy(() -> MenuItem.findByName(invalidName))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessageContaining(INVALID_ORDER_ERROR.getMessage());
        }
    }
}
