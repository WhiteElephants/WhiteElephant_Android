package rawe.gordon.com.fruitmarketclient.views.generals.dialogs.filter;

import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import rawe.gordon.com.business.utils.CacheBean;
import rawe.gordon.com.business.utils.ToastUtil;
import rawe.gordon.com.fruitmarketclient.R;
import rawe.gordon.com.fruitmarketclient.views.generals.dialogs.base.AbstractDialog;

/**
 * Created by gordon on 16/5/13.
 */
public class FilterDialog extends AbstractDialog {

    private View selectCategory;
    private TextView selectedCategoryText;
    private EditText fromText, toText;

    private List<String> selectedCategories = new ArrayList<>();
    private float from, to;


    public static FilterDialog newInstance() {
        FilterDialog frag = new FilterDialog();
        return frag;
    }

    @Override
    protected int getContentLayout() {
        return R.layout.layout_filter_view;
    }

    @Override
    protected void bindViews(View rootView) {
        selectCategory = rootView.findViewById(R.id.select_category);
        selectedCategoryText = (TextView) rootView.findViewById(R.id.select_category_text);
        fromText = (EditText) rootView.findViewById(R.id.from);
        toText = (EditText) rootView.findViewById(R.id.to);
        rootView.findViewById(R.id.ok).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ToastUtil.say(getState());
                dismiss();
            }
        });
    }

    @Override
    protected void prepareData() {
        setFrom(0f);
        setTo(100f);
        addCategoryLink();
    }

    @Override
    protected void onContainerClicked() {

    }

    @Override
    protected boolean getTouchOutSideMode() {
        return false;
    }

    private void addCategoryLink() {
        selectCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CategorySelectDialog.newInstance(new CategorySelectDialog.SelectResultListener() {
                    @Override
                    public void onSelected(List<String> selects) {
                        selectedCategories = selects;
                        if (selectedCategories.size() > 0)
                            selectedCategoryText.setText(selects.toString());
                        else
                            selectedCategoryText.setText(getContext().getResources().getString(R.string.select_commodity_categories));
                    }
                }, selectedCategories).show(getFragmentManager(), "category manager");
            }
        });
    }

    private String getState() {
        return selectedCategories.toString() + "[" + from + "][" + to + "]";
    }

    public void setFrom(float from) {
        this.from = from;
        fromText.setText(String.format("%.2f", from));
    }

    public void setTo(float to) {
        this.to = to;
        toText.setText(String.format("%.2f", to));
    }
}