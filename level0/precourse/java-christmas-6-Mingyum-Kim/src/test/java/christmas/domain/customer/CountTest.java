package christmas.domain.customer;

import static christmas.global.exception.ErrorMessage.INVALID_ORDER_ERROR;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class CountTest {

    @Test
    @DisplayName("메누 개수를 저장하는 객체를 정상적으로 생성한다.")
    void createCountAndGetValues() {
        // given
        int value = 3;

        // when
        Count count = Count.valueOf(value);

        // then
        assertNotNull(count);
        assertEquals(value, count.getValue());
    }

    @Test
    @DisplayName("양수가 아닌 값에 대해 유효성 검사에서 예외가 발생한다.")
    void validateNotPositiveValue() {
        // given
        int value = -1;

        // when, then
        assertThatThrownBy(() -> Count.valueOf(value))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining(INVALID_ORDER_ERROR.getMessage());
    }
}
