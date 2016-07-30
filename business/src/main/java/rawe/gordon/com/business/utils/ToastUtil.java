package rawe.gordon.com.business.utils;

import android.widget.Toast;

import rawe.gordon.com.business.application.ContextHolder;

/**
 * Created by gordon on 5/16/16.
 */
public class ToastUtil {
    public static void say(String message) {
        Toast.makeText(ContextHolder.getInstance().getContext(), message, Toast.LENGTH_SHORT).show();
    }
}