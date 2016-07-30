package rawe.gordon.com.business.utils;

import android.util.TypedValue;

import rawe.gordon.com.business.application.ContextHolder;

/**
 * Created by gordon on 16/5/17.
 */
public class DimenUtil {
    public static float dip2pix(int dip) {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dip,
                ContextHolder.getInstance().getContext().getResources().getDisplayMetrics());
    }

    public static int pix2dip(float pix) {
        return (int) (pix / ContextHolder.getInstance().getContext().getResources().getDisplayMetrics().density);
    }
}