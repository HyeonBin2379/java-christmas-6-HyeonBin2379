package christmas.model.order;

import static christmas.validator.DateValidator.validateDateInteger;
import static christmas.validator.DateValidator.validateDateRange;

public class OrderDate {

    private int date;

    private int validate(String input) {
        int validDate = validateDateInteger(input);
        validateDateRange(validDate);
        return validDate;
    }

    public void setValidDate(String input) {
        this.date = validate(input);
    }

    public int getDate() {
        return date;
    }
}
