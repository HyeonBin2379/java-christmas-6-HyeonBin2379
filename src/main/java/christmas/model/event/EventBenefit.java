package christmas.model.event;

import static christmas.model.event.EventName.GIVEAWAY;

import christmas.model.order.Order;
import java.util.EnumMap;
import java.util.Map;

public class EventBenefit {

    private final Discounts discount;
    private final Map<EventName, Integer> events;

    public EventBenefit(Order totalOrder) {
        this.discount = new Discounts(totalOrder);
        this.events = new EnumMap<>(EventName.class);
    }

    public void addBenefit(int date, Giveaway giveaway) {
        addEventDiscount(date);
        addEventGiveaway(giveaway);
    }

    public void addEventDiscount(int date) {
        discount.getDiscountBenefit(date, events);
    }

    public void addEventGiveaway(Giveaway giveaway) {
        if (giveaway.getSum() > 0) {
            events.put(GIVEAWAY, (-1) * giveaway.getSum());
        }
    }

    public int getTotalBenefit() {
        int totalBenefit = 0;
        for (EventName eventName : events.keySet()) {
            totalBenefit += events.get(eventName);
        }
        return totalBenefit;
    }

    public int getAfterDiscounts(int beforeDiscount, Giveaway giveaway) {
        return beforeDiscount + getTotalBenefit() + giveaway.getSum();
    }

    public Map<EventName, Integer> getEventTable() {
        return events;
    }
}
