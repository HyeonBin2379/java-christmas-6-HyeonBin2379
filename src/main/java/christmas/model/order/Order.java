package christmas.model.order;

import static christmas.constants.others.MarksAndConstants.INITIAL_VALUE_ZERO;
import static christmas.validator.OrderFormatValidator.validateHyphen;
import static christmas.validator.OrderValidator.validateMenuName;
import static christmas.validator.OrderValidator.validateQuantity;
import static christmas.validator.OrderValidator.validateTotalOrder;

import christmas.model.menu.Menu;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;

public class Order {

    private final Map<Menu, Integer> orderTable;

    public Order() {
        this.orderTable = new EnumMap<>(Menu.class);
    }

    public void setValidOrderTable(List<String> allOrders) {
        setAllOrderToken(allOrders);
        validateTotalOrder(getTotalCounts(), orderTable);
    }

    public void setAllOrderToken(List<String> allOrders) {
        for (String order : allOrders) {
            setEachOrderToken(order);
        }
    }

    private void setEachOrderToken(String order) {
        String[] tokens = validateHyphen(order);
        Menu menuName = validateMenuName(tokens[0], orderTable);
        int quantity = validateQuantity(tokens[1]);

        orderTable.put(menuName, quantity);
    }

    public int getTotalCounts() {
        int totalCounts = INITIAL_VALUE_ZERO;
        for (Menu menuName : orderTable.keySet()) {
            totalCounts += orderTable.get(menuName);
        }
        return totalCounts;
    }

    public int getTotalCost() {
        int totalCost = INITIAL_VALUE_ZERO;
        for (Menu menuName : orderTable.keySet()) {
            totalCost += menuName.getPrice() * orderTable.get(menuName);
        }
        return totalCost;
    }

    public Map<Menu, Integer> getOrder() {
        return orderTable;
    }

    public void clearOrder() {
        orderTable.clear();
    }
}
