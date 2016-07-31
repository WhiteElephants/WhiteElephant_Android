package rawe.gordon.com.fruitmarketclient.activities.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import rawe.gordon.com.fruitmarketclient.fragments.edits.EditEmailFragment;
import rawe.gordon.com.fruitmarketclient.fragments.edits.EditGenderFragment;
import rawe.gordon.com.fruitmarketclient.fragments.edits.EditLogoFragment;
import rawe.gordon.com.fruitmarketclient.fragments.edits.EditNameFragment;
import rawe.gordon.com.fruitmarketclient.fragments.edits.EditPhoneFragment;

/**
 * Created by gordon on 16/7/31.
 */
public class LauncherFragmentAdapter extends FragmentPagerAdapter {
    final int PAGE_COUNT = 4;
    private String indicates[] = new String[]{"首页", "发现", "创作", "个人"};

    public LauncherFragmentAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return EditEmailFragment.newInstance(String.valueOf(position) + "sssss", null);
            case 1:
                return EditGenderFragment.newInstance(String.valueOf(position) + "sssss", null);
            case 2:
                return EditNameFragment.newInstance(String.valueOf(position) + "sssss", null);
            case 3:
                return EditPhoneFragment.newInstance(String.valueOf(position) + "sssss", null);
            default:
                return EditLogoFragment.newInstance("",null);
        }

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
