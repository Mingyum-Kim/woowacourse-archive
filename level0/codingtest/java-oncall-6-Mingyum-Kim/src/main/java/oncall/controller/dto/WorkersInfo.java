package oncall.controller.dto;

import oncall.domain.Workers;

public record WorkersInfo(
        Workers weekday,
        Workers weekend
) {
}
