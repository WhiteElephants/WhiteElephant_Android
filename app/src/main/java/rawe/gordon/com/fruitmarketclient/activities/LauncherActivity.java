package rawe.gordon.com.fruitmarketclient.activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import rawe.gordon.com.fruitmarketclient.R;
import rawe.gordon.com.fruitmarketclient.activities.adapter.LauncherFragmentAdapter;
import rawe.gordon.com.fruitmarketclient.activities.navigation.LauncherTabLayout;

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
        tabLayout.setupIcons(new LauncherTabLayout.Tab[]{
                new LauncherTabLayout.Tab(R.drawable.ic_close, "首页"),
                new LauncherTabLayout.Tab(R.drawable.ic_close, "发现"),
                new LauncherTabLayout.Tab(R.drawable.ic_close, "创作"),
                new LauncherTabLayout.Tab(R.drawable.ic_close, "个人")
        }).hookUpWithViewPager(fragmentPager);
        tabLayout.setupIcons(null);
    }

    private void findViews() {
        fragmentPager = (ViewPager) findViewById(R.id.fragment_pager);
        tabLayout = (LauncherTabLayout) findViewById(R.id.tabs);
    }
}
