package rawe.gordon.com.pick.pick.utils;

/**
 * Created by gordon on 3/12/17.
 */

public class ViewUtil {
    private static final int FAST_CLICK_TIME_THRESHOLD = 650;

    private static long sLastClickTime = 0;

    public static boolean isFastDoubleClick() {
        long currentTime = System.currentTimeMillis();
        long timeDiff = currentTime - sLastClickTime;
        if (timeDiff > 0 && timeDiff < FAST_CLICK_TIME_THRESHOLD) {
            return true;
        }

        sLastClickTime = currentTime;
        return false;
    }
}
