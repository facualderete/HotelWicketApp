package ar.edu.itba.it.paw.common;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

public class DateStringHelper {

    private static final DateTimeFormatter parser = DateTimeFormat.forPattern("dd-MM-yyyy");

    public DateStringHelper() {

    }

    public static DateTime getDateFromString(String stringDate){
        return parser.parseDateTime(stringDate);
    }

    public static DateTime getNowDate(){
        return new DateTime();
    }

    public static String getStringFromDate(DateTime date){
        return parser.print(date);
    }
}
