package christmas.domain.promotion;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class EventBadgeTest {
    @Test
    @DisplayName("혜택 금액에 따라 적절한 이벤트 배지를 찾는다.")
    void findByTotalBenefits() {
        assertBadgeForBenefits(25_000, EventBadge.SANTA);
        assertBadgeForBenefits(20_000, EventBadge.SANTA);

        assertBadgeForBenefits(15_000, EventBadge.TREE);
        assertBadgeForBenefits(10_000, EventBadge.TREE);

        assertBadgeForBenefits(9_000, EventBadge.STAR);
        assertBadgeForBenefits(5_000, EventBadge.STAR);

        assertBadgeForBenefits(1_000, EventBadge.NONE);
        assertBadgeForBenefits(0, EventBadge.NONE);
    }

    private void assertBadgeForBenefits(int benefits, EventBadge expectedBadge) {
        // when
        EventBadge actualBadge = EventBadge.findByTotalBenefits(benefits);

        // then
        assertEquals(expectedBadge, actualBadge);
    }
}
