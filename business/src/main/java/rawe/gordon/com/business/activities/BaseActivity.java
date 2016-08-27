package rawe.gordon.com.business.activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;

import java.util.ArrayList;
import java.util.List;

import rawe.gordon.com.business.R;
import rawe.gordon.com.business.fragments.BaseFragment;

/**
 * Created by gordon on 16/5/8.
 */
public abstract class BaseActivity extends AppCompatActivity {

    public List<BaseFragment> fragments = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        onGetExtras(getIntent() == null ? null : getIntent().getExtras() == null ? null : getIntent().getExtras());
        setContentView(R.layout.layout_base_activity);
        bindViews(LayoutInflater.from(this).inflate(getContentLayout(), (RelativeLayout) findViewById(R.id.default_content)));
        prepareData();
    }

    public void addFragmentWithoutEffect(BaseFragment fragment) {
        if (findViewById(R.id.fragment_area) != null) {
            getSupportFragmentManager().beginTransaction().add(R.id.fragment_area, fragment).commitAllowingStateLoss();
            fragments.add(fragment);
        }
    }

    public void addFragment(BaseFragment fragment) {
        if (findViewById(R.id.fragment_area) != null) {
            getFragmentTransaction().add(R.id.fragment_area, fragment).commitAllowingStateLoss();
            fragments.add(fragment);
        }
    }

    public void removeFragmentWithoutEffect(BaseFragment fragment) {
        if (amountWhenClose() == fragments.size()) {
            finish();
            return;
        }
        if (fragment != null) {
            if (fragments.size() >= 1) {
                fragments.remove(fragment);
                getSupportFragmentManager().beginTransaction().remove(fragment).commitAllowingStateLoss();
            }
        }
    }

    public void removeFragment(BaseFragment fragment) {
        if (amountWhenClose() == fragments.size()) {
            finish();
            return;
        }
        if (fragment != null) {
            if (fragments.size() >= 1) {
                fragments.remove(fragment);
                getFragmentTransaction().remove(fragment).commitAllowingStateLoss();
            }
        }
    }

    private FragmentTransaction getFragmentTransaction() {
        return getSupportFragmentManager().
                beginTransaction().setCustomAnimations(R.anim.slide_in_right_fragment,
                R.anim.slide_out_right_fragment, R.anim.slide_in_right_fragment, R.anim.slide_out_right_fragment);
    }

    protected List<BaseFragment> getFragments() {
        return fragments;
    }

    protected abstract int getContentLayout();

    protected abstract void bindViews(View rootView);

    protected abstract void prepareData();

    protected void onGetExtras(Bundle bundle) {
        //do nothing
    }

    protected int amountWhenClose() {
        return 1;
    }

    @Override
    public void onBackPressed() {
        if (amountWhenClose() == fragments.size()) {
            if (amountWhenClose() == 1) {
                fragments.get(0).closeWithAnimation(new BaseFragment.Callback() {
                    @Override
                    public void onAnimationFinish() {
                        finish();
                    }
                });
            } else finish();
            return;
        }
        if (fragments.size() >= 1) {
            fragments.get(fragments.size() - 1).handleBackPress(new BaseFragment.Callback() {
                @Override
                public void onAnimationFinish() {
                    removeFragmentWithoutEffect(fragments.get(fragments.size() - 1));
                    if (fragments.size() - 1 >= 0) fragments.remove(fragments.size() - 1);
                }
            });
        }
    }
}
