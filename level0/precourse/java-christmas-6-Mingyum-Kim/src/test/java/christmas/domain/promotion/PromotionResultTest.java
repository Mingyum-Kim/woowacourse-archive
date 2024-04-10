package christmas.domain.promotion;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import christmas.controller.dto.response.benefit.BenefitResponse;
import christmas.domain.customer.Count;
import christmas.domain.restaurant.MenuItem;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

class PromotionResultTest {

    @Nested
    @DisplayName("BenefitResponse 변환 테스트")
    class BenefitResponseTests {
        @Test
        @DisplayName("할인 혜택인 경우 BenefitResponse를 올바르게 생성한다.")
        void createBenefitResponseForDiscount() {
            // given
            Promotion promotion = Promotion.SPECIAL;
            Discount discount = Discount.from(1000);
            PromotionResult<Discount> promotionResult = PromotionResult.of(promotion, discount);

            // when
            BenefitResponse response = promotionResult.toBenefitResponse();

            // then
            assertEquals(Promotion.SPECIAL.getName(), response.promotion());
            assertEquals(1000, response.price());
        }

        @Test
        @DisplayName("증정 혜택인 경우 BenefitResponse를 올바르게 생성한다.")
        void createBenefitResponseForGift() {
            // given
            Promotion promotion = Promotion.GIFT;
            Gift gift = Gift.of(MenuItem.CHAMPAGNE, Count.valueOf(1));
            PromotionResult<Discount> promotionResult = PromotionResult.of(promotion, gift);

            // when
            BenefitResponse response = promotionResult.toBenefitResponse();

            // then
            assertEquals(Promotion.GIFT.getName(), response.promotion());
            assertEquals(gift.getPrice(), response.price());
        }
    }

    @Nested
    class InstanceTests {
        @Test
        @DisplayName("증정 이벤트의 결과로 생성된 객체에서 혜택의 인스턴스를 확인한다.")
        void checkIsInstanceOfGift() {
            // given
            Promotion promotion = Promotion.GIFT;
            Benefit benefit = Gift.of(MenuItem.CHAMPAGNE, Count.valueOf(1));
            PromotionResult<? extends Benefit> promotionResult = PromotionResult.of(promotion, benefit);

            // when & then
            assertTrue(promotionResult.isInstanceOf(Gift.class));
        }

        @Test
        @DisplayName("할인 이벤트의 결과로 생성된 객체에서 혜택의 인스턴스를 확인한다.")
        void checkIsInstanceOfDiscount() {
            // given
            Promotion promotion = Promotion.CHRISTMAS;
            Benefit benefit = Discount.from(1_000);
            PromotionResult<? extends Benefit> promotionResult = PromotionResult.of(promotion, benefit);

            // when & then
            assertTrue(promotionResult.isInstanceOf(Discount.class));
        }
    }


    @Nested
    @DisplayName("증정 혜택과 할인 혜택 객체 조회 테스트")
    class GetGiftAndDiscountTests {
        @Test
        @DisplayName("Gift 객체를 성공적으로 다운 캐스팅하여 반환한다.")
        void getGift() {
            // given
            Promotion promotion = Promotion.GIFT;
            Benefit benefit = Gift.of(MenuItem.CHAMPAGNE, Count.valueOf(1));
            PromotionResult<? extends Benefit> promotionResult = PromotionResult.of(promotion, benefit);

            // when
            Gift gift = promotionResult.getGift();

            // then
            assertNotNull(gift);
            assertEquals(MenuItem.CHAMPAGNE.getCost() * 1, gift.getPrice());
        }

        @Test
        @DisplayName("Discount 객체를 성공적으로 다운 캐스팅하여 반환한다.")
        void getDiscount() {
            // given
            Promotion promotion = Promotion.CHRISTMAS;
            Benefit benefit = Discount.from(1_000);
            PromotionResult<? extends Benefit> promotionResult = PromotionResult.of(promotion, benefit);

            // when
            Discount discount = promotionResult.getDiscount();

            // then
            assertNotNull(discount);
            assertEquals(1000, discount.getPrice());
        }
    }
}
