package rawe.gordon.com.fruitmarketclient.fragments.launcher;

import android.view.View;

import rawe.gordon.com.fruitmarketclient.R;
import rawe.gordon.com.fruitmarketclient.fragments.LauncherBaseFragment;

/**
 * Created by gordon on 16/7/31.
 */
public class LauncherSecondFragment extends LauncherBaseFragment {

    public static LauncherSecondFragment newInstance() {
        LauncherSecondFragment secondLauncher = new LauncherSecondFragment();
        return secondLauncher;
    }

    @Override
    protected int getContentLayout() {
        return R.layout.layout_launcher_second_fragment;
    }

    @Override
    protected void bindViews(View rootView) {

    }

    @Override
    protected void prepareData() {

    }

    @Override
    protected int getRightDrawable() {
        return NO_DRAWABLE;
    }

    @Override
    protected void onRightClicked() {

    }

    @Override
    public void fetchNetWorkData() {

    }
}
