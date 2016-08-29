package rawe.gordon.com.business.utils;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.util.TypedValue;

import java.lang.reflect.Field;

import rawe.gordon.com.business.application.ContextHolder;
import rawe.gordon.com.business.definitions.Size;

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

    public static int[] decodeImageSize(String localPath) {
        if (localPath.startsWith("file")) localPath = localPath.substring(5);
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(localPath, options);
        int[] retValue = new int[2];
        retValue[0] = options.outWidth;
        retValue[1] = options.outHeight;
        return retValue;
    }
}