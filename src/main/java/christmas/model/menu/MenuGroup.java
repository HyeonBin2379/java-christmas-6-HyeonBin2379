package christmas.model.menu;

import static christmas.constants.others.MarksAndConstants.INITIAL_VALUE_ZERO;
import static christmas.model.menu.Menu.BARBEQUE_RIB;
import static christmas.model.menu.Menu.CHAMPAGNE;
import static christmas.model.menu.Menu.CHOCO_CAKE;
import static christmas.model.menu.Menu.CHRISTMAS_PASTA;
import static christmas.model.menu.Menu.ICE_CREAM;
import static christmas.model.menu.Menu.RED_WINE;
import static christmas.model.menu.Menu.SALAD;
import static christmas.model.menu.Menu.SEAFOOD_PASTA;
import static christmas.model.menu.Menu.SOUP;
import static christmas.model.menu.Menu.TAPAS;
import static christmas.model.menu.Menu.T_STAKE;
import static christmas.model.menu.Menu.ZERO_COKE;

import java.util.Arrays;
import java.util.Collections;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;

public enum MenuGroup {
    APPETIZER("에피타이저", Arrays.asList(SOUP, TAPAS, SALAD)),
    MAIN_DISH("메인", Arrays.asList(T_STAKE, BARBEQUE_RIB, SEAFOOD_PASTA, CHRISTMAS_PASTA)),
    DESSERT("디저트", Arrays.asList(CHOCO_CAKE, ICE_CREAM)),
    BEVERAGE("음료", Arrays.asList(ZERO_COKE, RED_WINE, CHAMPAGNE)),
    NONE("해당없음", Collections.EMPTY_LIST);

    private final String groupName;
    private final List<Menu> menuNames;

    MenuGroup(String groupName, List<Menu> menuNames) {
        this.groupName = groupName;
        this.menuNames = menuNames;
    }

    public static Map<MenuGroup, Integer> getCountsByGroup(Map<Menu, Integer> orderTable) {
        Map<MenuGroup, Integer> countTable = initializeCountTable();
        for (Menu menuName : orderTable.keySet()) {
            getCountsByEachGroup(menuName, orderTable, countTable);
        }
        return countTable;
    }

    private static void getCountsByEachGroup(Menu name, Map<Menu, Integer> order, Map<MenuGroup, Integer> count) {
        MenuGroup group = findMenuGroup(name.getName());
        int eachCount = count.get(group);
        count.put(group, eachCount + order.get(name));
    }

    private static Map<MenuGroup, Integer> initializeCountTable() {
        Map<MenuGroup, Integer> countTable = new EnumMap<>(MenuGroup.class);
        for (MenuGroup group : MenuGroup.values()) {
            if (group == NONE) {
                continue;
            }
            countTable.put(group, INITIAL_VALUE_ZERO);
        }
        return countTable;
    }

    public static MenuGroup findMenuGroup(String name) {
        return Arrays.stream(MenuGroup.values())
                .filter(menuGroup -> menuGroup.hasMenu(name))
                .findFirst()
                .orElse(NONE);
    }

    private boolean hasMenu(String name) {
        return menuNames.stream()
                .anyMatch(menu -> menu.equals(Menu.findMenuName(name)));
    }

    public String getGroupName() {
        return groupName;
    }

    public List<Menu> getList() {
        return menuNames;
    }
}