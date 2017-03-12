package rawe.gordon.com.pick.utils;

import android.graphics.Bitmap;
import android.text.TextUtils;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by gordon on 3/12/17.
 */

public class FileUtil {
    public static void saveBitmap2File(Bitmap bm, String filepath) throws IOException {
        BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(filepath));
        bm.compress(Bitmap.CompressFormat.JPEG, 80, bos);
        bos.flush();
        bos.close();
    }

    public static boolean isExist(String path) {
        if (TextUtils.isEmpty(path)) {
            return false;
        }
        boolean exist;
        try {
            File file = new File(path);
            exist = file.exists();
        } catch (Exception e) {
            return false;
        }
        return exist;
    }
}
