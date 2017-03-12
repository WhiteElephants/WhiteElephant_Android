package rawe.gordon.com.fruitmarketclient.old.activities.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import rawe.gordon.com.fruitmarketclient.old.fragments.launcher.LauncherFirstFragment;
import rawe.gordon.com.fruitmarketclient.old.fragments.launcher.LauncherFourthFragment;
import rawe.gordon.com.fruitmarketclient.old.fragments.launcher.LauncherSecondFragment;
import rawe.gordon.com.fruitmarketclient.old.fragments.launcher.LauncherThirdFragment;

/**
 * Created by gordon on 16/7/31.
 */
public class LauncherFragmentAdapter extends FragmentPagerAdapter {
    final int PAGE_COUNT = 4;

    public LauncherFragmentAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return LauncherFirstFragment.newInstance();
            case 1:
                return LauncherSecondFragment.newInstance();
            case 2:
                return LauncherThirdFragment.newInstance();
            case 3:
                return LauncherFourthFragment.newInstance();
            default:
                return LauncherFourthFragment.newInstance();
        }

    }

    @Override
    public int getCount() {
        return PAGE_COUNT;
    }
}
