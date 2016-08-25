package rawe.gordon.com.business.activities;

import android.os.Bundle;
import android.view.View;

import rawe.gordon.com.business.R;

/**
 * Created by gordon on 8/25/16.
 */
public class ContainerActivity extends BaseActivity {
    @Override
    protected int getContentLayout() {
        return R.layout.layout_empty_container;
    }

    @Override
    protected void bindViews(View rootView) {

    }

    @Override
    protected int getMenuLayout() {
        return  R.menu.empty;
    }

    @Override
    protected String getNavTitle() {
        return null;
    }

    @Override
    protected void prepareData() {

    }

    @Override
    protected int getIcon() {
        return 0;
    }

    @Override
    protected void onBackAction() {

    }

    @Override
    protected void onGetExtras(Bundle bundle) {

    }
}
