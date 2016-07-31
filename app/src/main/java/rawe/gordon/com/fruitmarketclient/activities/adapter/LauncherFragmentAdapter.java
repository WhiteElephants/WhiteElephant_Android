package rawe.gordon.com.fruitmarketclient.activities.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import rawe.gordon.com.fruitmarketclient.fragments.edits.EditEmailFragment;

/**
 * Created by gordon on 16/7/31.
 */
public class LauncherFragmentAdapter extends FragmentPagerAdapter {
    final int PAGE_COUNT = 4;
    private String indicates[] = new String[]{"首页", "发现", "消息", "个人"};

    public LauncherFragmentAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        return EditEmailFragment.newInstance("16121388949", null);
    }

    @Override
    public int getCount() {
        return PAGE_COUNT;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return indicates[position];
    }
}
