package rawe.gordon.com.fruitmarketclient.fragments.edits;

import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;

import rawe.gordon.com.business.utils.ToastUtil;
import rawe.gordon.com.fruitmarketclient.R;
import rawe.gordon.com.fruitmarketclient.activities.BaseActivity;
import rawe.gordon.com.fruitmarketclient.fragments.BaseFragment;
import rawe.gordon.com.business.utils.CacheBean;

/**
 * Created by gordon on 5/25/16.
 */
public class EditNameFragment extends BaseFragment implements View.OnClickListener, TextWatcher {
    public static final String EDIT_NAME_FRAGMENT = "EDIT_NAME_FRAGMENT";
    private Toolbar toolbar;
    private View cancel, inputArea;
    private EditText input;

    @Override
    protected int getContentLayout() {
        return R.layout.layout_edit_phone_fragment;
    }

    @Override
    protected void bindViews(View rootView) {
        toolbar = (Toolbar) rootView.findViewById(R.id.toolbar);
        cancel = rootView.findViewById(R.id.cancel);
        input = (EditText) rootView.findViewById(R.id.input);
        inputArea = rootView.findViewById(R.id.input_area);
    }

    @Override
    protected void prepareData() {
        toolbar.setTitle("Edit Name");
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (input.getText().toString().length() == 0) {
                    ToastUtil.say("You should at least input some words");
                    return;
                }
                if (listener != null) listener.onSelected(getName());
                ((BaseActivity) getActivity()).removeFragment(EditNameFragment.this);
            }
        });
        setName((String) CacheBean.getParam(EDIT_NAME_FRAGMENT, EDIT_NAME_FRAGMENT));
        CacheBean.clean(EDIT_NAME_FRAGMENT);
        cancel.setOnClickListener(this);
        inputArea.setOnClickListener(this);
        input.addTextChangedListener(this);
        input.requestFocus();
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

    public String getName() {
        return input.getText().toString();
    }

    public void setName(String phone) {
        input.setText(phone);
    }

    public static EditNameFragment newInstance(String name, ResultListener listener) {
        EditNameFragment editNameFragment = new EditNameFragment();
        editNameFragment.setListener(listener);
        CacheBean.putParam(EDIT_NAME_FRAGMENT, EDIT_NAME_FRAGMENT, name);
        return editNameFragment;
    }

    private ResultListener listener;

    public void setListener(ResultListener listener) {
        this.listener = listener;
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.input_area) {
            input.requestFocus();
        } else if (view.getId() == R.id.cancel) {
            setName("");
        }
    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void afterTextChanged(Editable editable) {
        update();
    }

    private void update() {
        if (input.getText().toString().equals("")) cancel.setVisibility(View.GONE);
        else cancel.setVisibility(View.VISIBLE);
    }

    public interface ResultListener {
        void onSelected(String name);
    }
}
