package christmas.domain.customer;

import christmas.global.constants.DayType;
import christmas.global.exception.CustomException;
import christmas.global.exception.ErrorMessage;
import java.time.DayOfWeek;
import java.time.LocalDate;

public class Date {
    private static final int YEAR = 2023;
    private static final int MONTH = 12;
    private static final int START_DATE = 1;
    private static final int END_DATE = 31;

    private final int date;

    private Date(int date) {
        Validator.validate(date);
        this.date = date;
    }

    public static Date from(int date) {
        return new Date(date);
    }

    /**
     * 방문 날짜가 특정 기간 내에 있는지 확인하는 메서드
     *
     * @param startDate 시작 날짜
     * @param endDate   종료 날짜
     * @return 방문 날짜가 시작 날짜와 종료 날짜 사이의 기간에 속하면 true, 그렇지 않으면 false
     */
    public boolean isInRange(int startDate, int endDate) {
        return isGreaterThanOrEqualTo(startDate) && isLessThanOrEqualTo(endDate);
    }

    private boolean isGreaterThanOrEqualTo(int date) {
        return this.date >= date;
    }

    private boolean isLessThanOrEqualTo(int date) {
        return this.date <= date;
    }

    /**
     * 방문 날짜의 크리스마스 디데이 할인 이벤트의 할인 가격을 계산하는 메서드
     *
     * @param increment 하루마다 오르는 할인 가격의 증가폭
     * @return 방문 날짜의 할인 가격
     */
    public int calculateTotalIncrement(int increment) {
        return (this.date - 1) * increment;
    }

    /**
     * 방문 날짜가 평일인지 확인하는 메서드
     *
     * @return 방문 날짜가 평일이면 true, 그렇지 않으면 false
     */
    public boolean isWeekday() {
        return DayType.contains(findDayOfWeek(date), DayType.WEEKDAY);
    }

    /**
     * 방문 날짜가 주말인지 확인하는 메서드
     *
     * @return 방문 날짜가 주말이면 true, 그렇지 않으면 false
     */
    public boolean isWeekend() {
        return DayType.contains(findDayOfWeek(date), DayType.WEEKEND);
    }


    private DayOfWeek findDayOfWeek(int date) {
        LocalDate localDate = LocalDate.of(YEAR, MONTH, date);
        return localDate.getDayOfWeek();
    }

    /**
     * 방문 날짜의 동등성을 확인하는 메서드
     *
     * @param date 동등성을 비교할 날짜
     * @return 날짜가 동등하면 true, 그렇지 않으면 false
     */
    public boolean equals(int date) {
        return this.date == date;
    }

    public int getDate() {
        return date;
    }

    private static class Validator {
        private static int validate(int date) {
            validateRange(date);
            return date;
        }

        private static void validateRange(int date) {
            if (isInvalidRange(date)) {
                throw CustomException.from(ErrorMessage.INVALID_DATE_ERROR);
            }
        }

        private static boolean isInvalidRange(int date) {
            return date < START_DATE || date > END_DATE;
        }
    }
}
