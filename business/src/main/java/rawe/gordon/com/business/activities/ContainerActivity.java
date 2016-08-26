package rawe.gordon.com.business.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import rawe.gordon.com.business.R;
import rawe.gordon.com.business.fragments.BaseFragment;

/**
 * Created by gordon on 8/25/16.
 */
public class ContainerActivity extends BaseActivity {
    public static final String KEY_FRAGMENT = "KEY_FRAGMENT";
    public static final String KEY_BUNDLE = "KEY_BUNDLE";

    private BaseFragment fragment;
    private Bundle data;

    @Override
    protected int getContentLayout() {
        return R.layout.layout_empty_container;
    }

    @Override
    protected void bindViews(View rootView) {

    }

    @Override
    protected void onGetExtras(Bundle bundle) {
        try {
            fragment = (BaseFragment) Class.forName(bundle.getString(KEY_FRAGMENT)).newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }
        data = bundle.getBundle(KEY_BUNDLE);
    }

    @Override
    protected void prepareData() {
        addFragmentWithoutEffect(fragment);
    }

    public static void startFragmentInside(Activity from, Class<?> fragmentClass, Bundle bundle) {
        Intent intent = new Intent(from, ContainerActivity.class);
        intent.putExtra(KEY_BUNDLE, bundle);
        intent.putExtra(KEY_FRAGMENT, fragmentClass.getCanonicalName());
        from.startActivity(intent);
    }
}
