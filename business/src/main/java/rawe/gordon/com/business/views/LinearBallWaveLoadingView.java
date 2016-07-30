package rawe.gordon.com.business.views;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.Point;
import android.graphics.PorterDuff;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

/**
 * Created by gordon on 16/5/29.
 */
public class LinearBallWaveLoadingView extends SurfaceView implements SurfaceHolder.Callback {
    private Canvas canvas;
    private Paint paint;
    private SurfaceHolder holder;
    private Runnable drawingRunnable;
    private int containerWidth, containerHeight;
    private int factor;
    private int basicRadius;
    private boolean exitFlag = true;
    private Point[] positions;
    private int[] colors;
    private int xGap;
    private int count;
    /**
     * set this params larger is bigger amplitude is required.
     */
    private static final int AMPLITUDE = 20;
    /**
     * set this params larger is faster speed is required.
     */
    private static final int SPEED = 12;
    /**
     * if equal to count, then no delay at all, set this params larger if no delay is required.
     */
    private static final int DELAY = 8;


    public LinearBallWaveLoadingView(Context context) {
        super(context);
        init();
    }

    public LinearBallWaveLoadingView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public LinearBallWaveLoadingView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        setZOrderOnTop(true);
        getHolder().addCallback(this);
        getHolder().setFormat(PixelFormat.TRANSLUCENT);
        factor = 0;
        basicRadius = 20;
        count = 5;
        paint = new Paint();
        paint.setColor(Color.BLACK);
        drawingRunnable = new Runnable() {
            @Override
            public void run() {
                containerWidth = getWidth();
                containerHeight = getHeight();
                xGap = (containerWidth - count * basicRadius * 2) / (count + 1);
                positions = new Point[count];
                colors = new int[count];
                for (int i = 0; i < count; i++) {
                    positions[i] = new Point();
                    colors[i] = i == 0 ? Color.RED : i == 1 ? Color.GREEN : i == 2 ? Color.BLUE : i == 3 ? Color.YELLOW : i == 4 ? Color.BLACK : Color.RED;
                    positions[i].set(xGap + basicRadius + (xGap + 2 * basicRadius) * i, containerHeight / 2);
                }
                while (exitFlag) {
                    canvas = holder.lockCanvas();
                    if (canvas == null) return;
                    canvas.drawColor(Color.TRANSPARENT, PorterDuff.Mode.CLEAR);// 清除画布
                    for (int i = 0; i < count; i++) {
                        int wave = calcFactor(i, factor);
                        paint.setColor(colors[i]);
                        canvas.drawCircle(positions[i].x, positions[i].y, basicRadius + wave, paint);
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
        Log.d("loading", "created");
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        Log.d("loading", "changed");
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        exitFlag = false;
        Log.d("loading", "destroyed");
    }


}
