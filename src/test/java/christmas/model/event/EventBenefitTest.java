package christmas.model.event;

import static org.junit.jupiter.api.Assertions.assertEquals;

import christmas.model.menu.Menu;
import christmas.model.order.Order;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;

class EventBenefitTest {

    private Order orderSample;
    private EventBenefit eventSample;
    private Giveaway giveawaySample;

    @BeforeEach
    void setUp() {
        orderSample = new Order();
        eventSample = new EventBenefit(orderSample);
        giveawaySample = new Giveaway(orderSample);
    }

    @ParameterizedTest
    @DisplayName("할인 전 총주문금액이 10,000원 이상 120,000원 미만일 때, 총혜택금액이 정확히 계산되는지 확인")
    @CsvSource(value = {
            "4, -3323", "8, -3723", "25, -6423",
            "26, -2023", "29, -2023", "31, -3023"
    })
    void getTotalBenefit_noGiveawayTest(int date, int expected) {
        orderSample.setValidOrderTable(List.of("초코케이크-1", "해산물파스타-1", "타파스-1", "제로콜라-1"));
        giveawaySample.addMenu("샴페인", 1);
        eventSample.addBenefit(date, giveawaySample);
        assertEquals(eventSample.getTotalBenefit(), expected);
    }

    @ParameterizedTest
    @DisplayName("할인 전 총주문금액이 120,000원 이상일 때, 총혜택금액이 정확히 계산되는지 확인")
    @CsvSource(value = {
            "4, -28323", "8, -28723", "25, -31423",
            "26, -27023", "29, -27023", "31, -28023"
    })
    void getTotalBenefit_test(int date, int expected) {
        orderSample.setValidOrderTable(List.of("초코케이크-1", "티본스테이크-1", "타파스-1", "레드와인-1"));
        giveawaySample.addMenu("샴페인", 1);
        eventSample.addBenefit(date, giveawaySample);
        assertEquals(eventSample.getTotalBenefit(), expected);
    }

    @ParameterizedTest
    @DisplayName("주어진 테스트케이스에서는 총혜택금액은 0원(적용 가능한 할인 및 증정 이벤트 없음)")
    @MethodSource("sampleTotalBenefitsZero")
    void getTotalBenefit_zeroBenefitTest(int date, List<String> orders) {
        int expected = 0;
        orderSample.setValidOrderTable(orders);
        giveawaySample.addMenu("샴페인", 1);
        eventSample.addBenefit(date, giveawaySample);
        assertEquals(eventSample.getTotalBenefit(), expected);
    }

    @ParameterizedTest
    @DisplayName("총혜택금액과 총할인금액이 동일할 때, 할인 후 총주문금액 계산")
    @CsvSource(value = {
            "4, 55177", "8, 54777", "25, 52077",
            "26, 56477", "29, 56477", "31, 55477"
    })
    void getAfterDiscounts_noGiveawayTest(int date, int expected) {
        orderSample.setValidOrderTable(List.of("초코케이크-1", "해산물파스타-1", "타파스-1", "제로콜라-1"));
        giveawaySample.addMenu("샴페인", 1);
        eventSample.addBenefit(date, giveawaySample);

        int result = eventSample.getAfterDiscounts(orderSample.getTotalCost(), giveawaySample);
        assertEquals(result, expected);
    }

    @ParameterizedTest
    @DisplayName("총혜택금액이 총할인금액보다 클 때, 할인 후 총주문금액 계산")
    @CsvSource(value = {
            "4, 132177", "8, 131777", "25, 129077",
            "26, 133477", "29, 133477", "31, 132477"
    })
    void getAfterDiscounts_test(int date, int expected) {
        orderSample.setValidOrderTable(List.of("초코케이크-1", "티본스테이크-1", "타파스-1", "레드와인-1"));
        giveawaySample.addMenu("샴페인", 1);
        eventSample.addBenefit(date, giveawaySample);

        int result = eventSample.getAfterDiscounts(orderSample.getTotalCost(), giveawaySample);
        assertEquals(result, expected);
    }

    @AfterEach
    void finish() {
        Map<Menu, Integer> orderResult = orderSample.getOrder();
        Map<Menu, Integer> giveawayResult = giveawaySample.getGiveaway();
        Map<EventName, Integer> eventResult = eventSample.getEventTable();
        orderResult.clear();
        giveawayResult.clear();
        eventResult.clear();
    }

    private static Stream<Arguments> sampleTotalBenefitsZero() {
        return Stream.of(
                Arguments.of(25, List.of("타파스-1","제로콜라-1")),
                Arguments.of(26, List.of("해산물파스타-1", "타파스-1", "제로콜라-1")),
                Arguments.of(28, List.of("해산물파스타-1", "타파스-1", "제로콜라-1")),
                Arguments.of(29, List.of("초코케이크-1", "타파스-1", "제로콜라-1")),
                Arguments.of(30, List.of("초코케이크-1", "타파스-1", "제로콜라-1"))
        );
    }
}