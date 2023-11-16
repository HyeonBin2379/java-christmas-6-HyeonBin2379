package christmas.model.order;

import static christmas.model.menu.Menu.BARBEQUE_RIB;
import static christmas.model.menu.Menu.CHOCO_CAKE;
import static christmas.model.menu.Menu.TAPAS;
import static christmas.model.menu.Menu.ZERO_COKE;
import static org.junit.jupiter.api.Assertions.assertEquals;

import christmas.model.menu.Menu;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class OrderTest {

    private Order sample;

    @BeforeEach
    void setUp() {
        sample = new Order();
    }

    @ParameterizedTest
    @DisplayName("주문 메뉴가 유효하면 메뉴 이름이 키, 주문 수량이 값인 Map을 생성")
    @MethodSource("sampleOrders")
    void setValidOrderTable_test(List<String> given, Map<Menu, Integer> expected) {
        sample.setValidOrderTable(given);
        assertEquals(sample.getOrder(), expected);
    }

    @ParameterizedTest
    @DisplayName("주문한 메뉴 개수의 총합을 정확히 계산했는지 확인")
    @MethodSource("sampleOrderedMenu")
    void getTotalCounts_test(List<String> input, int expected) {
        sample.setValidOrderTable(input);
        assertEquals(sample.getTotalCounts(), expected);
    }

    @ParameterizedTest
    @DisplayName("주문한 메뉴에 관한 할인 전 총 주문 금액을 정확히 계산했는지 확인")
    @MethodSource("sampleTotalOrderCost")
    void getTotalCost_test(List<String> input, int expected) {
        sample.setValidOrderTable(input);
        assertEquals(sample.getTotalCost(), expected);
    }

    @AfterEach
    void finish() {
        Map<Menu, Integer> result = sample.getOrder();
        result.clear();
    }

    private static Stream<Arguments> sampleOrders() {
        List<String> given = List.of("타파스-2", "제로콜라-5", "바비큐립-2", "초코케이크-1");
        Map<Menu, Integer> expected = Map.of(
                TAPAS, 2, ZERO_COKE, 5,
                BARBEQUE_RIB, 2, CHOCO_CAKE, 1
        );
        return Stream.of(
                Arguments.of(given, expected)
        );
    }
    private static Stream<Arguments> sampleOrderedMenu() {
        return Stream.of(
                Arguments.of(List.of("해산물파스타-1"), 1),
                Arguments.of(List.of("크리스마스파스타-3","레드와인-5","시저샐러드-4"), 12),
                Arguments.of(List.of("타파스-2","제로콜라-3"), 5),
                Arguments.of(List.of("티본스테이크-4","아이스크림-7","양송이수프-4","레드와인-5"), 20)
        );
    }
    private static Stream<Arguments> sampleTotalOrderCost() {
        return Stream.of(
                Arguments.of(List.of("해산물파스타-1"), 35000),
                Arguments.of(List.of("크리스마스파스타-3","레드와인-5","시저샐러드-4"), 407000),
                Arguments.of(List.of("타파스-2","제로콜라-3"), 20000),
                Arguments.of(List.of("티본스테이크-4","아이스크림-7","양송이수프-4","레드와인-5"), 579000)
        );
    }
}