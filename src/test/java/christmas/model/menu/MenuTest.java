package christmas.model.menu;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class MenuTest {

    @ParameterizedTest
    @DisplayName("인자로 사용된 메뉴 이름에 대응되는 Enum을 올바르게 반환되는지 확인")
    @CsvSource(value = {
            "'양송이수프', SOUP", "'타파스', TAPAS", "'시저샐러드', SALAD", "'티본스테이크', T_STAKE",
            "'바비큐립', BARBEQUE_RIB", "'해산물파스타', SEAFOOD_PASTA", "'크리스마스파스타', CHRISTMAS_PASTA",
            "'초코케이크', CHOCO_CAKE", "'아이스크림', ICE_CREAM", "'제로콜라', ZERO_COKE",
            "'레드와인', RED_WINE", "'샴페인', CHAMPAGNE"
    })
    void findMenuName(String given, Menu expected) {
        Menu result = Menu.findMenuName(given);
        assertEquals(result, expected);
    }
}