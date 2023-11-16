package christmas.validator;

import static christmas.constants.message.ErrorMessage.INVALID_DATE;
import static christmas.validator.DateValidator.validateDateInteger;
import static christmas.validator.DateValidator.validateDateRange;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class DateValidatorTest {

    @ParameterizedTest
    @DisplayName("입력된 문자열을 정수로 변환할 수 없으면 예외 발생")
    @ValueSource(strings = {"asdf", "10a", "1th", "x-mas"})
    void validateDateInteger_test(String input) {
        Throwable exception = assertThrows(IllegalArgumentException.class, () -> validateDateInteger(input));
        assertEquals(INVALID_DATE.getErrorMsg(), exception.getMessage());
    }

    @ParameterizedTest
    @DisplayName("정수로 변환했을 시, 1 이상 31 이하의 숫자가 아니면 예외 발생")
    @ValueSource(ints = {0, 32})
    void validateDateRange_test(int input) {
        Throwable exception = assertThrows(IllegalArgumentException.class, () -> validateDateRange(input));
        assertEquals(INVALID_DATE.getErrorMsg(), exception.getMessage());
    }
}