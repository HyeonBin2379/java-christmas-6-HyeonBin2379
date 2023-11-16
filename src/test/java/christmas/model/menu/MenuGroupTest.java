package christmas.model.menu;

import static christmas.constants.others.MarksAndConstants.COMMA;
import static christmas.model.menu.MenuGroup.APPETIZER;
import static christmas.model.menu.MenuGroup.BEVERAGE;
import static christmas.model.menu.MenuGroup.DESSERT;
import static christmas.model.menu.MenuGroup.MAIN_DISH;
import static org.junit.jupiter.api.Assertions.assertEquals;

import christmas.model.order.Order;
import java.util.Arrays;
import java.util.Collections;
import java.util.Map;
import java.util.stream.Stream;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;

class MenuGroupTest {

    @ParameterizedTest
    @DisplayName("메뉴 그룹별 주문 수량의 총합을 정확히 계산했는지 확인")
    @MethodSource("sampleCountsByMenuGroup")
    void getCountsByGroup_test(String input, Map<MenuGroup, Integer> expected) {
        Order given = new Order();

        given.setValidOrderTable(Arrays.asList(input.split(COMMA)));
        Map<MenuGroup, Integer> result = MenuGroup.getCountsByGroup(given.getOrder());

        assertEquals(result, expected);
    }

    @ParameterizedTest
    @DisplayName("주어진 메뉴가 속한 메뉴 그룹명을 정확히 반환하는지 확인")
    @CsvSource(value = {
            "'시저샐러드', '에피타이저'", "'티본스테이크', '메인'",
            "'초코케이크', '디저트'", "'제로콜라', '음료'"
    })
    void findMenuGroup_test(String given, String expected) {
        MenuGroup result = MenuGroup.findMenuGroup(given);
        assertEquals(result.getGroupName(), expected);
    }

    private static Stream<Arguments> sampleCountsByMenuGroup() {
        Map<MenuGroup, Integer> expected = Collections.unmodifiableMap(
                Map.of(APPETIZER, 1, MAIN_DISH, 2, DESSERT, 2, BEVERAGE, 1)
        );
        return Stream.of(
                Arguments.of("티본스테이크-1,바비큐립-1,시저샐러드-1,초코케이크-2,제로콜라-1", expected)
        );
    }
}