package christmas.model.menu;

import java.util.Arrays;

public enum Menu {
    SOUP("양송이수프", 6000),
    TAPAS("타파스", 5500),
    SALAD("시저샐러드", 8000),
    T_STAKE("티본스테이크", 55000),
    BARBEQUE_RIB("바비큐립", 54000),
    SEAFOOD_PASTA("해산물파스타", 35000),
    CHRISTMAS_PASTA("크리스마스파스타", 25000),
    CHOCO_CAKE("초코케이크", 15000),
    ICE_CREAM("아이스크림", 5000),
    ZERO_COKE("제로콜라", 3000),
    RED_WINE("레드와인", 60000),
    CHAMPAGNE("샴페인", 25000),
    NONE("없음", 0);

    private final String name;
    private final int price;

    Menu(String name, int price) {
        this.name = name;
        this.price = price;
    }

    public static Menu findMenuName(String order) {
        return Arrays.stream(Menu.values())
                .filter(menu -> menu.hasMenuName(order))
                .findAny()
                .orElse(NONE);
    }

    private boolean hasMenuName(String order) {
        return name.equals(order);
    }

    public String getName() {
        return name;
    }

    public int getPrice() {
        return price;
    }
}
