package rawe.gordon.com.fruitmarketclient.activities;

import android.Manifest;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.view.View;

import java.util.Arrays;

import rawe.gordon.com.business.activities.BaseActivity;
import rawe.gordon.com.business.permission.PermissionManager;
import rawe.gordon.com.business.utils.ToastUtil;
import rawe.gordon.com.fruitmarketclient.R;
import rawe.gordon.com.fruitmarketclient.activities.adapter.LauncherFragmentAdapter;
import rawe.gordon.com.fruitmarketclient.activities.launcher.LauncherTabLayout;

/**
 * Created by gordon on 16/7/30.
 */
public class LauncherActivity extends BaseActivity {

    private ViewPager fragmentPager;
    private LauncherTabLayout tabLayout;
    private PermissionManager permissionManager = new PermissionManager();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        permissionManager.checkAndRequestPermissions(this, new PermissionManager.PermissionCallback() {
            @Override
            public void onAllGranted() {
                ToastUtil.say("都授权了，可以愉快的玩耍");
            }

            @Override
            public void onNotAllGranted() {
                ToastUtil.say("不授权怎么玩");
            }
        }, Arrays.asList(Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.READ_PHONE_STATE, Manifest.permission.CAMERA));
    }

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
    protected int getMenuLayout() {
        return R.menu.empty;
    }

    @Override
    protected String getNavTitle() {
        return "首页";
    }

    @Override
    protected void prepareData() {
        hooks();
    }

    @Override
    protected int getIcon() {
        return R.drawable.ic_arrow_back;
    }

    @Override
    protected void onBackAction() {

    }

    @Override
    protected void onGetExtras(Bundle bundle) {

    }

    @Override
    protected boolean enableTitle() {
        return false;
    }

    private void hooks() {
        fragmentPager.setAdapter(new LauncherFragmentAdapter(getSupportFragmentManager()));
        tabLayout.configure(new LauncherTabLayout.Tab[]{
                new LauncherTabLayout.Tab(R.drawable.ic_credit_card_orange, "首页", R.drawable.ic_credit_card_grey),
                new LauncherTabLayout.Tab(R.drawable.ic_safari_orange, "发现", R.drawable.ic_safari_gray),
                new LauncherTabLayout.Tab(R.drawable.ic_add_white, "", R.drawable.ic_add_white),
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

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        permissionManager.handlePermission(requestCode, permissions, grantResults);
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }
}
