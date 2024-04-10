package christmas.controller.dto.response.order;

import java.util.List;

public record CustomerResponse(
        int date,
        List<OrderResponse> orders
) {
}
