package ar.edu.itba.it.paw.common;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.util.Date;

public class DateHelper {

    private static final DateTimeFormatter parser = DateTimeFormat.forPattern("dd/MM/yyyy");

    public DateHelper() {

    }

    public static DateTime getDateFromString(String stringDate) {
        return parser.parseDateTime(stringDate);
    }

    public static DateTime getNowDate() {
        return new DateTime();
    }

    public static DateTime getFromUtilDate(Date utilDate) {
        return new DateTime(utilDate);
    }

    public static String getStringFromDate(DateTime date) {
        return parser.print(date);
    }
}
