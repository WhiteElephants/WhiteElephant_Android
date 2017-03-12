package rawe.gordon.com.pick.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.widget.FrameLayout;
import android.widget.TextView;

import rawe.gordon.com.pick.R;


/**
 * Created by WindPush on 15/5/31.
 */
public class LoadingView extends FrameLayout {

    private TextView mTxProgressValue;

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
    }

    public void setProgress(int progress) {
        mTxProgressValue.setText(String.valueOf(progress));
    }

    public void disMiss() {
        setVisibility(GONE);
        mTxProgressValue.setText("");
    }

    public void show() {
        setVisibility(VISIBLE);
        setProgress(85);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return true;
    }
}
