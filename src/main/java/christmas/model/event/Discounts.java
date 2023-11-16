package christmas.model.event;

import static christmas.constants.others.DaysOfWeek.FRI;
import static christmas.constants.others.DaysOfWeek.SAT;
import static christmas.constants.others.DaysOfWeek.SUN;
import static christmas.constants.others.MarksAndConstants.DISCOUNT_LOW_LIMIT;
import static christmas.constants.others.MarksAndConstants.ONE_WEEK;
import static christmas.constants.others.MarksAndConstants.X_MAS;
import static christmas.model.event.EventName.CHRISTMAS;
import static christmas.model.event.EventName.SPECIAL;
import static christmas.model.event.EventName.WEEKDAY;
import static christmas.model.event.EventName.WEEKEND;
import static christmas.model.menu.MenuGroup.DESSERT;
import static christmas.model.menu.MenuGroup.MAIN_DISH;

import christmas.model.menu.MenuGroup;
import christmas.model.order.Order;
import java.util.Map;

public class Discounts {

    private final Order order;

    public Discounts(Order order) {
        this.order = order;
    }

    public void getDiscountBenefit(int date, Map<EventName, Integer> events) {
        int beforeDiscounts = order.getTotalCost();
        Map<MenuGroup, Integer> countTable = MenuGroup.getCountsByGroup(order.getOrder());
        if (beforeDiscounts >= DISCOUNT_LOW_LIMIT) {
            getDiscounts(date, events, countTable);
        }
    }

    private void getDiscounts(int date, Map<EventName, Integer> events, Map<MenuGroup, Integer> counts) {
        getChristmasDiscount(date, events);
        getSpecialDiscount(date, events);
        getWeekdayDiscount(date, events, counts);
        getWeekendDiscount(date, events, counts);
    }

    public void getChristmasDiscount(int date, Map<EventName, Integer> events) {
        if (date <= X_MAS) {
            events.put(CHRISTMAS, CHRISTMAS.getDiscount(date));
        }
    }

    public void getSpecialDiscount(int date, Map<EventName, Integer> events) {
        if (date % ONE_WEEK == SUN.getDay() || date == X_MAS) {
            events.put(SPECIAL, SPECIAL.getDiscount(date));
        }
    }

    public void getWeekdayDiscount(int date, Map<EventName, Integer> events, Map<MenuGroup, Integer> counts) {
        if (date % ONE_WEEK != FRI.getDay() && date % ONE_WEEK != SAT.getDay()) {
            if (counts.get(DESSERT) > 0) {
                events.put(WEEKDAY, WEEKDAY.getDiscount(counts.get(DESSERT)));
            }
        }
    }

    public void getWeekendDiscount(int date, Map<EventName, Integer> events, Map<MenuGroup, Integer> counts) {
        if (date % ONE_WEEK == FRI.getDay() || date % ONE_WEEK == SAT.getDay()) {
            if (counts.get(MAIN_DISH) > 0) {
                events.put(WEEKEND, WEEKEND.getDiscount(counts.get(MAIN_DISH)));
            }
        }
    }
}