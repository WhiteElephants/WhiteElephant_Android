package rawe.gordon.com.fruitmarketclient.old.fragments.edits;

import android.support.v7.widget.Toolbar;
import android.view.View;

import rawe.gordon.com.fruitmarketclient.R;
import rawe.gordon.com.business.fragments.EditBaseFragment;
import rawe.gordon.com.business.utils.CacheBean;

/**
 * Created by gordon on 5/25/16.
 */
public class EditLogoFragment extends EditBaseFragment implements View.OnClickListener {
    public static final String EDIT_LOGO_FRAGMENT = "EDIT_NAME_FRAGMENT";
    private Toolbar toolbar;
    private String logoUrl;

    @Override
    protected int getContentLayout() {
        return R.layout.layout_edit_logo_fragment;
    }

    @Override
    protected void bindViews(View rootView) {
        toolbar = (Toolbar) rootView.findViewById(R.id.toolbar);
    }

    @Override
    protected void prepareData() {
        toolbar.setTitle("Edit Phone");
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) listener.onSelected(getLogoUrl());
//                ((BaseActivity) getActivity()).removeFragment(EditLogoFragment.this);
            }
        });
        setLogoUrl((String) CacheBean.getParam(EDIT_LOGO_FRAGMENT, EDIT_LOGO_FRAGMENT));
        CacheBean.clean(EDIT_LOGO_FRAGMENT);
    }

    @Override
    public void emitSignals() {

    }

    @Override
    protected int getOkDrawable() {
        return NO_DRAWABLE;
    }

    @Override
    protected void onOkClicked() {

    }

    public String getLogoUrl() {
        return logoUrl;
    }

    public void setLogoUrl(String logoUrl) {
        this.logoUrl = logoUrl;
    }

    public static EditLogoFragment newInstance(String logoUrl, ResultListener listener) {
        EditLogoFragment editLogoFragment = new EditLogoFragment();
        editLogoFragment.setListener(listener);
        CacheBean.putParam(EDIT_LOGO_FRAGMENT, EDIT_LOGO_FRAGMENT, logoUrl);
        return editLogoFragment;
    }

    private ResultListener listener;

    public void setListener(ResultListener listener) {
        this.listener = listener;
    }

    @Override
    public void onClick(View view) {

    }

    public interface ResultListener {
        void onSelected(String name);
    }
}
