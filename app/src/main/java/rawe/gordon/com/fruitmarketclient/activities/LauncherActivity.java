package rawe.gordon.com.fruitmarketclient.activities;

import android.Manifest;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import rawe.gordon.com.fruitmarketclient.fragments.MultiSelectFragments;

import java.util.Arrays;

import rawe.gordon.com.business.permission.PermissionManager;
import rawe.gordon.com.fruitmarketclient.R;
import rawe.gordon.com.fruitmarketclient.activities.adapter.LauncherFragmentAdapter;
import rawe.gordon.com.fruitmarketclient.activities.launcher.LauncherTabLayout;

/**
 * Created by gordon on 16/7/30.
 */
public class LauncherActivity extends AppCompatActivity {

    private ViewPager fragmentPager;
    private LauncherTabLayout tabLayout;
    private PermissionManager permissionManager = new PermissionManager();
    private MultiSelectFragments multiSelectFragments;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        permissionManager.checkAndRequestPermissions(this, new PermissionManager.PermissionCallback() {
            @Override
            public void onAllGranted() {

            }

            @Override
            public void onNotAllGranted() {

            }
        }, Arrays.asList(Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.READ_PHONE_STATE, Manifest.permission.CAMERA));
        setContentView(R.layout.layout_activity_launcher);
        fragmentPager = (ViewPager) findViewById(R.id.fragment_pager);
        tabLayout = (LauncherTabLayout) findViewById(R.id.tabs);
        hooks();
    }

    private void hooks() {
        fragmentPager.setAdapter(new LauncherFragmentAdapter(getSupportFragmentManager()));
        tabLayout.configure(new LauncherTabLayout.Tab[]{
                new LauncherTabLayout.Tab(R.drawable.ic_home_orange, "推荐", R.drawable.ic_home_grey),
                new LauncherTabLayout.Tab(R.drawable.ic_safari_orange, "发现", R.drawable.ic_safari_gray),
                new LauncherTabLayout.Tab(R.drawable.ic_add_white, "", R.drawable.ic_add_white),
                new LauncherTabLayout.Tab(R.drawable.ic_draft_orange, "草稿", R.drawable.ic_draft_gray),
                new LauncherTabLayout.Tab(R.drawable.ic_person_orange, "个人", R.drawable.ic_person_gray)
        }, 0).setListener(new LauncherTabLayout.SwitchListener() {
            @Override
            public void onCenter() {
                getSupportFragmentManager().beginTransaction().add(R.id.container, multiSelectFragments = new MultiSelectFragments().setIntention(MultiSelectFragments.INTENTION_TO_POST)
                        , MultiSelectFragments.class.getCanonicalName()).commitAllowingStateLoss();
            }

            @Override
            public void onTabs(int position) {

            }
        }).hookUpWithViewPager(fragmentPager);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        permissionManager.handlePermission(requestCode, permissions, grantResults);
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    public void onBackPressed() {
        if (multiSelectFragments != null) {
            getSupportFragmentManager().beginTransaction().remove(multiSelectFragments).commitAllowingStateLoss();
            multiSelectFragments = null;
        }
    }
}
