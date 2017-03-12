package rawe.gordon.com.pick.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by gordon on 3/7/17.
 */

public class DateUtil {

    public static final String VIDEO_DURATION = "mm:ss";

    public static String transferLongToDate(String dateFormat, long millSec) {
        SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
        Date date = new Date(millSec);
        return sdf.format(date);
    }
}
