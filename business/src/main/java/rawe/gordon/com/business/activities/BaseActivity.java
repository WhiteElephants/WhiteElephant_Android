package rawe.gordon.com.business.activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import rawe.gordon.com.business.R;
import rawe.gordon.com.business.fragments.BaseFragment;

/**
 * Created by gordon on 16/5/8.
 */
public abstract class BaseActivity extends AppCompatActivity {

    public List<Fragment> fragments = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        onGetExtras(getIntent() == null ? null : getIntent().getExtras() == null ? null : getIntent().getExtras());
        setContentView(R.layout.layout_base_activity);
        prepareData();
    }

    public void addFragment(BaseFragment fragment) {
        if (findViewById(R.id.fragment_container) != null) {
            getFragmentTransaction().add(R.id.fragment_container, fragment).commitAllowingStateLoss();
            fragments.add(fragment);
        }
    }

    public void removeFragment(Fragment fragment) {
        if (fragment != null && fragments.size() > 1) {
            getFragmentTransaction().remove(fragment).commitAllowingStateLoss();
            fragments.remove(fragment);
        }
    }

    private FragmentTransaction getFragmentTransaction() {
        return getSupportFragmentManager().
                beginTransaction().setCustomAnimations(R.anim.slide_in_right_fragment,
                R.anim.slide_out_right_fragment, R.anim.slide_in_right_fragment, R.anim.slide_out_right_fragment);
    }

    protected List<Fragment> getFragments() {
        return fragments;
    }

    protected abstract int getContentLayout();

    protected abstract void bindViews(View rootView);

    protected abstract void prepareData();

    protected void onGetExtras(Bundle bundle){
        //do nothing
    }
}
