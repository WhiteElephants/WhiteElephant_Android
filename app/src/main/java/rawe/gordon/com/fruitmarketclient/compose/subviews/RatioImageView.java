package rawe.gordon.com.fruitmarketclient.compose.subviews;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.widget.ImageView;

import rawe.gordon.com.business.utils.DimenUtil;

/**
 * Created by gordon on 16/8/23.
 */
public class RatioImageView extends ImageView {

    private float ratio_w_h = 1 / 2F;

    public RatioImageView(Context context) {
        super(context);
    }

    public RatioImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public RatioImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    Paint paint;

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, MeasureSpec.makeMeasureSpec((int) (MeasureSpec.getSize(widthMeasureSpec) * ratio_w_h), MeasureSpec.getMode(heightMeasureSpec)));
    }

    @Override
    protected void dispatchDraw(Canvas canvas) {
        LinearGradient gradient = new LinearGradient(0, DimenUtil.dip2pix(100), 0, getMeasuredHeight(), 0x00000000, 0x44000000, android.graphics.Shader.TileMode.CLAMP);
        paint = new Paint();
        paint.setDither(true);
        paint.setShader(gradient);
        canvas.drawRect(0, 0, getMeasuredWidth(), getMeasuredHeight(), paint);
    }
}
