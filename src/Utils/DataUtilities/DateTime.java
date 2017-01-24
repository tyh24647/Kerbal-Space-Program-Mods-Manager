package Utils.DataUtilities;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

import static AppData.Constants.DEFAULT_DATE_FORMAT_STR;

/**
 * generates a 'DateTime' object which has specific date formatting
 *
 * @author Tyler Hostager
 * @version 1/21/17
 */
public class DateTime {
    private static final String FORMAT = DEFAULT_DATE_FORMAT_STR;
    private static String dateTime;

    public DateTime() {
        generateDateTime();
    }

    static void generateDateTime() {
        SimpleDateFormat dateFormat = new SimpleDateFormat(FORMAT);
        dateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
        String formattedDate = dateFormat.format(new Date());
        setDateTime(formattedDate);
    }

    private static void setDateTime(String newDateTime) {
        dateTime = newDateTime;
    }

    public String getDateTime() {
        return dateTime;
    }
}
