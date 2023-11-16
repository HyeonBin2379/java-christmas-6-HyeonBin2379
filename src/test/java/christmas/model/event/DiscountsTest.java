package christmas.model.event;

import static christmas.model.event.EventName.CHRISTMAS;
import static christmas.model.event.EventName.SPECIAL;
import static christmas.model.event.EventName.WEEKDAY;
import static christmas.model.event.EventName.WEEKEND;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import christmas.model.menu.Menu;
import christmas.model.menu.MenuGroup;
import christmas.model.order.Order;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;

class DiscountsTest {

    private Order orderSample;
    private EventBenefit eventSample;
    private Discounts discounts;

    @BeforeEach
    void setUp() {
        orderSample = new Order();
        eventSample = new EventBenefit(orderSample);
        discounts = new Discounts(orderSample);
    }

    @ParameterizedTest
    @DisplayName("주어진 테스트케이스는 제공 가능한 할인 혜택이 없음")
    @MethodSource("sampleNotDiscounted")
    void getDiscountBenefit_notAddTest(int dateInput, List<String> givenOrder) {
        orderSample.setValidOrderTable(givenOrder);
        discounts.getDiscountBenefit(dateInput, eventSample.getEventTable());

        Map<EventName, Integer> result = eventSample.getEventTable();
        assertTrue(result.isEmpty());
    }

    @ParameterizedTest
    @DisplayName("총주문금액이 10,000원 이상일 때, 1일부터 25일까지만 크리스마스 디데이 할인 적용")
    @MethodSource("sampleChristmasDiscount")
    void getChristmasDiscount_addTest(int dateInput, List<String> givenOrder, Map<EventName, Integer> expected) {
        orderSample.setValidOrderTable(givenOrder);
        discounts.getChristmasDiscount(dateInput, eventSample.getEventTable());

        Map<EventName, Integer> result = eventSample.getEventTable();
        assertEquals(result, expected);
    }

    @ParameterizedTest
    @DisplayName("총주문금액이 10,000원 이상일 때, 매주 일요일 또는 크리스마스에만 특별 할인 적용")
    @ValueSource(ints = {3,10,17,24,25,31})
    void getSpecialDiscount_addTest(int dateInput) {
        orderSample.setValidOrderTable(List.of("티본스테이크-1","바비큐립-1","초코케이크-2","제로콜라-1"));
        discounts.getSpecialDiscount(dateInput, eventSample.getEventTable());

        Map<EventName, Integer> result = eventSample.getEventTable();
        Map<EventName, Integer> expected = Map.of(SPECIAL, -1000);
        assertEquals(result, expected);
    }

    @ParameterizedTest
    @DisplayName("총주문금액이 10,000원 이상일 때, 일요일도, 크리스마스도 아니면 특별 할인 미적용")
    @ValueSource(ints = {2,9,16,23,26})
    void getSpecialDiscount_notAddTest(int dateInput) {
        orderSample.setValidOrderTable(List.of("티본스테이크-1","바비큐립-1","초코케이크-2","제로콜라-1"));
        discounts.getSpecialDiscount(dateInput, eventSample.getEventTable());

        Map<EventName, Integer> result = eventSample.getEventTable();
        Map<EventName, Integer> expected = Map.of();
        assertEquals(result, expected);
    }

    @ParameterizedTest
    @DisplayName("총주문금액이 10,000원 이상일 때, 일요일~목요일에 주문한 디저트 메뉴에 대해 평일 할인 적용")
    @MethodSource("sampleWeekdayDiscount")
    void getWeekdayDiscount_addTest(int dateInput, List<String> givenOrder, Map<EventName, Integer> expected) {
        orderSample.setValidOrderTable(givenOrder);
        Map<MenuGroup, Integer> givenCounts = MenuGroup.getCountsByGroup(orderSample.getOrder());
        discounts.getWeekdayDiscount(dateInput, eventSample.getEventTable(), givenCounts);

        Map<EventName, Integer> result = eventSample.getEventTable();
        assertEquals(result, expected);
    }

    @ParameterizedTest
    @DisplayName("총주문금액이 10,000원 이상일 때, 금요일~토요일에 주문한 메인 요리에 대해 주말 할인 적용")
    @MethodSource("sampleWeekendDiscount")
    void getWeekendDiscount_addTest(int dateInput, List<String> givenOrder, Map<EventName, Integer> expected) {
        orderSample.setValidOrderTable(givenOrder);
        Map<MenuGroup, Integer> givenCounts = MenuGroup.getCountsByGroup(orderSample.getOrder());
        discounts.getWeekendDiscount(dateInput, eventSample.getEventTable(), givenCounts);

        Map<EventName, Integer> result = eventSample.getEventTable();
        assertEquals(result, expected);
    }

    @AfterEach
    void finish() {
        Map<Menu, Integer> orderResult = orderSample.getOrder();
        Map<EventName, Integer> eventResult = eventSample.getEventTable();
        orderResult.clear();
        eventResult.clear();
    }

    private static Stream<Arguments> sampleNotDiscounted() {
        return Stream.of(
                Arguments.of(25, List.of("타파스-1","제로콜라-1")),
                Arguments.of(26, List.of("해산물파스타-1", "타파스-1", "제로콜라-1")),
                Arguments.of(27, List.of("해산물파스타-1", "타파스-1", "제로콜라-1")),
                Arguments.of(28, List.of("해산물파스타-1", "타파스-1", "제로콜라-1")),
                Arguments.of(29, List.of("초코케이크-1", "타파스-1", "제로콜라-1")),
                Arguments.of(30, List.of("초코케이크-1", "타파스-1", "제로콜라-1"))
        );
    }
    private static Stream<Arguments> sampleChristmasDiscount() {
        return Stream.of(
                Arguments.of(1, List.of("해산물파스타-1"), Map.of(CHRISTMAS, -1000)),
                Arguments.of(25, List.of("해산물파스타-1"), Map.of(CHRISTMAS, -3400)),
                Arguments.of(26, List.of("해산물파스타-1"), Map.of())
        );
    }

    private static Stream<Arguments> sampleWeekdayDiscount() {
        return Stream.of(
                Arguments.of(3, List.of("초코케이크-2", "타파스-1", "제로콜라-1"), Map.of(WEEKDAY, -4046)),
                Arguments.of(7, List.of("초코케이크-2", "타파스-1", "제로콜라-1"), Map.of(WEEKDAY, -4046)),

                Arguments.of(2, List.of("초코케이크-2", "타파스-1", "제로콜라-1"), Map.of()),
                Arguments.of(3, List.of("해산물파스타-2", "타파스-1", "제로콜라-1"), Map.of()),
                Arguments.of(7, List.of("해산물파스타-2", "타파스-1", "제로콜라-1"), Map.of()),
                Arguments.of(8, List.of("초코케이크-2", "타파스-1", "제로콜라-1"), Map.of())
        );
    }

    private static Stream<Arguments> sampleWeekendDiscount() {
        return Stream.of(
                Arguments.of(8, List.of("해산물파스타-2", "타파스-1", "제로콜라-1"), Map.of(WEEKEND, -4046)),
                Arguments.of(9, List.of("해산물파스타-2", "타파스-1", "제로콜라-1"), Map.of(WEEKEND, -4046)),

                Arguments.of(7, List.of("해산물파스타-2", "타파스-1", "제로콜라-1"), Map.of()),
                Arguments.of(8, List.of("초코케이크-2", "타파스-1", "제로콜라-1"), Map.of()),
                Arguments.of(9, List.of("초코케이크-2", "타파스-1", "제로콜라-1"), Map.of()),
                Arguments.of(10, List.of("해산물파스타-2", "타파스-1", "제로콜라-1"), Map.of())
        );
    }
}