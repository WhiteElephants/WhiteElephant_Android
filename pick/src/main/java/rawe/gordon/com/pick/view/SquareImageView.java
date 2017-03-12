package rawe.gordon.com.pick.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.util.AttributeSet;
import android.widget.ImageView;

/**
 * Created by gordon on 8/2/16.
 */
public class SquareImageView extends ImageView {
    private int maskColor = Color.argb(100, 0, 0, 0);
    private boolean showMask = false;

    public SquareImageView(Context context) {
        super(context);
    }

    public SquareImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public SquareImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, widthMeasureSpec);
    }

    @Override
    protected void dispatchDraw(Canvas canvas) {
        super.dispatchDraw(canvas);
        if (showMask) canvas.drawColor(maskColor);
    }

    public void toggleMask(boolean isShow) {
        showMask = isShow;
        invalidate();
    }
}
