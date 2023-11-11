package christmas.validator;

import static christmas.constants.message.ErrorMessage.BLANK_INPUT;
import static christmas.constants.message.ErrorMessage.INVALID_ORDER;

public class InputStringValidator {
    public static void validateDateString(String input) {
        validateBlankOrSpace(input);
    }
    public static void validateOrderString(String input) {
        validateBlankOrSpace(input);
        validateComma(input);
    }
    private static void validateBlankOrSpace(String input) {
        if (input.isBlank()) {
            throw new IllegalArgumentException(BLANK_INPUT.getErrorMsg());
        }
        if (input.contains(" ")) {
            throw new IllegalArgumentException(BLANK_INPUT.getErrorMsg());
        }
    }
    public static void validateComma(String input) {
        validateFirstOrLastComma(input);
        validateContinuousComma(input);
    }

    private static void validateFirstOrLastComma(String input) {
        int lastIndex = input.length() - 1;
        if (input.charAt(0) == ',' || input.charAt(lastIndex) == ',') {
            throw new IllegalArgumentException(INVALID_ORDER.getErrorMsg());
        }
    }

    private static void validateContinuousComma(String input) {
        if (input.contains(",,")) {
            throw new IllegalArgumentException(INVALID_ORDER.getErrorMsg());
        }
    }
}
