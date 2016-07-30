package rawe.gordon.com.business.utils;

import android.graphics.drawable.Drawable;

import rawe.gordon.com.business.application.ContextHolder;
import rawe.gordon.com.business.application.SharedParameter;

/**
 * Created by gordon on 5/9/16.
 */
public class DrawableUtil {
    public static Drawable decodeFromVector(int resId) {
        return SharedParameter.getInstance().getDrawableManager().getDrawable(ContextHolder.getInstance().getContext(), resId);
    }
}