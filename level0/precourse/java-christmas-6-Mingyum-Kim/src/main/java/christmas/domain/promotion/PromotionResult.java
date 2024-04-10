package christmas.domain.promotion;

import christmas.controller.dto.response.benefit.BenefitResponse;

public class PromotionResult<T extends Benefit> {
    private Promotion promotion;
    private T benefit;

    private PromotionResult(Promotion promotion, T benefit) {
        this.promotion = promotion;
        this.benefit = benefit;
    }

    public static PromotionResult of(Promotion promotion, Benefit benefit) {
        return new PromotionResult(promotion, benefit);
    }

    public BenefitResponse toBenefitResponse() {
        if (benefit instanceof Discount) {
            return new BenefitResponse(
                    promotion.getName(),
                    ((Discount) benefit).getPrice()
            );
        }
        return new BenefitResponse(
                promotion.getName(),
                ((Gift) benefit).getPrice()
        );
    }

    /**
     * 혜택 종류를 확인하는 메서드
     *
     * @param clazz 혜택 종류를 저장하는 클래스
     * @return 입력된 혜택 종류에 해당하면 true, 그렇지 않으면 false
     */
    public boolean isInstanceOf(Class<?> clazz) {
        return clazz.isInstance(benefit);
    }

    /**
     * 증정 혜택 객체를 다운 캐스팅하여 반환하는 메서드
     *
     * @return 증정 혜택 객체
     */
    public Gift getGift() {
        return (Gift) benefit;
    }

    /**
     * 할인 혜택 객체를 다운 캐스팅하여 반환하는 메서드
     *
     * @return 할인 혜택 객체
     */
    public Discount getDiscount() {
        return (Discount) benefit;
    }
}
