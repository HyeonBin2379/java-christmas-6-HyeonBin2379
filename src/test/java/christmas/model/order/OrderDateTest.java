package christmas.model.order;

import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

class OrderDateTest {

    private OrderDate sample;
    @BeforeEach
    void setUp() {
        sample = new OrderDate();
    }

    @ParameterizedTest
    @DisplayName("입력된 날짜가 유효한 날짜가 아닐 경우 예외 발생")
    @ValueSource(strings = {"3일", "3 "})
    void setValidDate_exceptionTest(String given) {
        assertThatThrownBy(() -> sample.setValidDate(given)).isInstanceOf(IllegalArgumentException.class);
    }

    @ParameterizedTest
    @DisplayName("입력된 날짜가 유효한 날짜일 경우 정수로 변환한 값을 반환한 후 정상 종료")
    @CsvSource(value = {"'3', 3"})
    void setValidDate_normalTest(String input, int expected) {
        assertThatCode(() -> sample.setValidDate(input)).doesNotThrowAnyException();
        assertEquals(sample.getDate(), expected);
    }
}