package oncall.domain.constants;

public enum Holiday {
    NEW_YEAR(Month.JANUARY, 1),
    INDEPENDENCE_MOVEMENT_DAY(Month.MARCH, 1),
    CHILDREN_DAY(Month.MAY, 5),
    MEMORIAL_DAY(Month.JUNE, 6),
    LIBERATION_DAY(Month.AUGUST, 15),
    NATIONAL_FOUNDATION_DAY(Month.OCTOBER, 3),
    HANGUL_DAY(Month.OCTOBER, 9),
    CHRISTMAS(Month.DECEMBER, 25);

    private final Month month;
    private final int day;

    Holiday(Month month, int day) {
        this.month = month;
        this.day = day;
    }

    /**
     * 입력한 월, 일이 법정 공휴일에 해당되는 지 확인하는 메서드
     */
    public static boolean isHoliday(Month month, int day) {
        for (Holiday holiday : Holiday.values()) {
            if (holiday.month.equals(month) && holiday.day == day) {
                return true;
            }
        }
        return false;
    }
}
