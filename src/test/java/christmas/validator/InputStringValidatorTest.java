package christmas.validator;

import static christmas.validator.InputStringValidator.validateBlankOrSpace;
import static christmas.validator.InputStringValidator.validateComma;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class InputStringValidatorTest {

    @ParameterizedTest
    @DisplayName("입력된 문자열이 빈 문자열 혹은 공백문자가 포함된 문자열이면 예외 발생")
    @ValueSource(strings = {"", " ", "해산물파스타-1, 레드와인-1", "12 "})
    void validateBlankOrSpace_test(String input) {
        assertThatThrownBy(() -> validateBlankOrSpace(input)).isInstanceOf(IllegalArgumentException.class);
    }

    @ParameterizedTest
    @DisplayName("주문할 메뉴 입력 시 쉼표(,)가 처음이나 마지막에 사용되거나 연속으로 사용되면 예외 발생")
    @ValueSource(strings = {",티본스테이크-1,초코케이크-1", "티본스테이크-1,초코케이크-1,", "티본스테이크-1,,초코케이크-1"})
    void validateComma_test(String input) {
        assertThatThrownBy(() -> validateComma(input)).isInstanceOf(IllegalArgumentException.class);
    }
}