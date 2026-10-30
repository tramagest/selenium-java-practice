package common.Utilities;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class DateUtils {
    private static final String DEFAULT_FORMAT = "M/d/yyyy";

    public static String getFutureDate(int days) {
        LocalDate date = LocalDate.now().plusDays(days);
        DateTimeFormatter formatter =
                DateTimeFormatter.ofPattern(DEFAULT_FORMAT);

        return date.format(formatter);
    }

    public static String getFutureDateInFormat(int days, String format) {
        LocalDate date = LocalDate.now().plusDays(days);
        DateTimeFormatter formatter =
                DateTimeFormatter.ofPattern(format);

        return date.format(formatter);
    }
}
