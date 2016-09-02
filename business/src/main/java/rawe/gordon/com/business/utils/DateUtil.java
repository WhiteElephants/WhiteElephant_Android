package rawe.gordon.com.business.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by gordon on 16/9/1.
 */
public class DateUtil {
    public static String currentTime() {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return df.format(new Date());
    }
}
