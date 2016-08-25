package rawe.gordon.com.fruitmarketclient.activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import rawe.gordon.com.fruitmarketclient.R;
import rawe.gordon.com.fruitmarketclient.activities.adapter.LauncherFragmentAdapter;
import rawe.gordon.com.fruitmarketclient.activities.launcher.LauncherTabLayout;

/**
 * Created by gordon on 16/7/30.
 */
public class LauncherActivity extends AppCompatActivity {

    private ViewPager fragmentPager;
    private LauncherTabLayout tabLayout;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_activity_launcher);
        findViews();
        hooks();
    }

    private void hooks() {
        fragmentPager.setAdapter(new LauncherFragmentAdapter(getSupportFragmentManager()));
        tabLayout.configure(new LauncherTabLayout.Tab[]{
                new LauncherTabLayout.Tab(R.drawable.ic_credit_card_orange, "首页", R.drawable.ic_credit_card_grey),
                new LauncherTabLayout.Tab(R.drawable.ic_safari_orange, "发现", R.drawable.ic_safari_gray),
                new LauncherTabLayout.Tab(R.drawable.ic_camera_gray, "", R.drawable.ic_camera_gray),
                new LauncherTabLayout.Tab(R.drawable.ic_settings_orange, "创作", R.drawable.ic_settings_grey),
                new LauncherTabLayout.Tab(R.drawable.ic_person_orange, "个人", R.drawable.ic_person_gray)
        }, 0).setListener(new LauncherTabLayout.SwitchListener() {
            @Override
            public void onCenter() {
                PostComposeActivity.gotoPostComposeActivity(LauncherActivity.this);
            }

            @Override
            public void onTabs(int position) {

            }
        }).hookUpWithViewPager(fragmentPager);
    }

    private void findViews() {
        fragmentPager = (ViewPager) findViewById(R.id.fragment_pager);
        tabLayout = (LauncherTabLayout) findViewById(R.id.tabs);
    }
}
