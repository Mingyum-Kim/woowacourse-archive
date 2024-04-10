package christmas.domain.customer;

import static christmas.global.exception.ErrorMessage.INVALID_DATE_ERROR;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

class DateTest {
    @Nested
    @DisplayName("객체 생성 및 유효성 검증 테스트")
    class CreationAndValidationTests {

        @ParameterizedTest
        @ValueSource(ints = {1, 15, 31})
        @DisplayName("날짜 객체를 정상적으로 생성한다.")
        void createAndValidateDate(int inputDate) {
            // when & then
            Assertions.assertThatNoException()
                    .isThrownBy(() -> Date.from(inputDate));
        }

        @ParameterizedTest
        @ValueSource(ints = {-1, 0, 32})
        @DisplayName("범위에서 벗어나 날짜 객체 생성에 실패한다.")
        void checkDateRange(int inputDate) {
            // when & then
            assertThatThrownBy(() -> Date.from(inputDate))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessageContaining(INVALID_DATE_ERROR.getMessage());
        }
    }


    @ParameterizedTest
    @CsvSource({
            "1, false, true",
            "7, true, false",
            "24, true, false"
    })
    @DisplayName("평일 및 주말 여부를 확인한다.")
    void checkWeekdayAndWeekend(int inputDate, boolean isWeekday, boolean isWeekend) {
        // given
        Date date = Date.from(inputDate);

        // when
        boolean weekdayResult = date.isWeekday();
        boolean weekendResult = date.isWeekend();

        // then
        assertEquals(isWeekday, weekdayResult);
        assertEquals(isWeekend, weekendResult);
    }

    @ParameterizedTest
    @CsvSource({
            "1, 0",
            "15, 1400",
            "31, 3000"
    })
    @DisplayName("방문 날짜에서 할인 가격의 증가폭을 계산한다.")
    void calculateTotalIncrement(int inputDate, int expectedIncrement) {
        // given
        Date date = Date.from(inputDate);

        // when
        int totalIncrement = date.calculateTotalIncrement(100);

        // then
        assertEquals(expectedIncrement, totalIncrement);
    }

    @ParameterizedTest
    @CsvSource({
            "1, 1, true",
            "15, 15, true",
            "31, 30, false"
    })
    @DisplayName("날짜가 주어진 값과 같은지 확인한다.")
    void checkIfDateEquals(int inputDate, int compareDate, boolean expectedResult) {
        // given
        Date date = Date.from(inputDate);

        // when
        boolean result = date.equals(compareDate);

        // then
        assertEquals(expectedResult, result);
    }
}
