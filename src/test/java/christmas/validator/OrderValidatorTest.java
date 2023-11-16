package christmas.validator;

import static christmas.model.menu.Menu.CHAMPAGNE;
import static christmas.model.menu.Menu.RED_WINE;
import static christmas.model.menu.Menu.ZERO_COKE;
import static christmas.validator.OrderValidator.validateOnlyBeverage;
import static christmas.validator.OrderValidator.validateOverOrderLimits;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import christmas.model.order.Order;
import christmas.model.menu.Menu;
import java.util.Arrays;
import java.util.Map;
import java.util.stream.Stream;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;

class OrderValidatorTest {

    @ParameterizedTest
    @DisplayName("주문한 메뉴 개수의 총합이 20개를 초과하면 예외 발생")
    @ValueSource(strings = {"초코케이크-5,아이스크림-10,제로콜라-10", "크리스마스파스타-21"})
    void validateOverOrderLimits_test(String input) {
        Order given = new Order();
        given.setAllOrderToken(Arrays.asList(input.split(",")));

        assertThatThrownBy(() -> validateOverOrderLimits(given.getTotalCounts()))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @ParameterizedTest
    @DisplayName("음료 메뉴만 주문할 시 예외 발생")
    @MethodSource("sampleOnlyBeverage")
    void validateOnlyBeverage_Test(Map<Menu, Integer> orderInput) {
        assertThatThrownBy(() -> validateOnlyBeverage(orderInput)).isInstanceOf(IllegalArgumentException.class);
    }

    private static Stream<Arguments> sampleOnlyBeverage() {
        return Stream.of(
                Arguments.of(Map.of(RED_WINE, 1)),
                Arguments.of(Map.of(ZERO_COKE, 1)),
                Arguments.of(Map.of(CHAMPAGNE, 1)),
                Arguments.of(Map.of(RED_WINE,1, ZERO_COKE, 1)),
                Arguments.of(Map.of(RED_WINE, 1, CHAMPAGNE, 1)),
                Arguments.of(Map.of(ZERO_COKE, 1, CHAMPAGNE, 1)),
                Arguments.of(Map.of(RED_WINE, 1, ZERO_COKE, 1, CHAMPAGNE, 1))
        );
    }
}