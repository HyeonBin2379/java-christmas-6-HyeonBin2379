package christmas.constants.message;

import static christmas.constants.message.MessageFormat.ERROR_PREFIX;

public enum ErrorMessage {
    BLANK_INPUT("입력된 문자열이 빈 문자열입니다."),
    INVALID_DATE("유효하지 않은 날짜입니다. 다시 입력해 주세요."),
    INVALID_ORDER("유효하지 않은 주문입니다. 다시 입력해 주세요."),
    TOO_MANY_ORDER("총 주문 개수가 20개를 초과했습니다."),
    ONLY_BEVERAGE("음료만 주문할 수 없습니다.");

    private final String ErrorMsg;

    ErrorMessage(String errorMsg) {
        this.ErrorMsg = errorMsg;
    }

    public String getErrorMsg() {
        return String.format(ERROR_PREFIX + this.ErrorMsg);
    }
}
