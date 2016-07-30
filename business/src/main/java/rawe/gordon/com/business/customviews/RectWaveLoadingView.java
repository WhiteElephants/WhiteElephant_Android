package rawe.gordon.com.business.customviews;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.PorterDuff;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

/**
 * Created by gordon on 16/5/29.
 */
public class RectWaveLoadingView extends SurfaceView implements SurfaceHolder.Callback {
    private Canvas canvas;
    private Paint paint;
    private SurfaceHolder holder;
    private Runnable drawingRunnable;
    private int containerWidth, containerHeight;
    private int factor;
    private int totalWidth, itemHeight;
    private boolean exitFlag = true;
    private int startX, startY;
    private int xGap;
    private int count;

    /**
     * set this params larger is bigger amplitude is required.
     */
    private static final int AMPLITUDE = 50;
    /**
     * set this params larger is faster speed is required.
     */
    private static final int SPEED = 12;
    /**
     * if equal to count, then no delay at all, set this params larger if no delay is required.
     */
    private static final int DELAY = 8;


    public RectWaveLoadingView(Context context) {
        super(context);
        init();
    }

    public RectWaveLoadingView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public RectWaveLoadingView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        setZOrderOnTop(true);
        getHolder().addCallback(this);
        getHolder().setFormat(PixelFormat.TRANSLUCENT);
        factor = 0;
        totalWidth = 120;
        itemHeight = 80;
        count = 5;
        paint = new Paint();
        paint.setColor(Color.BLACK);
        final int step = (totalWidth - (count - 1) * xGap) / count;
        drawingRunnable = new Runnable() {
            @Override
            public void run() {
                containerWidth = getWidth();
                containerHeight = getHeight();
                startX = (containerWidth - totalWidth) / 2;
                startY = (containerHeight - itemHeight) / 2;
                xGap = 10;
                Rect dirtyArea = new Rect(startX, startY, startX + totalWidth, startY + itemHeight);
                while (exitFlag) {
                    canvas = holder.lockCanvas(dirtyArea);
//                    canvas = holder.lockCanvas();
                    if (canvas == null) return;
                    canvas.drawColor(Color.TRANSPARENT, PorterDuff.Mode.CLEAR);// 清除画布
                    for (int i = 0; i < count; i++) {
                        int wave = calcFactor(i, factor);
                        canvas.drawRect(startX + (xGap + step) * i, startY - wave / 2,
                                startX + step + (xGap + step) * i,
                                startY + itemHeight + wave / 2, paint);
                    }
                    factor += SPEED;
                    holder.unlockCanvasAndPost(canvas);// 更新屏幕显示内容
                    try {
                        Thread.sleep(10);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        };
    }

    private int calcFactor(int index, int factor) {
        double diff = Math.PI / (DELAY);
        int res = (int) (AMPLITUDE * Math.cos(Math.PI * factor / 360 - index * diff));
        return res < 0 ? 0 : res;
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        this.holder = holder;
        new Thread(drawingRunnable).start();
        Log.d("loading","created");
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        Log.d("loading","changed");
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        exitFlag = false;
        Log.d("loading","destroyed");
    }
}
