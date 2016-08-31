package rawe.gordon.com.fruitmarketclient.fragments.launcher;

import android.view.View;

import rawe.gordon.com.fruitmarketclient.R;
import rawe.gordon.com.business.fragments.LauncherBaseFragment;

/**
 * Created by gordon on 16/7/31.
 */
public class LauncherThirdFragment extends LauncherBaseFragment {

    public static LauncherThirdFragment newInstance() {
        LauncherThirdFragment thirdLauncher = new LauncherThirdFragment();
        return thirdLauncher;
    }

    @Override
    protected int getContentLayout() {
        return R.layout.layout_launcher_third_fragment;
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

    @Override
    protected String getTitle() {
        return "草稿";
    }
}
