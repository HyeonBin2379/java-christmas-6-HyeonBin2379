package christmas.model.event;

import static christmas.constants.others.MarksAndConstants.GIVEAWAY_LOW_LIMIT;

import christmas.model.menu.Menu;
import christmas.model.order.Order;
import java.util.EnumMap;
import java.util.Map;

public class Giveaway {

    private final Order order;
    private final Map<Menu, Integer> giveaway;

    public Giveaway(Order order) {
        this.order = order;
        this.giveaway = new EnumMap<>(Menu.class);
    }

    public void addMenu(String name, int quantity) {
        Menu menuName = Menu.findMenuName(name);
        if (isMatchedCondition(menuName, quantity)) {
            giveaway.put(menuName, quantity);
        }
    }

    private boolean isMatchedCondition(Menu menuName, int quantity) {
        return order.getTotalCost() >= GIVEAWAY_LOW_LIMIT && menuName != Menu.NONE && quantity > 0;
    }

    public int getSum() {
        int totalGiveAway = 0;
        for (Menu menu : giveaway.keySet()) {
            totalGiveAway += menu.getPrice() * giveaway.get(menu);
        }
        return totalGiveAway;
    }

    public Map<Menu, Integer> getGiveaway() {
        return giveaway;
    }
}