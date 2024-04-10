package oncall.domain.constants;

import java.util.Arrays;
import oncall.exception.CustomException;
import oncall.exception.ErrorMessage;

public enum Month {
    JANUARY(1, 31),
    FEBRUARY(2, 28),
    MARCH(3, 31),
    APRIL(4, 30),
    MAY(5, 31),
    JUNE(6, 30),
    JULY(7, 31),
    AUGUST(8, 31),
    SEPTEMBER(9, 30),
    OCTOBER(10, 31),
    NOVEMBER(11, 30),
    DECEMBER(12, 31);

    private final int month;
    private final int days;

    Month(int month, int days) {
        this.month = month;
        this.days = days;
    }

    public static Month from(int month) {
        return Arrays.stream(Month.values())
                .filter(element -> element.month == month)
                .findFirst()
                .orElseThrow(() -> CustomException.from(ErrorMessage.INVALID_INPUT_ERROR));
    }

    public int getMonth() {
        return month;
    }

    public int getDays() {
        return days;
    }
}
