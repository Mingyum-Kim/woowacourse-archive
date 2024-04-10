package christmas.controller.dto.response.benefit;

import java.util.List;

public record BenefitsResponse(
        List<BenefitResponse> responses
) {
}
