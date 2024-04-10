package christmas.global.constants;

import java.time.DayOfWeek;
import java.util.Arrays;
import java.util.List;

public enum DayType {
    WEEKDAY(Arrays.asList(
            DayOfWeek.SUNDAY,
            DayOfWeek.MONDAY,
            DayOfWeek.TUESDAY,
            DayOfWeek.WEDNESDAY,
            DayOfWeek.THURSDAY
    )),
    WEEKEND(Arrays.asList(
            DayOfWeek.FRIDAY,
            DayOfWeek.SATURDAY
    ));

    private final List<DayOfWeek> days;

    DayType(List<DayOfWeek> days) {
        this.days = days;
    }

    public static boolean contains(DayOfWeek actual, DayType expected) {
        return findDayType(actual).equals(expected);
    }

    private static DayType findDayType(DayOfWeek target) {
        return Arrays.stream(DayType.values())
                .filter(dayType -> dayType.containsDayOfWeek(target))
                .findAny()
                .orElseThrow(() -> new IllegalStateException());
    }

    private boolean containsDayOfWeek(DayOfWeek dayOfWeek) {
        return days.contains(dayOfWeek);
    }
}
