package christmas.controller.dto.response.gift;

import java.util.List;

public record GiftsResponse(
        List<GiftResponse> responses
) {
}
