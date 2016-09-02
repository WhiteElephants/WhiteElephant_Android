package rawe.gordon.com.business.imageloader;

import android.graphics.Bitmap;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.display.CircleBitmapDisplayer;

import rawe.gordon.com.business.definitions.Color;

/**
 * Created by gordon on 8/2/16.
 */
public class DisplayOptions {
    public static DisplayImageOptions getCacheFadeOptions() {
        return new DisplayImageOptions.Builder().cacheInMemory(true).cacheOnDisk(true)
                .bitmapConfig(Bitmap.Config.ARGB_8888).displayer(new FadeBitmapDisplayer(500)).showImageOnLoading(getDefaultDrawable()).build();
    }

    public static DisplayImageOptions getCacheNoneFadeOptions() {
        return new DisplayImageOptions.Builder().cacheInMemory(true).cacheOnDisk(true)
                .bitmapConfig(Bitmap.Config.ARGB_8888).build();
    }

    public static DisplayImageOptions getCacheNoneFadeCornerOptions(int radius) {
        return new DisplayImageOptions.Builder().cacheInMemory(true).cacheOnDisk(true).displayer(new CircleBitmapDisplayer(android.graphics.Color.WHITE,radius))
                .bitmapConfig(Bitmap.Config.ARGB_8888).build();
    }

    public static Drawable getDefaultDrawable() {
        return new ColorDrawable(getDefaultDrawableColor());
    }

    public static int getDefaultDrawableColor() {
        return 0xFF65C6BB;
    }
}
