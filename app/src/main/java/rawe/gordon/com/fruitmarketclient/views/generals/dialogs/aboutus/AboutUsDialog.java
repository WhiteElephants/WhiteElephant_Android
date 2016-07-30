package rawe.gordon.com.fruitmarketclient.views.generals.dialogs.aboutus;

import android.view.View;

import rawe.gordon.com.fruitmarketclient.R;
import rawe.gordon.com.fruitmarketclient.views.generals.dialogs.base.AbstractDialog;

/**
 * Created by gordon on 16/5/13.
 */
public class AboutUsDialog extends AbstractDialog {

    public AboutUsDialog() {
    }

    public static AboutUsDialog newInstance() {
        AboutUsDialog frag = new AboutUsDialog();
        return frag;
    }

    @Override
    protected int getContentLayout() {
        return R.layout.layout_about_us;
    }

    @Override
    protected void bindViews(View rootView) {

    }

    @Override
    protected void prepareData() {

    }

    @Override
    protected void onContainerClicked() {
        dismiss();
    }

    @Override
    protected boolean getTouchOutSideMode() {
        return false;
    }
}