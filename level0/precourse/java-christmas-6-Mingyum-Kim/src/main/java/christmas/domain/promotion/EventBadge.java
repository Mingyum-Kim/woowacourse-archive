package christmas.domain.promotion;

import java.util.Arrays;

/**
 * 이벤트 배지의 이름과 배지를 부여하는 최소 기준 금액을 저장하는 클래스
 */
public enum EventBadge {
    SANTA("산타", 20_000),
    TREE("트리", 10_000),
    STAR("별", 5_000),
    NONE("없음", 0);

    private final String name;
    private final int minAmount;

    EventBadge(String name, int minAmount) {
        this.name = name;
        this.minAmount = minAmount;
    }

    /**
     * 금액이 큰 배지부터 순회하며, 혜택 금액이 배지의 기준 금액보다 크다면 바로 발급하는 메서드
     *
     * @param benefits 총 혜택 금액
     * @return 이벤트 배지
     */
    public static EventBadge findByTotalBenefits(int benefits) {
        return Arrays.stream(values())
                .filter(badge -> benefits >= badge.minAmount)
                .findFirst()
                .orElse(NONE);
    }

    public String getName() {
        return this.name;
    }
}
