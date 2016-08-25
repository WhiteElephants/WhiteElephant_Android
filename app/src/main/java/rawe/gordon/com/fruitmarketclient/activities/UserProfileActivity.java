package rawe.gordon.com.fruitmarketclient.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.gordon.rawe.business.models.User;
import com.nostra13.universalimageloader.core.ImageLoader;

import rawe.gordon.com.business.activities.BaseActivity;
import rawe.gordon.com.business.db.DBManager;
import rawe.gordon.com.business.landscape.ProvinceUtil;
import rawe.gordon.com.business.network.RestClient;
import rawe.gordon.com.business.network.responses.UserResponse;
import rawe.gordon.com.business.network.responses.pojo.UserModel;
import rawe.gordon.com.business.utils.LoginManager;
import rawe.gordon.com.business.utils.NullTransformer;
import rawe.gordon.com.business.utils.ToastUtil;
import rawe.gordon.com.fruitmarketclient.R;
import rawe.gordon.com.business.fragments.EditBaseFragment;
import rawe.gordon.com.fruitmarketclient.fragments.edits.EditDistrictFragment;
import rawe.gordon.com.fruitmarketclient.fragments.edits.EditEmailFragment;
import rawe.gordon.com.fruitmarketclient.fragments.edits.EditGenderFragment;
import rawe.gordon.com.fruitmarketclient.fragments.edits.EditNameFragment;
import rawe.gordon.com.fruitmarketclient.fragments.edits.EditPhoneFragment;
import rawe.gordon.com.fruitmarketclient.generals.pops.LocationEntity;
import rawe.gordon.com.fruitmarketclient.generals.pops.PopChooser;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by gordon on 16/5/12.
 */
public class UserProfileActivity extends BaseActivity implements View.OnClickListener {
    private ImageView logo;
    private TextView nickName, userId, deliveryAddress, gender, districtText, phoneText, emailText;
    private View logout, genderArea, districtArea, phoneArea, emailArea, nameArea, deliverArea;
    private EditBaseFragment fragment;

    @Override
    protected int getContentLayout() {
        return R.layout.layout_user_center;
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
    }

    @Override
    protected int getMenuLayout() {
        return R.menu.empty;
    }

    @Override
    protected String getNavTitle() {
        return "User Profile";
    }

    @Override
    protected void prepareData() {
        if (LoginManager.getInstance().isLogin()) {
            User user = LoginManager.getInstance().getLogedInUser();
            nickName.setText(user.getUsername());
            userId.setText(String.valueOf(user.getUuid()));
            deliveryAddress.setText(user.getDeliveryAddresses());
            gender.setText(user.getGender());
            districtText.setText(user.getAddress());
            phoneText.setText(user.getPhone());
            emailText.setText(user.getEmail());
            sync(user.getUuid());
        }
        setupListeners();
        ImageLoader.getInstance().displayImage("http://depot.nipic.com/file/20150605/13378630_23102978350.jpg", logo);
    }

    @Override
    protected int getIcon() {
        return R.drawable.ic_arrow_back;
    }

    @Override
    protected void onBackAction() {
        finishWithAnimation();
    }

    @Override
    protected void onGetExtras(Bundle bundle) {

    }

    public void sync(String uuid) {
        RestClient.getInstance().getUserInfo(uuid, new Callback<UserResponse>() {
            @Override
            public void onResponse(Call<UserResponse> call, Response<UserResponse> response) {
                UserModel userModel = response.body().getUser();
                if (userModel == null) return;
                DBManager.getInstance().createUser(NullTransformer.transform(userModel.getId()),
                        NullTransformer.transform(userModel.getUsername()),
                        NullTransformer.transform(userModel.getThumbnail()),
                        NullTransformer.transform(userModel.getPhone()),
                        NullTransformer.transform(userModel.getAddress()),
                        NullTransformer.transform(userModel.getGender()),
                        NullTransformer.transform(userModel.getCreditCardNumber()),
                        NullTransformer.transform(userModel.getDeliveryAddresses()));
                User user = LoginManager.getInstance().getLogedInUser();
                nickName.setText(user.getUsername());
                userId.setText(String.valueOf(user.getUuid()));
                deliveryAddress.setText(user.getDeliveryAddresses());
                gender.setText(user.getGender());
                districtText.setText(user.getAddress());
                phoneText.setText(user.getPhone());
                emailText.setText(user.getEmail());
                ToastUtil.say("sync success ->" + user.toString());
            }

            @Override
            public void onFailure(Call<UserResponse> call, Throwable t) {
                ToastUtil.say("sync user profile data failed...");
            }
        });
    }

    public static void gotoUserProfileActivity(Activity start) {
        Intent intent = new Intent(start, UserProfileActivity.class);
        start.startActivity(intent);
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
    public void onBackPressed() {
        if (getFragments().size() > 0) {
            removeFragment(fragment);
        } else {
            super.onBackPressed();
        }
    }

    private LocationEntity locationEntity = new LocationEntity();

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.logout) {
            LoginManager.getInstance().logOut();
            finishWithAnimation();
        } else if (v.getId() == R.id.gender_area) {
            addFragment(fragment = EditGenderFragment.newInstance(gender.getText().toString().toLowerCase(), new EditGenderFragment.ResultListener() {
                @Override
                public void onSelected(String genderString) {
                    gender.setText(genderString);
                }
            }));
        } else if (v.getId() == R.id.district_area) {
            addFragment(fragment = EditDistrictFragment.newInstance(districtText.getText().toString(), new EditDistrictFragment.ResultListener() {
                @Override
                public void onSelected(String district) {
                    districtText.setText(district);
                }
            }));
        } else if (v.getId() == R.id.phone_area) {
            addFragment(fragment = EditPhoneFragment.newInstance(phoneText.getText().toString(), new EditPhoneFragment.ResultListener() {
                @Override
                public void onSelected(String phone) {
                    phoneText.setText(phone);
                }
            }));
        } else if (v.getId() == R.id.email_area) {
            addFragment(fragment = EditEmailFragment.newInstance(emailText.getText().toString(), new EditEmailFragment.ResultListener() {
                @Override
                public void onSelected(String email) {
                    emailText.setText(email);
                }
            }));
        } else if (v.getId() == R.id.name_area) {
            addFragment(fragment = EditNameFragment.newInstance(nickName.getText().toString(), new EditNameFragment.ResultListener() {
                @Override
                public void onSelected(String name) {
                    nickName.setText(name);
                }
            }));
        } else if (v.getId() == R.id.deliver_area) {
//            ProvinceUtil.getProvinces(getApplicationContext());
            new PopChooser(UserProfileActivity.this, PopChooser.PopMenuLevel.LEVEL1,
                    locationEntity, ProvinceUtil.getProvinces(getApplicationContext())).setListener(new PopChooser.PopChooserListener() {
                @Override
                public void finish(LocationEntity locationEntity) {
                    ToastUtil.say(locationEntity.toString());
                }
            }).show();
        }
    }
}
