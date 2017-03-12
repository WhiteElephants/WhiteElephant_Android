package rawe.gordon.com.fruitmarketclient.old.activities;

import android.animation.ValueAnimator;
import android.app.Activity;
import android.content.Intent;
import android.os.Handler;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;

import rawe.gordon.com.business.activities.BaseActivity;
import rawe.gordon.com.fruitmarketclient.R;
import rawe.gordon.com.business.generals.DetectableHorizontalScrollView;

/**
 * Created by gordon on 16/5/12.
 */
public class SearchActivity extends BaseActivity {
    private DetectableHorizontalScrollView scrollView;
    private boolean direct = true;

    private View left, right, mask;
    private ViewGroup viewGroup;
    private SurfaceView surfaceView;

    @Override
    protected int getContentLayout() {
        return R.layout.layout_search;
    }

    @Override
    protected void bindViews(View rootView) {
        scrollView = (DetectableHorizontalScrollView) rootView.findViewById(R.id.horizontal);
        left = rootView.findViewById(R.id.nav_left);
        right = rootView.findViewById(R.id.nav_right);
        mask = rootView.findViewById(R.id.mask);
        surfaceView = (SurfaceView) rootView.findViewById(R.id.loading_view);
        viewGroup = (ViewGroup) rootView.findViewById(R.id.container);
        rootView.findViewById(R.id.search).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (direct) scrollView.fullScroll(View.FOCUS_RIGHT);
                else scrollView.fullScroll(View.FOCUS_LEFT);
                direct = !direct;
            }
        });
    }

//    @Override
//    protected int getMenuLayout() {
//        return R.menu.empty;
//    }
//
//    @Override
//    protected String getNavTitle() {
//        return "Search";
//    }

    @Override
    protected void prepareData() {
        final int[] length = {0};
        final float base_distance = 200f;
        scrollView.setOnScrollChangeListener(new DetectableHorizontalScrollView.OnScrollChangeListener() {
            @Override
            public void onScrollChanged(int l, int t, int oldl, int oldt) {
                int diff = length[0] - l;
                setRightAlpha(1 - (l < 0 ? 0f : l < base_distance ? l / base_distance : 1f));
                setLeftAlpha(1 - (diff < 0 ? 0f : diff < base_distance ? diff / base_distance : 1f));
            }
        });
        scrollView.post(new Runnable() {
            @Override
            public void run() {
                scrollView.fullScroll(View.FOCUS_LEFT);
                for (int i = 0; i < viewGroup.getChildCount(); i++) {
                    length[0] += viewGroup.getChildAt(i).getWidth();
                }
                length[0] = length[0] - scrollView.getWidth();
            }
        });
        left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                scrollView.fullScroll(View.FOCUS_LEFT);
            }
        });
        right.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                scrollView.fullScroll(View.FOCUS_RIGHT);
            }
        });
        showLoadingView();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                hideLoadingView();
            }
        }, 8000);
    }

    private void setLeftAlpha(float alpha) {
        left.setAlpha(alpha);
        left.setVisibility(alpha > 0f ? View.VISIBLE : View.GONE);
    }

    private void setRightAlpha(float alpha) {
        right.setAlpha(alpha);
        right.setVisibility(alpha > 0f ? View.VISIBLE : View.GONE);
    }
//
//    @Override
//    protected int getIcon() {
//        return R.drawable.ic_arrow_back;
//    }
//
//    @Override
//    protected void onBackAction() {
//        finishWithAnimation();
//    }
//
//    @Override
//    protected void onGetExtras(Bundle bundle) {
//    }

    private void showLoadingView() {
        mask.setVisibility(View.VISIBLE);
        surfaceView.setVisibility(View.VISIBLE);
    }

    private void hideLoadingView() {
        ValueAnimator valueAnimator = ValueAnimator.ofFloat(1F, 0F);
        valueAnimator.setDuration(500);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {

            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float value = (float) animation.getAnimatedValue();
                mask.setAlpha(value);
                if (value == 0F) {
                    mask.setVisibility(View.GONE);
                }
            }
        });
        surfaceView.setVisibility(View.GONE);
        valueAnimator.start();
    }

    public static void gotoSearchActivity(Activity start) {
        Intent intent = new Intent(start, SearchActivity.class);
        start.startActivity(intent);
    }
}
