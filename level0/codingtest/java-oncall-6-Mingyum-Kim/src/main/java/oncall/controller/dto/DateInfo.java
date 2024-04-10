package oncall.controller.dto;

import oncall.domain.constants.CustomDayOfWeek;
import oncall.domain.constants.Month;

public record DateInfo(
        Month month,
        CustomDayOfWeek startDayOfWeek
) {
}
