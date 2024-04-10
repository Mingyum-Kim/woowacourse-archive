package christmas.controller.dto.request;

import java.util.List;

public record OrdersRequest(
        List<OrderRequest> orders
) {
}
