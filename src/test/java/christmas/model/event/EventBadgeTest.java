package christmas.model.event;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class EventBadgeTest {

    @ParameterizedTest
    @DisplayName("총혜택금액을 통해 요구사항대로 이벤트 배지 부여가 이루어지는지 확인")
    @CsvSource(value = {
            "-4999, '없음'", "-5000, '별'", "-9999, '별'",
            "-10000, '트리'", "-19999, '트리'", "-20000, '산타'"
    })
    void getBadge_test(int input, String expected) {
        EventBadge result = EventBadge.getBadge(input);
        assertEquals(result.getName(), expected);
    }
}