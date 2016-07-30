package rawe.gordon.com.business.utils;

import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import rawe.gordon.com.business.application.SharedParameter;

/**
 * Created by gordon on 16/5/7.
 */
public class ViewSizeRegulator {
    public static void regulateScreenRatio(View view, float ratio) {
        try {
            view.getLayoutParams().height = (int) (SharedParameter.getInstance().getScreenWidth() / ratio);
            view.requestLayout();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void regulateViewRatio(final View view, final float ratio) throws Exception {
        if (view != null) view.post(new Runnable() {
            @Override
            public void run() {
                view.getLayoutParams().height = (int) (view.getWidth() / ratio);
                view.requestLayout();
                if (view.getWidth() == 0)
                    Log.e("ViewSizeRegulator", "regulateViewRatio failed to regulate view size");
            }
        });
    }

    public static void adjsutViewSize(final View view, int width, int height) {
        ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
        layoutParams.width = width;
        layoutParams.height = height;
    }
}