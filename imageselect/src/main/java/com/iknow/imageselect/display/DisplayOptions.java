package com.iknow.imageselect.display;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.display.CircleBitmapDisplayer;

/**
 * Created by gordon on 8/2/16.
 */
public class DisplayOptions {
    public static DisplayImageOptions getCacheFadeOptions() {
        return new DisplayImageOptions.Builder().cacheInMemory(true).cacheOnDisk(true)
                .bitmapConfig(Bitmap.Config.ARGB_8888).displayer(new FadeBitmapDisplayer(500)).showImageOnLoading(getDefaultDrawable()).build();
    }

    public static DisplayImageOptions getCacheFadeLowerQualityOptions() {
        return new DisplayImageOptions.Builder().cacheInMemory(true).cacheOnDisk(true)
                .bitmapConfig(Bitmap.Config.ARGB_4444).displayer(new FadeBitmapDisplayer(500)).showImageOnLoading(getDefaultDrawable()).build();
    }

    public static DisplayImageOptions getCacheNoneFadeOptions() {
        return new DisplayImageOptions.Builder().cacheInMemory(true).cacheOnDisk(true)
                .bitmapConfig(Bitmap.Config.ARGB_8888).build();
    }

    public static DisplayImageOptions getCacheNoneFadeCornerOptions() {
        return new DisplayImageOptions.Builder().cacheInMemory(true).cacheOnDisk(true)
                .bitmapConfig(Bitmap.Config.ARGB_8888).displayer(new CircleBitmapDisplayer()).build();
    }

    public static Drawable getDefaultDrawable() {
        return new ColorDrawable(getDefaultDrawableColor());
    }

    public static int getDefaultDrawableColor() {
        return 0xFF65C6BB;
    }
}
