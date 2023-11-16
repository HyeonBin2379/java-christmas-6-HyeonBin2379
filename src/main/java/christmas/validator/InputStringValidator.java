package christmas.validator;

import static christmas.constants.message.ErrorMessage.BLANK_INPUT;
import static christmas.constants.message.ErrorMessage.INVALID_ORDER;
import static christmas.constants.others.MarksAndConstants.COMMA;
import static christmas.constants.others.MarksAndConstants.SPACE;

public class InputStringValidator {

    public static void validateDateString(String input) {
        validateBlankOrSpace(input);
    }

    public static void validateOrderString(String input) {
        validateBlankOrSpace(input);
        validateComma(input);
    }

    public static void validateBlankOrSpace(String input) {
        validateBlank(input);
        validateSpace(input);
    }

    private static void validateBlank(String input) {
        if (input.isBlank()) {
            throw new IllegalArgumentException(BLANK_INPUT.getErrorMsg());
        }
    }

    private static void validateSpace(String input) {
        if (input.contains(SPACE)) {
            throw new IllegalArgumentException(BLANK_INPUT.getErrorMsg());
        }
    }

    public static void validateComma(String input) {
        validateFirstOrLastComma(input);
        validateContinuousComma(input);
    }

    private static void validateFirstOrLastComma(String input) {
        int lastIndex = input.length() - 1;
        if (input.charAt(0) == COMMA.charAt(0) || input.charAt(lastIndex) == COMMA.charAt(0)) {
            throw new IllegalArgumentException(INVALID_ORDER.getErrorMsg());
        }
    }

    private static void validateContinuousComma(String input) {
        if (input.contains(COMMA + COMMA)) {
            throw new IllegalArgumentException(INVALID_ORDER.getErrorMsg());
        }
    }
}
