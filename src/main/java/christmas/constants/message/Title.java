package christmas.constants.message;

import static christmas.constants.message.MessageFormat.TITLE_FORMAT;
import static christmas.constants.others.MarksAndConstants.MONTH;

public enum Title {
    MENU("주문 메뉴"),
    BEFORE_DISCOUNT("할인 전 총주문 금액"),
    GIVEAWAY("증정 메뉴"),
    BENEFIT_DETAIL("혜택 내역"),
    TOTAL_BENEFIT("총혜택 금액"),
    AFTER_DISCOUNT("할인 후 예상 결제 금액"),
    EVENT_BADGE(String.format("%d월 이벤트 배지", MONTH));

    private final String title;

    Title(String title) {
        this.title = title;
    }

    public String getTitle() {
        return String.format(TITLE_FORMAT, this.title);
    }
}
