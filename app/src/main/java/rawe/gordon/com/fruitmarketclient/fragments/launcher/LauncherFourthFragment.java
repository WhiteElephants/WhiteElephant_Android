package rawe.gordon.com.fruitmarketclient.fragments.launcher;

import android.view.View;

import rawe.gordon.com.fruitmarketclient.R;
import rawe.gordon.com.fruitmarketclient.fragments.LauncherBaseFragment;

/**
 * Created by gordon on 16/7/31.
 */
public class LauncherFourthFragment extends LauncherBaseFragment {

    public static LauncherFourthFragment newInstance() {
        LauncherFourthFragment fourthLauncher = new LauncherFourthFragment();
        return fourthLauncher;
    }

    @Override
    protected int getContentLayout() {
        return R.layout.layout_launcher_fourth_fragment;
    }

    @Override
    protected void bindViews(View rootView) {

    }

    @Override
    public void fetchData() {

    }
}
