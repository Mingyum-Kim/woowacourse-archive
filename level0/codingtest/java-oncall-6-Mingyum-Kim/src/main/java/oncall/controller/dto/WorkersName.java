package oncall.controller.dto;

import java.util.List;

public record WorkersName(
        List<String> weekday,
        List<String> weekend
) {
}
