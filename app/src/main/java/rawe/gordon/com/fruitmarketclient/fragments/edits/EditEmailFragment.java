package rawe.gordon.com.fruitmarketclient.fragments.edits;

import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;

import rawe.gordon.com.business.utils.RegexUtil;
import rawe.gordon.com.business.utils.ToastUtil;
import rawe.gordon.com.fruitmarketclient.R;
import rawe.gordon.com.business.activities.BaseActivity;
import rawe.gordon.com.business.fragments.EditBaseFragment;
import rawe.gordon.com.business.utils.CacheBean;

/**
 * Created by gordon on 5/25/16.
 */
public class EditEmailFragment extends EditBaseFragment implements View.OnClickListener, TextWatcher {
    public static final String EDIT_EMAIL_FRAGMENT = "EDIT_EMAIL_FRAGMENT";
    private Toolbar toolbar;
    private View cancel, inputArea;
    private EditText input;

    @Override
    protected int getContentLayout() {
        return R.layout.layout_edit_name_fragment;
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
        toolbar.setTitle("Edit Email");
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!RegexUtil.validateEmail(input.getText().toString())) {
                    ToastUtil.say("Phone number format incorrect");
                    return;
                }
                if (listener != null) listener.onSelected(getEmail());
//                ((BaseActivity) getActivity()).removeFragment(EditEmailFragment.this);
            }
        });
        setEmail((String) CacheBean.getParam(EDIT_EMAIL_FRAGMENT, EDIT_EMAIL_FRAGMENT));
        CacheBean.clean(EDIT_EMAIL_FRAGMENT);
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

    public String getEmail() {
        return input.getText().toString();
    }

    public void setEmail(String phone) {
        input.setText(phone);
    }

    public static EditEmailFragment newInstance(String phone, ResultListener listener) {
        EditEmailFragment editEmailFragment = new EditEmailFragment();
        editEmailFragment.setListener(listener);
        CacheBean.putParam(EDIT_EMAIL_FRAGMENT, EDIT_EMAIL_FRAGMENT, phone);
        return editEmailFragment;
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
            setEmail("");
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
        void onSelected(String email);
    }
}
