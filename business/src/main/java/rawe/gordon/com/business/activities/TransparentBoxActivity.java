package rawe.gordon.com.business.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import rawe.gordon.com.business.R;
import rawe.gordon.com.business.fragments.BaseFragment;

/**
 * Created by gordon on 16/8/27.
 */
public class TransparentBoxActivity extends AppCompatActivity {
    public static final String KEY_FRAGMENT = "KEY_FRAGMENT";

    private BaseFragment fragment;

    @Override
    protected void onCreate(@Nullable Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.layout_blank_box);
        if (getIntent().getExtras() != null) {
            try {
                fragment = (BaseFragment) Class.forName(getIntent().getExtras().getString(KEY_FRAGMENT)).newInstance();
            } catch (Exception e) {
                e.printStackTrace();
            }
            getSupportFragmentManager().beginTransaction().add(R.id.box, fragment, fragment.getClass().getCanonicalName()).commitAllowingStateLoss();
        } else {
            finish();
            Log.d("TransparentBoxActivity", "no fragment data offered");
        }
    }

    public static void startFragmentInside(Activity from, Class<?> fragmentClass) {
        Intent intent = new Intent(from, TransparentBoxActivity.class);
        intent.putExtra(KEY_FRAGMENT, fragmentClass.getCanonicalName());
        from.startActivity(intent);
    }

    @Override
    public void onBackPressed() {
        fragment.handleBackPress(new BaseFragment.Callback() {
            @Override
            public void onAnimationFinish() {
                finish();
            }
        });
    }
}
