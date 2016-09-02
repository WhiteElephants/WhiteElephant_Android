package rawe.gordon.com.business.activities;

import android.animation.ValueAnimator;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.io.Serializable;

import rawe.gordon.com.business.R;
import rawe.gordon.com.business.application.SharedParameter;
import rawe.gordon.com.business.utils.DimenUtil;
import uk.co.senab.photoview.PhotoViewAttacher;

/**
 * Created by gordon on 16/8/27.
 */
public class SitoImageViewActivity extends AppCompatActivity {
    public static final String KEY_DATA = "KEY_DATA";

    private View sitoArea;
    private ImageView sitoImage;
    ImageModel data;

    @Override
    protected void onCreate(@Nullable Bundle bundle) {
        super.onCreate(bundle);
        if (getIntent() == null || getIntent().getExtras() == null || getIntent().getExtras().getSerializable(KEY_DATA) == null) {
            finish();
        }
        data = (ImageModel) getIntent().getExtras().getSerializable(KEY_DATA);
        setContentView(R.layout.layout_sito_browse_image);
        sitoArea = findViewById(R.id.sito_area);
        sitoImage = (ImageView) findViewById(R.id.sito_image);
        sitoArea.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        PhotoViewAttacher attacher = new PhotoViewAttacher(sitoImage);
        attacher.setOnViewTapListener(new PhotoViewAttacher.OnViewTapListener() {
            @Override
            public void onViewTap(View view, float v, float v1) {
                finish();
            }
        });
        showSitoImage(data.currentX, data.currentY, data.currentWidth, data.currentHeight, data.imgUrl);
    }

    private void showSitoImage(final int currentX, int currentY, final int currentWidth, final int currentHeight, String url) {
        currentY -= DimenUtil.getBarHeight(this);
        final ViewGroup.MarginLayoutParams layoutParams = (ViewGroup.MarginLayoutParams) sitoImage.getLayoutParams();
        layoutParams.width = currentWidth;
        layoutParams.height = currentHeight;
        layoutParams.leftMargin = currentX;
        layoutParams.topMargin = currentY;
        sitoImage.setLayoutParams(layoutParams);
        sitoImage.requestLayout();
        final ValueAnimator valueAnimator = ValueAnimator.ofFloat(0F, 1F);
        int toY = 0;
        int toX = 0;
        int toWidth = SharedParameter.getInstance().getScreenWidth(), toHeight = SharedParameter.getInstance().getScreenHeight() - DimenUtil.getBarHeight(this);
        final int deltaX = toX - currentX;
        final int deltaY = toY - currentY;
        final int deltaWidth = toWidth - currentWidth;
        final int deltaHeight = toHeight - currentHeight;
        final int finalCurrentY = currentY;
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float fac = (float) animation.getAnimatedValue();
                //假设放大到屏幕宽度和高度的方的
                layoutParams.leftMargin = (int) (deltaX * fac) + currentX;
                layoutParams.topMargin = (int) (deltaY * fac) + finalCurrentY;
                layoutParams.width = (int) (currentWidth + deltaWidth * fac);
                layoutParams.height = (int) (currentHeight + deltaHeight * fac);
                sitoImage.requestLayout();
            }
        });
        valueAnimator.setDuration(500);
        Glide.with(this).load(url).diskCacheStrategy(DiskCacheStrategy.ALL).into(sitoImage);
        sitoArea.setVisibility(View.VISIBLE);
        valueAnimator.start();
    }

    public static void goToSitoImageBrowsePage(Activity from, ImageModel model) {
        Intent intent = new Intent(from, SitoImageViewActivity.class);
        Bundle data = new Bundle();
        data.putSerializable(SitoImageViewActivity.KEY_DATA, model);
        intent.putExtras(data);
        from.startActivity(intent);
    }

    public static class ImageModel implements Serializable {

        public ImageModel(String imgUrl, int currentX, int currentY, int currentWidth, int currentHeight) {
            this.imgUrl = imgUrl;
            this.currentX = currentX;
            this.currentY = currentY;
            this.currentWidth = currentWidth;
            this.currentHeight = currentHeight;
        }

        public String imgUrl;
        public int currentX;
        public int currentY;
        public int currentWidth;
        public int currentHeight;
    }
}
