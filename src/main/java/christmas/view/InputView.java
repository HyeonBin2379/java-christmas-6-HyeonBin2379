package christmas.view;

import static christmas.constants.message.IOMessageConstants.DATE_INPUT;
import static christmas.constants.message.IOMessageConstants.MENU_ORDER_INPUT;
import static christmas.constants.message.IOMessageConstants.ORDER_EXAMPLE;
import static christmas.constants.others.MarksAndConstants.COMMA;
import static christmas.constants.others.MarksAndConstants.MONTH;
import static christmas.validator.InputStringValidator.validateDateString;
import static christmas.validator.InputStringValidator.validateOrderString;

import camp.nextstep.edu.missionutils.Console;
import java.util.ArrayList;
import java.util.List;

public class InputView {

    public String inputDate() {
        System.out.printf(DATE_INPUT, MONTH);
        String input = Console.readLine();
        validateDateString(input);
        return input;
    }

    public List<String> inputMenuOrder() {
        System.out.println(MENU_ORDER_INPUT + ORDER_EXAMPLE);
        String input = Console.readLine();
        validateOrderString(input);
        return new ArrayList<>(List.of(input.split(COMMA)));
    }
}
