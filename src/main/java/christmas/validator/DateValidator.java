package christmas.validator;

import static christmas.constants.message.ErrorMessage.INVALID_DATE;
import static christmas.constants.others.MarksAndConstants.FIRST_DAY;
import static christmas.constants.others.MarksAndConstants.LAST_DAY;

public class DateValidator {

    public static int validateDateInteger(String input) {
        int valid;
        try {
            valid = Integer.parseInt(input);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException(INVALID_DATE.getErrorMsg());
        }
        return valid;
    }

    public static void validateDateRange(int date) {
        if (date < FIRST_DAY || date > LAST_DAY) {
            throw new IllegalArgumentException(INVALID_DATE.getErrorMsg());
        }
    }
}