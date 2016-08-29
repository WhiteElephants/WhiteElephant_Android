package rawe.gordon.com.fruitmarketclient.views.preview.widgets;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageView;

import com.nostra13.universalimageloader.core.ImageLoader;

import rawe.gordon.com.business.application.SharedParameter;
import rawe.gordon.com.business.imageloader.DisplayOptions;
import rawe.gordon.com.business.utils.DimenUtil;

/**
 * Created by gordon on 16/8/28.
 */
public class AspectImageView extends ImageView {

    private int innerWidth = 0, innerHeight = 0;

    public AspectImageView(Context context) {
        super(context);
        init();
    }

    public AspectImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public AspectImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init(){
        setScaleType(ScaleType.FIT_XY);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        setMeasuredDimension(MeasureSpec.makeMeasureSpec(innerWidth, MeasureSpec.EXACTLY),
                MeasureSpec.makeMeasureSpec(innerHeight, MeasureSpec.EXACTLY));
    }

    public void diaplayImage(String localPath) {
        int[] size = DimenUtil.decodeImageSize(localPath);
        int screenWidth = SharedParameter.getInstance().getScreenWidth();
        if (size[0] >= screenWidth) {
            innerWidth = screenWidth;
            innerHeight = size[1]*screenWidth/size[0];
        } else {
            innerWidth = size[0];
            innerHeight = size[1];
        }
        invalidate();
        ImageLoader.getInstance().displayImage(localPath, this, DisplayOptions.getCacheNoneFadeOptions());
    }

}
