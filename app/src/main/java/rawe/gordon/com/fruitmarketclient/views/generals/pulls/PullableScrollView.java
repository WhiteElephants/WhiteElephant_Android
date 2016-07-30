package rawe.gordon.com.fruitmarketclient.views.generals.pulls;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ScrollView;

public class PullableScrollView extends ScrollView implements Pullable {
    private OnYScrollChangeListener listener;

    public PullableScrollView(Context context) {
        super(context);
    }

    public PullableScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public PullableScrollView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    public boolean canPullDown() {
        if (getScrollY() == 0)
            return true;
        else
            return false;
    }

    @Override
    public boolean canPullUp() {
        if (getScrollY() >= (getChildAt(0).getHeight() - getMeasuredHeight()))
            return true;
        else
            return false;
    }

    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);
        if (this.listener != null) this.listener.onScrollChanged(l, t, oldl, oldt);
    }

    public void setYScrollListener(OnYScrollChangeListener listener) {
        this.listener = listener;
    }

    public interface OnYScrollChangeListener {
        void onScrollChanged(int l, int t, int oldl, int oldt);
    }
}
