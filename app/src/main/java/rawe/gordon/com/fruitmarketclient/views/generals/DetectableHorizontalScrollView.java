package rawe.gordon.com.fruitmarketclient.views.generals;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.HorizontalScrollView;
import android.widget.ScrollView;

/**
 * Created by gordon on 5/13/16.
 */
public class DetectableHorizontalScrollView extends HorizontalScrollView {
    public DetectableHorizontalScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        if (onScrollChangeListener != null)
            onScrollChangeListener.onScrollChanged(l, t, oldl, oldt);
    }

    private OnScrollChangeListener onScrollChangeListener;

    public void setOnScrollChangeListener(OnScrollChangeListener listener) {
        this.onScrollChangeListener = listener;
    }

    public interface OnScrollChangeListener {
        void onScrollChanged(int l, int t, int oldl, int oldt);
    }
}
