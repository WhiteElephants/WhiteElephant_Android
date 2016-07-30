package rawe.gordon.com.selectimage.widget;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Checkable;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import com.facebook.drawee.view.SimpleDraweeView;

import rawe.gordon.com.selectimage.R;
import rawe.gordon.com.selectimage.utils.DeviceInforHelper;

public class PicItemCheckedView extends RelativeLayout implements Checkable {

  private Context mContext;
  private boolean mChecked;
  private SimpleDraweeView mImgView = null;
  private ImageView mSelectView,mUnSelectView,mVideoIcon;
  private View mSelectPanelView;

  private static final int columnNum = 3;

    public PicItemCheckedView(Context context,boolean isHide) {
      this(context, null, 0,isHide);
    }

    public PicItemCheckedView(Context context) {
        this(context, null, 0,false);
    }

    public PicItemCheckedView(Context context, AttributeSet attrs) {
        this(context, attrs, 0,false);
    }

    public PicItemCheckedView(Context context, AttributeSet attrs, int defStyle,boolean isHide) {
        super(context, attrs, defStyle);
        mContext = context;
        LayoutInflater.from(mContext).inflate(R.layout.image_select_item, this);
        mImgView = (SimpleDraweeView) findViewById(R.id.img_view);
        mVideoIcon = (ImageView) findViewById(R.id.video_icon);
       
        int size = DeviceInforHelper.getScreenWidth()/ columnNum;
		    FrameLayout.LayoutParams params = (FrameLayout.LayoutParams) mImgView.getLayoutParams();
        params.width = size;
        params.height = size;
        mImgView.setLayoutParams(params);

        mSelectView = (ImageView) findViewById(R.id.select);
        mUnSelectView = (ImageView) findViewById(R.id.unselect);
        if(isHide) {
          mUnSelectView.setVisibility(View.GONE);
        }

        mSelectPanelView = findViewById(R.id.album_pic_select_panel);
    }

	@Override
    public void setChecked(boolean checked) {
        mChecked = checked;
        mSelectView.setVisibility(checked ? View.VISIBLE : View.GONE);
        mUnSelectView.setVisibility(checked ? View.GONE : View.VISIBLE);
    }

    @Override
    public boolean isChecked() {
        return mChecked;
    }

    @Override
    public void toggle() {
        setChecked(!mChecked);
    }

    public void setImgResId(int resId) {
        if (mImgView != null) {
            mImgView.setBackgroundResource(resId);
        }
    }
    
    public void setImageView(Bitmap bp){
    	if(mImgView != null){
    		mImgView.setImageBitmap(bp);
    	}
    }
    
    public void setImageView(Drawable db){
    	if(mImgView != null){
    		mImgView.setImageDrawable(db);
    	}
    }

  public ImageView getVideoIcon() {
    return mVideoIcon;
  }

  public SimpleDraweeView getImageView(){
    	return mImgView;
    }

    public View getSelectPanelView(){
      return mSelectPanelView;
    }
}
