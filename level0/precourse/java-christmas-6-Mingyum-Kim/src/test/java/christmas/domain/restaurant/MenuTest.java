package christmas.domain.restaurant;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;

class MenuTest {

    @Nested
    @DisplayName("Menu 조회 테스트")
    class ContainsTests {
        @ParameterizedTest
        @EnumSource(Menu.class)
        @DisplayName("메뉴에 속하는 경우 참을 반환한다.")
        void checkContains(Menu menu) {
            // given
            MenuItem menuItem = menu.getMenuItems().get(0);

            // when, then
            assertTrue(Menu.contains(menu, menuItem));
        }

        @Test
        @DisplayName("메뉴에 속하지 않는 경우 거짓을 반환한다.")
        void checkNotContains() {
            // given
            MenuItem menuItem = MenuItem.CHOCOLATE_CAKE;

            // when, then
            assertFalse(Menu.contains(Menu.APPETIZER, menuItem));
        }
    }
}
