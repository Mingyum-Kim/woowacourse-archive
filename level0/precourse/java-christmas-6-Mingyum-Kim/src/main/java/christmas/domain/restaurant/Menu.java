package christmas.domain.restaurant;

import java.util.Arrays;
import java.util.List;

public enum Menu {
    APPETIZER(Arrays.asList(
            MenuItem.MUSHROOM_SOUP,
            MenuItem.TAPAS,
            MenuItem.CAESAR_SALAD)
    ),
    MAIN_COURSE(Arrays.asList(
            MenuItem.TBONE_STEAK,
            MenuItem.BARBECUE_RIBS,
            MenuItem.SEAFOOD_PASTA,
            MenuItem.CHRISTMAS_PASTA)
    ),
    DESSERT(Arrays.asList(
            MenuItem.CHOCOLATE_CAKE,
            MenuItem.ICE_CREAM)
    ),
    BEVERAGE(Arrays.asList(
            MenuItem.ZERO_COLA,
            MenuItem.RED_WINE,
            MenuItem.CHAMPAGNE)
    );

    private List<MenuItem> menuItems;

    Menu(List<MenuItem> menuItems) {
        this.menuItems = menuItems;
    }

    public List<MenuItem> getMenuItems() {
        return menuItems;
    }

    /**
     * 메뉴 아이템이 특정 메뉴 분류에 확인하는 메서드
     *
     * @param menu     메뉴 분류
     * @param menuItem 메뉴 아이템
     * @return 메뉴 분류에 속하는 메뉴 아이템이면 true, 그렇지 않으면 false
     */
    public static boolean contains(Menu menu, MenuItem menuItem) {
        return findByMenuItem(menuItem).equals(menu);
    }

    private static Menu findByMenuItem(MenuItem menuItem) {
        return Arrays.stream(values())
                .filter(menu -> menu.menuItems.contains(menuItem))
                .findFirst()
                .orElseThrow(() -> new IllegalStateException());
    }
}
