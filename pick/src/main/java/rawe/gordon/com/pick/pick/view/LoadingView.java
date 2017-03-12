package rawe.gordon.com.pick.pick.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.widget.FrameLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import rawe.gordon.com.pick.R;


/**
 * Created by WindPush on 15/5/31.
 */
public class LoadingView extends FrameLayout {
    public static final int NORMAL = 0;
    public static final int IOS = 1;

    private TextView mTxProgressValue;
    private ProgressBar mProgress;

    private boolean mCanTouch = false;
    private int mProgressType = 0;
    private boolean isPreview = false;

    public LoadingView(Context context) {
        super(context);
        ((LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE))
                .inflate(R.layout.layout_loadingview, this, true);
        initView();
    }

    public LoadingView(Context context, AttributeSet attrs) {
        super(context, attrs);
        ((LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE))
                .inflate(R.layout.layout_loadingview, this, true);
        initView();
    }

    public LoadingView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        ((LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE))
                .inflate(R.layout.layout_loadingview, this, true);
        initView();
    }

    private void initView() {
        mTxProgressValue = (TextView) findViewById(R.id.tx_progressvaule);
        mProgress = (ProgressBar) findViewById(R.id.progress);
    }

    public void disMiss() {
        setVisibility(GONE);
        mTxProgressValue.setText("");
    }

    public void show() {
        setVisibility(VISIBLE);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        if (mCanTouch) {
            return super.onInterceptTouchEvent(ev);
        } else {
            return true;
        }
    }

    public void init() {
        mProgress.setVisibility(VISIBLE);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return true;
    }
}
