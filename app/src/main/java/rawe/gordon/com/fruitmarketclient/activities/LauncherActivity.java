package rawe.gordon.com.fruitmarketclient.activities;

import android.Manifest;
import android.support.annotation.NonNull;
import android.support.v4.view.ViewPager;
import android.view.View;

import java.util.Arrays;

import rawe.gordon.com.business.activities.BaseActivity;
import rawe.gordon.com.business.permission.PermissionManager;
import rawe.gordon.com.fruitmarketclient.R;
import rawe.gordon.com.fruitmarketclient.activities.adapter.LauncherFragmentAdapter;
import rawe.gordon.com.fruitmarketclient.activities.launcher.LauncherTabLayout;
import rawe.gordon.com.fruitmarketclient.fragments.MultiSelectFragment;

/**
 * Created by gordon on 16/7/30.
 */
public class LauncherActivity extends BaseActivity {

    private ViewPager fragmentPager;
    private LauncherTabLayout tabLayout;
    private PermissionManager permissionManager = new PermissionManager();

    @Override
    protected int getContentLayout() {
        return R.layout.layout_activity_launcher;
    }

    @Override
    protected void bindViews(View rootView) {
        fragmentPager = (ViewPager) rootView.findViewById(R.id.fragment_pager);
        tabLayout = (LauncherTabLayout) rootView.findViewById(R.id.tabs);
    }

    @Override
    protected void prepareData() {
        permissionManager.checkAndRequestPermissions(this, new PermissionManager.PermissionCallback() {
            @Override
            public void onAllGranted() {

            }

            @Override
            public void onNotAllGranted() {

            }
        }, Arrays.asList(Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.READ_PHONE_STATE, Manifest.permission.CAMERA));
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
                addFragmentWithoutEffect(new MultiSelectFragment());
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
    protected int amountWhenClose() {
        return 0;
    }
}
