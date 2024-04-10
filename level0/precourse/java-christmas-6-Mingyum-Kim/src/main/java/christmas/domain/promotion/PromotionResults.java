package christmas.domain.promotion;

import christmas.controller.dto.response.benefit.BenefitResponse;
import christmas.controller.dto.response.benefit.BenefitsResponse;
import christmas.controller.dto.response.gift.GiftResponse;
import christmas.controller.dto.response.gift.GiftsResponse;
import christmas.domain.customer.Orders;
import java.util.List;

public class PromotionResults {
    private List<PromotionResult> results;

    private PromotionResults(List<PromotionResult> results) {
        this.results = results;
    }

    public static PromotionResults from(List<PromotionResult> promotionResponses) {
        return new PromotionResults(promotionResponses);
    }

    /**
     * 결제 금액에서 할인 금액을 차감한 예상 결제 금액을 계산하는 메서드
     *
     * @param orders 주문 내역
     * @return 예상 결제 금액
     */
    public int calculatePayment(Orders orders) {
        return orders.calculateOrdersCost() - calculateDiscount();
    }

    /**
     * 증정 혜택과 할인 혜택으로 인한 총 혜택 금액을 계산하는 메서드
     *
     * @return 총 혜택 금액
     */
    public int calculateTotalBenefits() {
        return calculateDiscount() + calculateGiftPrice();
    }

    private int calculateDiscount() {
        return results.stream()
                .filter(result -> result.isInstanceOf(Discount.class))
                .mapToInt(result -> result.getDiscount().getPrice())
                .sum();
    }

    private int calculateGiftPrice() {
        return results.stream()
                .filter(result -> result.isInstanceOf(Gift.class))
                .mapToInt(result -> result.getGift().getPrice())
                .sum();
    }

    /**
     * 증정품의 종류와 개수를 저장한 DTO 클래스를 생성하는 메서드
     *
     * @return GiftsResponse 객체
     */
    public GiftsResponse toGiftsResponse() {
        List<GiftResponse> giftResponses = toGiftResponses();
        return new GiftsResponse(giftResponses);
    }

    private List<GiftResponse> toGiftResponses() {
        return results.stream()
                .filter(result -> result.isInstanceOf(Gift.class))
                .map(result -> result.getGift())
                .map(Gift::toGiftResponse)
                .toList();
    }

    /**
     * 이벤트와 혜택 금액을 저장한 DTO 클래스를 생성하는 메서드
     *
     * @return 혜택 내역
     */
    public BenefitsResponse toBenefitsResponse() {
        List<BenefitResponse> benefitResponses = toBenefitResponses();
        return new BenefitsResponse(benefitResponses);
    }

    private List<BenefitResponse> toBenefitResponses() {
        return results.stream()
                .map(result -> result.toBenefitResponse())
                .toList();
    }
}
