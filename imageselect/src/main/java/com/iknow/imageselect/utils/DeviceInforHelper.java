package com.iknow.imageselect.utils;

import android.util.DisplayMetrics;
import android.util.TypedValue;

import rawe.gordon.com.business.application.ContextHolder;

/**
 * Author: Jason.Chou
 * Email: who_know_me@163.com
 * Created: 2016年04月26日 4:13 PM
 * Description:
 */
public class DeviceInforHelper {

    public static int getPixelFromDip(float f) {
        return getPixelFromDip(ContextHolder.getInstance().getContext().getResources().getDisplayMetrics(), f);
    }

    public static int getPixelFromDip(DisplayMetrics dm, float dip) {
        return (int) (TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dip, dm) + 0.5f);
    }
}
