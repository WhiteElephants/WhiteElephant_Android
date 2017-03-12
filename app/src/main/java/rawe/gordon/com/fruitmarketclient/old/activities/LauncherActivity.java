package rawe.gordon.com.fruitmarketclient.old.activities;

import android.Manifest;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import java.util.Arrays;
import java.util.List;

import rawe.gordon.com.business.application.SharedParameter;
import rawe.gordon.com.business.permission.PermissionManager;
import rawe.gordon.com.fruitmarketclient.R;
import rawe.gordon.com.fruitmarketclient.old.activities.adapter.LauncherFragmentAdapter;
import rawe.gordon.com.fruitmarketclient.old.activities.launcher.LauncherTabLayout;
import rawe.gordon.com.fruitmarketclient.router.ActivityHelper;
import rawe.gordon.com.pick.manage.PhotoChooseListener;
import rawe.gordon.com.pick.manage.PhotoPictureManager;
import rawe.gordon.com.pick.model.MediaInfo;

/**
 * Created by gordon on 16/7/30.
 */
public class LauncherActivity extends AppCompatActivity {

    private ViewPager fragmentPager;
    private LauncherTabLayout tabLayout;
    private PermissionManager permissionManager = new PermissionManager();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SharedParameter.getInstance().generateScreenParameters(this);
        setContentView(R.layout.layout_activity_launcher);
        fragmentPager = (ViewPager) findViewById(R.id.fragment_pager);
        tabLayout = (LauncherTabLayout) findViewById(R.id.tabs);
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
                new LauncherTabLayout.Tab(R.drawable.ic_home_grey, "推荐", R.drawable.ic_home_orange),
                new LauncherTabLayout.Tab(R.drawable.ic_safari_gray, "发现", R.drawable.ic_safari_orange),
                new LauncherTabLayout.Tab(R.drawable.ic_add_white, "", R.drawable.ic_add_white),
                new LauncherTabLayout.Tab(R.drawable.ic_draft_gray, "文章", R.drawable.ic_draft_orange),
                new LauncherTabLayout.Tab(R.drawable.ic_person_gray, "个人", R.drawable.ic_person_orange)
        }, 0).setListener(new LauncherTabLayout.SwitchListener() {
            @Override
            public void onCenter() {
                PhotoPictureManager.getInstance().startToChooseMedias(LauncherActivity.this, new PhotoChooseListener() {
                    @Override
                    public void getResult(List<MediaInfo> result) {
                        ActivityHelper.startToCompose(LauncherActivity.this, result);
                    }
                }, 9);
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
