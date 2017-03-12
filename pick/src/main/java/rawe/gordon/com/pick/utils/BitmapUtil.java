package rawe.gordon.com.pick.utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.ThumbnailUtils;
import android.provider.MediaStore;

import java.io.File;

import rawe.gordon.com.business.application.ContextHolder;

/**
 * Created by gordon on 3/8/17.
 */

public class BitmapUtil {
    private BitmapUtil() {
    }

    public final static int MAX_WIDTH = 1000;
    public final static int MAX_HEIGHT = 1000;

    public static int[] decodeSize(String path) {
        if (!new File(path).exists()) return new int[]{0, 0};
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(path, options);
        int imageHeight = options.outHeight;
        int imageWidth = options.outWidth;
        return new int[]{imageWidth, imageHeight};
    }

    public static String generateScaledPicture(String picPath) {
        if (!FileUtil.isExist(picPath)) {
            return picPath;
        }
        File aimFile = new File(ContextHolder.getInstance().getContext().getExternalFilesDir("nineplus"), MD5Util.getMd5(picPath) + "_m.jpg");
        if (aimFile.exists()) return aimFile.getAbsolutePath();
        try {
            Bitmap bitmap = getBitmapUsingScale(picPath);
            FileUtil.saveBitmap2File(bitmap, aimFile.getAbsolutePath());
            bitmap.recycle();
            return aimFile.getAbsolutePath();
        } catch (Exception e) {
            e.printStackTrace();
            return picPath;
        }
    }

    /**
     * 根据视频生成范围内图片
     */
    public static String generateScaledVideoPicture(String picPath) {
        if (!FileUtil.isExist(picPath)) {
            return picPath;
        }
        File aimFile = new File(ContextHolder.getInstance().getContext().getExternalFilesDir("nineplus"), MD5Util.getMd5(picPath) + "_m.jpg");
        if (aimFile.exists()) return aimFile.getAbsolutePath();
        try {
            Bitmap bitmap = ThumbnailUtils.createVideoThumbnail(picPath, MediaStore.Video.Thumbnails.MINI_KIND);
            FileUtil.saveBitmap2File(bitmap, aimFile.getAbsolutePath());
            bitmap.recycle();
            return aimFile.getAbsolutePath();
        } catch (Exception e) {
            e.printStackTrace();
            return picPath;
        }
    }

    /**
     * 缩放缩略图方法封装
     */
    public static String generateThumbPicture(String scaledPath) {
        if (!FileUtil.isExist(scaledPath)) {
            return scaledPath;
        }
        File aimFile = new File(ContextHolder.getInstance().getContext().getExternalFilesDir("nineplus"), MD5Util.getMd5(scaledPath) + "_s.jpg");
        if (aimFile.exists()) return aimFile.getAbsolutePath();
        try {
            Bitmap bitmap = getBitmapUsingThumb(scaledPath, 0.5F);
            FileUtil.saveBitmap2File(bitmap, aimFile.getAbsolutePath());
            bitmap.recycle();
            return aimFile.getAbsolutePath();
        } catch (Exception e) {
            e.printStackTrace();
            return scaledPath;
        }
    }


    /**
     * 根据图片生成范围内图片
     */
    public static Bitmap getBitmapUsingScale(String path) {
        int width, height, sampleSize = 1; //默认缩放为1
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;  //仅仅解码边缘区域
        //如果指定了inJustDecodeBounds，decodeByteArray将返回为空
        BitmapFactory.decodeFile(path, options);
        //得到宽与高
        height = options.outHeight;
        width = options.outWidth;
        //图片实际的宽与高，根据默认最大大小值，得到图片实际的缩放比例
        while ((height / sampleSize > MAX_HEIGHT)
                || (width / sampleSize > MAX_WIDTH)) {
            sampleSize += 1;
        }

        //不再只加载图片实际边缘
        options.inJustDecodeBounds = false;
        //并且制定缩放比例
        options.inSampleSize = sampleSize;
        return BitmapFactory.decodeFile(path, options);
    }

    /**
     * 缩放缩略图
     */
    public static Bitmap getBitmapUsingThumb(String path, float factor) {
        int width, height; //默认缩放为1
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;  //仅仅解码边缘区域
        //如果指定了inJustDecodeBounds，decodeByteArray将返回为空
        BitmapFactory.decodeFile(path, options);
        //得到宽与高
        height = options.outHeight;
        width = options.outWidth;
        options.inJustDecodeBounds = false;
        Bitmap bitmap = BitmapFactory.decodeFile(path, options);
        int targetWidth = (int) (factor * width);
        int targetHeight = (int) (factor * height);
        return ThumbnailUtils.extractThumbnail(bitmap, targetWidth,
                targetHeight, ThumbnailUtils.OPTIONS_RECYCLE_INPUT);
    }
}
