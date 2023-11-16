package christmas.model.event;

import java.util.function.Function;

public enum EventName {
    CHRISTMAS("크리스마스 디데이 할인", date -> (-1) * (900 + 100 * date)),
    SPECIAL("특별 할인", date -> (-1) * 1000),
    WEEKDAY("평일 할인", dessert -> (-1) * 2023 * dessert),
    WEEKEND("주말 할인", mainDish -> (-1) * 2023 * mainDish),
    GIVEAWAY("증정 이벤트", discount -> 0);

    private final String eventName;
    private final Function<Integer, Integer> discount;

    EventName(String eventName, Function<Integer, Integer> discount) {
        this.eventName = eventName;
        this.discount = discount;
    }

    public String getEventName() {
        return eventName;
    }

    public int getDiscount(int count) {
        return discount.apply(count);
    }
}
