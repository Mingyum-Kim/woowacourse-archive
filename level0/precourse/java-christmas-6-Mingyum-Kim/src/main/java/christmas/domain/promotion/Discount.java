package christmas.domain.promotion;

/**
 * 이벤트의 결과로 할인된 금액을 저장하는 클래스
 */
public class Discount extends Benefit {
    private final int price;

    private Discount(int price) {
        this.price = price;
    }

    public static Discount from(int price) {
        return new Discount(price);
    }

    public int getPrice() {
        return price;
    }
}
