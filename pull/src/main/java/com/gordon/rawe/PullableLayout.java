package com.gordon.rawe;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.ViewGroup;
import android.view.animation.OvershootInterpolator;

/**
 * Created by gordon on 16/8/5.
 */
public class PullableLayout extends ViewGroup {

    private int factor = 150;
    //获取容器基本属性
    private int selfWidth, selfHeight;
    //最终我们想要关注的那个拉动的Y
    private int obvservedY = 0;
    //容器的最大高度,我们不能拉动的数值超过这个
    private int maxY;
    //累计拉动真实距离
    private long actualY = 0;
    //多点触碰过滤辅助标志位
    private boolean mEvents = false;

    // 按下Y坐标，上一个事件点Y坐标
    private float downY, lastY;

    public PullableLayout(Context context) {
        super(context);
    }

    public PullableLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public PullableLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        setMeasuredDimension(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        selfWidth = getMeasuredHeight();
        maxY = selfHeight = getMeasuredHeight();
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        switch (ev.getActionMasked()) {
            case MotionEvent.ACTION_DOWN:
                downY = ev.getY();
                lastY = downY;
                mEvents = false;
                break;
            case MotionEvent.ACTION_POINTER_DOWN:
            case MotionEvent.ACTION_POINTER_UP:
                // 过滤多点触碰
                mEvents = true;
                break;
            case MotionEvent.ACTION_MOVE:
                if (!mEvents) {
                    actualY += ev.getY() - lastY;
                    calcObservedCurvedValue();
                } else
                    mEvents = false;
                lastY = ev.getY();
                break;
            case MotionEvent.ACTION_UP:
                slowlyResume();
                break;
            default:
                break;
        }

        super.dispatchTouchEvent(ev);
        return true;
    }

    //按照右向抛物线取值
    public void calcObservedCurvedValue() {
        int positive = actualY >= 0 ? 1 : -1;
        obvservedY = positive * (int) Math.sqrt(2 * factor * positive * actualY);
        Log.d("obvservedY", String.valueOf(obvservedY));
        invalidate();
    }

    private Paint paint;

    {
        paint = new Paint();
        paint.setColor(Color.parseColor("#3498db"));
    }

    @Override
    protected void dispatchDraw(Canvas canvas) {
        super.dispatchDraw(canvas);
        canvas.drawRect(0, obvservedY, selfWidth, obvservedY + selfHeight, paint);
    }

    private void slowlyResume() {
        ValueAnimator valueAnimator = ValueAnimator.ofInt((int) actualY, 0);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                actualY = (int) animation.getAnimatedValue();
                calcObservedCurvedValue();
            }
        });
        valueAnimator.setDuration(500);
        valueAnimator.setInterpolator(new OvershootInterpolator());
        valueAnimator.start();
    }
}
