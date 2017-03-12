package rawe.gordon.com.fruitmarketclient.old.fragments.launcher;

import android.annotation.TargetApi;
import android.os.Build;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import rawe.gordon.com.fruitmarketclient.R;
import rawe.gordon.com.business.fragments.LauncherBaseFragment;
import rawe.gordon.com.fruitmarketclient.old.generals.pops.LocationEntity;

/**
 * Created by gordon on 16/7/31.
 */
public class LauncherFourthFragment extends LauncherBaseFragment implements View.OnClickListener {
    private ImageView logo;
    private TextView nickName, userId, deliveryAddress, gender, districtText, phoneText, emailText;
    private View logout, genderArea, districtArea, phoneArea, emailArea, nameArea, deliverArea;
    private LocationEntity locationEntity = new LocationEntity();
    private Toolbar toolbar;

    public static LauncherFourthFragment newInstance() {
        LauncherFourthFragment fourthLauncher = new LauncherFourthFragment();
        return fourthLauncher;
    }

    @Override
    protected int getContentLayout() {
        return R.layout.layout_launcher_fourth_fragment;
    }

    @Override
    protected void bindViews(View rootView) {
        logo = (ImageView) rootView.findViewById(R.id.logo);
        nickName = (TextView) rootView.findViewById(R.id.nickname);
        nameArea = rootView.findViewById(R.id.name_area);
        userId = (TextView) rootView.findViewById(R.id.user_id);
        deliveryAddress = (TextView) rootView.findViewById(R.id.delivery_address);
        deliverArea = rootView.findViewById(R.id.deliver_area);
        gender = (TextView) rootView.findViewById(R.id.gender);
        genderArea = rootView.findViewById(R.id.gender_area);
        districtText = (TextView) rootView.findViewById(R.id.district);
        districtArea = rootView.findViewById(R.id.district_area);
        phoneText = (TextView) rootView.findViewById(R.id.phone);
        phoneArea = rootView.findViewById(R.id.phone_area);
        emailText = (TextView) rootView.findViewById(R.id.email);
        emailArea = rootView.findViewById(R.id.email_area);
        logout = rootView.findViewById(R.id.logout);
        toolbar = (Toolbar) rootView.findViewById(R.id.toolbar);
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
    @Override
    protected void prepareData() {
        setupListeners();
    }

    @Override
    public void fetchNetWorkData() {

    }

    private void setupListeners() {
        logout.setOnClickListener(this);
        genderArea.setOnClickListener(this);
        districtArea.setOnClickListener(this);
        phoneArea.setOnClickListener(this);
        emailArea.setOnClickListener(this);
        nameArea.setOnClickListener(this);
        deliverArea.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
//        if (v.getId() == R.id.logout) {
//            LoginManager.getInstance().logOut();
//            finishWithAnimation();
//        } else if (v.getId() == R.id.gender_area) {
//            addFragment(fragment = EditGenderFragment.newInstance(gender.getText().toString().toLowerCase(), new EditGenderFragment.ResultListener() {
//                @Override
//                public void onSelected(String genderString) {
//                    gender.setText(genderString);
//                }
//            }));
//        } else if (v.getId() == R.id.district_area) {
//            addFragment(fragment = EditDistrictFragment.newInstance(districtText.getText().toString(), new EditDistrictFragment.ResultListener() {
//                @Override
//                public void onSelected(String district) {
//                    districtText.setText(district);
//                }
//            }));
//        } else if (v.getId() == R.id.phone_area) {
//            addFragment(fragment = EditPhoneFragment.newInstance(phoneText.getText().toString(), new EditPhoneFragment.ResultListener() {
//                @Override
//                public void onSelected(String phone) {
//                    phoneText.setText(phone);
//                }
//            }));
//        } else if (v.getId() == R.id.email_area) {
//            addFragment(fragment = EditEmailFragment.newInstance(emailText.getText().toString(), new EditEmailFragment.ResultListener() {
//                @Override
//                public void onSelected(String email) {
//                    emailText.setText(email);
//                }
//            }));
//        } else if (v.getId() == R.id.name_area) {
//            addFragment(fragment = EditNameFragment.newInstance(nickName.getText().toString(), new EditNameFragment.ResultListener() {
//                @Override
//                public void onSelected(String name) {
//                    nickName.setText(name);
//                }
//            }));
//        } else if (v.getId() == R.id.deliver_area) {
////            ProvinceUtil.getProvinces(getApplicationContext());
//            new PopChooser(getContext(), PopChooser.PopMenuLevel.LEVEL1,
//                    locationEntity, ProvinceUtil.getProvinces(getContext())).setListener(new PopChooser.PopChooserListener() {
//                @Override
//                public void finish(LocationEntity locationEntity) {
//                    ToastUtil.say(locationEntity.toString());
//                }
//            }).show();
//        }
    }

    @Override
    protected String getTitle() {
        return "个人中心";
    }
}
