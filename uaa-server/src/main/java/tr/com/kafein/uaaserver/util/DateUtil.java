package tr.com.kafein.uaaserver.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateUtil {

    /**
     * Returns n'th day starting from today.
     *  1 for tomorrow
     *  -1 for yesterday
     *
     * @param n  how many days will move from today
     * @return n'th day starting from today
     */
    public static Date getNthDay(int n){
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, n);
        return cal.getTime();
    }

    public static String toString(Date date) {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd hh:mm:ss");
        return dateFormat.format(date);
    }
}
