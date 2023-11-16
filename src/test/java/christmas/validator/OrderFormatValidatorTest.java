package christmas.validator;

import static christmas.constants.message.ErrorMessage.INVALID_ORDER;
import static christmas.model.menu.Menu.CHAMPAGNE;
import static christmas.model.menu.Menu.CHOCO_CAKE;
import static christmas.model.menu.Menu.RED_WINE;
import static christmas.model.menu.Menu.TAPAS;
import static christmas.validator.OrderFormatValidator.validateDuplicatedMenu;
import static christmas.validator.OrderFormatValidator.validateHyphen;
import static christmas.validator.OrderFormatValidator.validatePositive;
import static christmas.validator.OrderFormatValidator.validatePossibleMenu;
import static christmas.validator.OrderFormatValidator.validateQuantityInteger;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import christmas.model.menu.Menu;
import java.util.Map;
import java.util.stream.Stream;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;

class OrderFormatValidatorTest {

    @ParameterizedTest
    @DisplayName("입력된 주문 메뉴의 문자열 토큰 1개에 하이픈(-)이 없거나 연속되면 예외 발생")
    @ValueSource(strings = {"티본스테이크--1", "티본스테이크1", "티본스테이크--"})
    void validateHyphen_test(String input) {
        Throwable exception = assertThrows(IllegalArgumentException.class, () -> validateHyphen(input));
        assertEquals(INVALID_ORDER.getErrorMsg(), exception.getMessage());
    }

    @ParameterizedTest
    @DisplayName("하이픈(-)을 기준으로 파싱한 앞부분이 주문 가능한 메뉴 이름이면 정상 종료")
    @ValueSource(strings = {
            "양송이수프-1", "타파스-1", "시저샐러드-1", "티본스테이크-1",
            "티본스테이크-1", "바비큐립-1", "해산물파스타-1", "크리스마스파스타-1",
            "초코케이크-1", "아이스크림-1", "제로콜라-1", "레드와인-1", "샴페인-1"
    })
    void validatePossibleMenu_normalTest(String input) {
        String[] given = validateHyphen(input);
        assertThatCode(() -> validatePossibleMenu(given[0])).doesNotThrowAnyException();
    }

    @ParameterizedTest
    @DisplayName("하이픈(-)을 기준으로 파싱한 앞부분이 주문 가능한 메뉴가 아니면 정상 종료")
    @ValueSource(strings = {"해물파스타-1", "바비큐-1", "-1"})
    void validatePossibleMenu_exceptionTest(String input) {
        String[] given = validateHyphen(input);
        Throwable exception = assertThrows(IllegalArgumentException.class, () -> validatePossibleMenu(given[0]));
        assertEquals(INVALID_ORDER.getErrorMsg(), exception.getMessage());
    }

    @ParameterizedTest
    @DisplayName("주문 가능한 메뉴를 하나라도 중복해서 주문하면 예외 발생")
    @MethodSource("sampleDuplicatedMenuName")
    void validateDuplicatedMenu_test(String input, Map<Menu, Integer> order) {
        Throwable exception = assertThrows(IllegalArgumentException.class, () -> validateDuplicatedMenu(input, order));
        assertEquals(INVALID_ORDER.getErrorMsg(), exception.getMessage());
    }

    @ParameterizedTest
    @DisplayName("하이픈(-) 기준 뒷부분을 정수로 변환할 수 없으면 예외 발생")
    @ValueSource(strings = {"크리스마스파스타-1'", "시저샐러드-제로콜라-1", "시저샐러드-"})
    void validateQuantityInteger_test(String input) {
        String[] given = validateHyphen(input);
        Throwable exception = assertThrows(IllegalArgumentException.class, () -> validateQuantityInteger(given[1]));
        assertEquals(INVALID_ORDER.getErrorMsg(), exception.getMessage());
    }

    @Test
    @DisplayName("하이픈(-) 기준 뒷부분을 정수로 변환한 값이 양의 정수가 아니면 예외 발생")
    void validatePositive_test() {
        String[] given = validateHyphen("크리스마스파스타-0");
        int when = Integer.parseInt(given[1]);

        Throwable exception = assertThrows(IllegalArgumentException.class, () -> validatePositive(when));
        assertEquals(INVALID_ORDER.getErrorMsg(), exception.getMessage());
    }

    private static Stream<Arguments> sampleDuplicatedMenuName() {
        Map<Menu, Integer> orderSample = Map.of(CHOCO_CAKE, 1, RED_WINE, 1, CHAMPAGNE, 1, TAPAS, 1);
        return Stream.of(
                Arguments.of("초코케이크", orderSample),
                Arguments.of("레드와인", orderSample),
                Arguments.of("샴페인", orderSample),
                Arguments.of("타파스", orderSample)
        );
    }
}