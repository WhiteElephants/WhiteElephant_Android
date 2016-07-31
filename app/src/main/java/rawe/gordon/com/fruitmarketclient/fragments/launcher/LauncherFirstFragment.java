package rawe.gordon.com.fruitmarketclient.fragments.launcher;

import android.view.View;

import rawe.gordon.com.fruitmarketclient.R;
import rawe.gordon.com.fruitmarketclient.fragments.LauncherBaseFragment;

/**
 * Created by gordon on 16/7/31.
 */
public class LauncherFirstFragment extends LauncherBaseFragment {

    public static LauncherFirstFragment newInstance() {
        LauncherFirstFragment firstLauncher = new LauncherFirstFragment();
        return firstLauncher;
    }

    @Override
    protected int getContentLayout() {
        return R.layout.layout_launcher_first_fragment;
    }

    @Override
    protected void bindViews(View rootView) {

    }

    @Override
    protected void prepareData() {

    }

    @Override
    public void fetchNetWorkData() {

    }
}
