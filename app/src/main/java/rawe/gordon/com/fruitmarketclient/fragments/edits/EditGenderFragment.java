package rawe.gordon.com.fruitmarketclient.fragments.edits;

import android.support.v7.widget.Toolbar;
import android.view.View;

import rawe.gordon.com.fruitmarketclient.R;
import rawe.gordon.com.fruitmarketclient.activities.BaseActivity;
import rawe.gordon.com.fruitmarketclient.fragments.BaseFragment;

/**
 * Created by gordon on 5/24/16.
 */
public class EditGenderFragment extends BaseFragment implements View.OnClickListener {
    private Toolbar toolbar;
    private View maleArea, femaleArea, maleLogo, femaleLogo;
    private String gender;

    @Override
    protected int getContentLayout() {
        return R.layout.layout_edit_gender_fragment;
    }

    @Override
    protected void bindViews(View rootView) {
        toolbar = (Toolbar) rootView.findViewById(R.id.toolbar);
        maleArea = rootView.findViewById(R.id.male_area);
        femaleArea = rootView.findViewById(R.id.female_area);
        maleLogo = rootView.findViewById(R.id.male_logo);
        femaleLogo = rootView.findViewById(R.id.female_logo);
        maleArea.setOnClickListener(this);
        femaleArea.setOnClickListener(this);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((BaseActivity) getActivity()).removeFragment(EditGenderFragment.this);
            }
        });
    }

    @Override
    protected void prepareData() {
        setGender(getGender());
    }

    @Override
    public void emitSignals() {
        listener.onSelected(getGender());
    }

    @Override
    protected int getOkDrawable() {
        return NO_DRAWABLE;
    }

    @Override
    protected void onOkClicked() {

    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.male_area) {
            setGender("male");
        } else if (view.getId() == R.id.female_area) {
            setGender("female");
        }
    }

    public void setGender(String gender) {
        this.gender = gender;
        if (gender.equals("male")) {
            maleLogo.setVisibility(View.VISIBLE);
            femaleLogo.setVisibility(View.GONE);
        } else if(gender.equals("female")){
            maleLogo.setVisibility(View.GONE);
            femaleLogo.setVisibility(View.VISIBLE);
        }else{
            maleLogo.setVisibility(View.GONE);
            femaleLogo.setVisibility(View.GONE);
        }
    }

    public void setJustGenderString(String gender) {
        this.gender = gender;
    }

    public String getGender() {
        return gender;
    }

    public static EditGenderFragment newInstance(String gender, ResultListener listener) {
        EditGenderFragment editGenderFragment = new EditGenderFragment();
        editGenderFragment.setListener(listener);
        editGenderFragment.setJustGenderString(gender);
        return editGenderFragment;
    }

    private ResultListener listener;

    public void setListener(ResultListener listener) {
        this.listener = listener;
    }

    public interface ResultListener {
        void onSelected(String genderString);
    }
}
