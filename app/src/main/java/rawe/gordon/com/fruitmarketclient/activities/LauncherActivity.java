package rawe.gordon.com.fruitmarketclient.activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import rawe.gordon.com.fruitmarketclient.R;
import rawe.gordon.com.fruitmarketclient.activities.adapter.LauncherFragmentAdapter;

/**
 * Created by gordon on 16/7/30.
 */
public class LauncherActivity extends AppCompatActivity {

    private ViewPager fragmentPager;
    private TabLayout tabLayout;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_activity_launcher);
        findViews();
        hooks();
    }

    private void hooks() {
        fragmentPager.setAdapter(new LauncherFragmentAdapter(getSupportFragmentManager()));
        tabLayout.setupWithViewPager(fragmentPager);
    }

    private void findViews() {
        fragmentPager = (ViewPager) findViewById(R.id.fragment_pager);
        tabLayout = (TabLayout) findViewById(R.id.tabs);
    }
}
