package rawe.gordon.com.business.utils;

import android.content.Context;
import android.util.TypedValue;

import java.lang.reflect.Field;

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

    public static int getBarHeight(Context context) {
        Class<?> c;
        Object obj;
        Field field;
        int x, sbar = 38;//默认为38，貌似大部分是这样的
        try {
            c = Class.forName("com.android.internal.R$dimen");
            obj = c.newInstance();
            field = c.getField("status_bar_height");
            x = Integer.parseInt(field.get(obj).toString());
            sbar = context.getResources().getDimensionPixelSize(x);

        } catch (Exception e1) {
            e1.printStackTrace();
        }
        return sbar;
    }
}