package rawe.gordon.com.business.activities;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import java.util.ArrayList;
import java.util.List;

import rawe.gordon.com.business.R;
import rawe.gordon.com.business.generals.DetectableScrollView;
import rawe.gordon.com.business.utils.AnimatorUtil;
import rawe.gordon.com.business.application.SharedParameter;
import rawe.gordon.com.business.fragments.EditBaseFragment;

/**
 * Created by gordon on 16/5/8.
 */
public abstract class BaseActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private RelativeLayout contentContainer;
    private View contentView;
    private List<EditBaseFragment> fragments = new ArrayList<>();
    private DetectableScrollView scrollView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        onGetExtras(getIntent() == null ? null : getIntent().getExtras() == null ? null : getIntent().getExtras());
        setContentView(R.layout.layout_base_activity);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        contentContainer = (RelativeLayout) findViewById(R.id.content);
        scrollView = (DetectableScrollView) findViewById(R.id.scroll_view);
        contentContainer.addView(contentView = LayoutInflater.from(getApplicationContext()).inflate(getContentLayout(), contentContainer, false));
        bindViews(contentView);
        contentView.setTranslationY(SharedParameter.getInstance().getScreenHeight());
        toolbar.setTitle(getNavTitle());
        toolbar.setNavigationIcon(getIcon());
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackAction();
            }
        });
        if(!enableTitle())toolbar.setVisibility(View.GONE);
        performInAnimation();
        prepareData();
        setTheme(R.style.noAnimationTheme);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(getMenuLayout(), menu);
        return true;
    }

    protected void performInAnimation() {
        Animator toolbarAnimator = AnimatorUtil.getToolbarDropDownAnimator(toolbar);
        Animator contentAlpha = AnimatorUtil.getAlphaInAnimator(contentContainer);
        Animator contentTranslate = AnimatorUtil.getDropDownAnimator(contentView);
        AnimatorSet set = new AnimatorSet();
        set.playSequentially(toolbarAnimator, contentAlpha, contentTranslate);
        set.start();
    }

    protected void finishWithAnimation() {
        Animator contentAlpha = AnimatorUtil.getAlphaOutAnimator(contentContainer);
        Animator contentTranslate = AnimatorUtil.getDropDownOutAnimator(contentView);
        AnimatorSet set = new AnimatorSet();
        set.playSequentially(contentTranslate, contentAlpha);
        set.start();
        set.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                finish();
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
    }

    protected void addFragment(EditBaseFragment fragment) {
        if (findViewById(R.id.fragment_container) != null) {
            getFragmentTransaction().add(R.id.fragment_container, fragment).commitAllowingStateLoss();
            playLeftAnimation();
            fragments.add(fragment);
        }
    }

    public void removeFragment(EditBaseFragment fragment) {
        if (fragment != null) {
            fragment.emitSignals();
            getFragmentTransaction().remove(fragment).commitAllowingStateLoss();
            resumePositionAnimation();
            fragments.remove(fragment);
        }
    }

    protected void addFragmentWithData(EditBaseFragment fragment, Bundle bundle) {
        if (findViewById(R.id.fragment_container) != null) {
            fragment.setArguments(bundle);
            playLeftAnimation();
            getFragmentTransaction().add(R.id.fragment_container, fragment).commitAllowingStateLoss();
        }
    }

    private FragmentTransaction getFragmentTransaction() {
        return getSupportFragmentManager().
                beginTransaction().setCustomAnimations(R.anim.slide_in_right_fragment,
                R.anim.slide_out_right_fragment, R.anim.slide_in_right_fragment, R.anim.slide_out_right_fragment);
    }

    private void playLeftAnimation() {
        Animator translate = ObjectAnimator.ofFloat(scrollView, "translationX", -400F);
        translate.setDuration(getResources().getInteger(R.integer.page_switch_time));
        translate.start();
    }

    private void resumePositionAnimation() {
        Animator translate = ObjectAnimator.ofFloat(scrollView, "translationX", 0F);
        translate.setDuration(getResources().getInteger(R.integer.page_switch_time));
        translate.start();
    }

    @Override
    public void onBackPressed() {
        finishWithAnimation();
    }

    protected List<EditBaseFragment> getFragments() {
        return fragments;
    }

    protected abstract int getContentLayout();

    protected abstract void bindViews(View rootView);

    protected abstract int getMenuLayout();

    protected abstract String getNavTitle();

    protected abstract void prepareData();

    protected abstract int getIcon();

    protected abstract void onBackAction();

    protected abstract void onGetExtras(Bundle bundle);

    protected boolean enableTitle() {
        return true;
    }

    /**
     * Created by gordon on 5/4/16.
     */
    public static class ViewFactory {
        public static View createView(LayoutInflater inflater, int resId, ViewGroup parent, boolean attachToParent) {
            return inflater.inflate(resId, parent, attachToParent);
        }
    }
}
