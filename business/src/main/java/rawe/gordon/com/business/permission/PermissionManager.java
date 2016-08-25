package rawe.gordon.com.business.permission;

import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.Build;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mcluo on 2016/8/19.
 */
public class PermissionManager {
    public static final int REQUEST_CODE_ASK_MISS_PERMISSIONS = 0x98;
    private List<String> miss = new ArrayList<>();

    private void filterPermissions(Activity act, String permission, List<String> miss) {
        if (!PermissionUtils.hasSelfPermissions(act, permission)) {
            miss.add(permission);
        }
    }

    /**
     * 检查并请求权限
     */
    public void checkAndRequestPermissions(final Activity act, PermissionCallback callback, List<String> permissions) {
        this.callback = callback;
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            callback.onAllGranted();
            return;
        }
        /**缺少权限*/
        miss.clear();
        /**   filter area that add the requested permissions here...  */
        for (String inspect : permissions) {
            filterPermissions(act, inspect, miss);
        }

        if (miss.size() > 0) {
            PermissionsDispatcher.checkPermissions(act, REQUEST_CODE_ASK_MISS_PERMISSIONS, new PermissionListener() {
                @Override
                public void onPermissionsGranted(int requestCode, int[] grantResults, String... permissions) {

                }

                @Override
                public void onPermissionsDenied(int requestCode, int[] grantResults, String... permissions) {

                }

                @Override
                public void onShowRequestPermissionRationale(int requestCode, boolean isShowRationale, String... permissions) {
                    if (permissions != null && permissions.length > 0) {
                        PermissionsDispatcher.requestPermissions(act, requestCode, permissions);
                    }
                }

                @Override
                public void onPermissionsError(int requestCode, int[] grantResults, String errorMsg, String... permissions) {

                }
            }, Build.VERSION.SDK_INT < 23, miss.toArray(new String[miss.size()]));
        } else {
            callback.onAllGranted();
        }
    }


    public void handlePermission(int requestCode, String[] permissions, int[] grantResults) {
        if (requestCode == REQUEST_CODE_ASK_MISS_PERMISSIONS) {
            for (int i = 0; i < permissions.length; i++) {
                if (grantResults[i] != PackageManager.PERMISSION_GRANTED) {
                    callback.onNotAllGranted();
                    return;
                }
            }
            callback.onAllGranted();
        }
    }

    private PermissionCallback callback;

    public interface PermissionCallback {
        void onAllGranted();

        void onNotAllGranted();
    }
}
