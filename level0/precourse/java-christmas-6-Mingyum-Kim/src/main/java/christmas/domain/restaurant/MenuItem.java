package christmas.domain.restaurant;

import christmas.global.exception.CustomException;
import christmas.global.exception.ErrorMessage;
import java.util.Arrays;

public enum MenuItem {
    MUSHROOM_SOUP("양송이수프", 6_000),
    TAPAS("타파스", 5_500),
    CAESAR_SALAD("시저샐러드", 8_000),

    TBONE_STEAK("티본스테이크", 55_000),
    BARBECUE_RIBS("바비큐립", 54_000),
    SEAFOOD_PASTA("해산물파스타", 35_000),
    CHRISTMAS_PASTA("크리스마스파스타", 25_000),

    CHOCOLATE_CAKE("초코케이크", 15_000),
    ICE_CREAM("아이스크림", 5_000),

    ZERO_COLA("제로콜라", 3_000),
    RED_WINE("레드와인", 60_000),
    CHAMPAGNE("샴페인", 25_000),

    NONE("없음", 0);

    private String name;
    private int cost;

    MenuItem(String name, int cost) {
        this.name = name;
        this.cost = cost;
    }

    public String getName() {
        return name;
    }

    public int getCost() {
        return cost;
    }

    public static MenuItem findByName(String name) {
        return Arrays.stream(MenuItem.values())
                .filter(menuItem -> menuItem.getName().equals(name))
                .findAny()
                .orElseThrow(() -> CustomException.from(ErrorMessage.INVALID_ORDER_ERROR));
    }
}
