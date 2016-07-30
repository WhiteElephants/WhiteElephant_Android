package rawe.gordon.com.fruitmarketclient.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.EditText;

import rawe.gordon.com.business.db.DBManager;
import rawe.gordon.com.business.network.RestClient;
import rawe.gordon.com.business.network.responses.UserResponse;
import rawe.gordon.com.business.network.responses.pojo.UserModel;
import rawe.gordon.com.business.utils.NullTransformer;
import rawe.gordon.com.business.utils.ToastUtil;
import rawe.gordon.com.fruitmarketclient.R;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by gordon on 16/5/22.
 */
public class RegisterActivity extends BaseActivity implements View.OnClickListener {
    private View registerButton;
    private EditText phone, username, password;

    @Override
    protected int getContentLayout() {
        return R.layout.layout_register_activity;
    }

    @Override
    protected void bindViews(View rootView) {
        registerButton = rootView.findViewById(R.id.ok);
        phone = (EditText) rootView.findViewById(R.id.phone);
        username = (EditText) rootView.findViewById(R.id.username);
        password = (EditText) rootView.findViewById(R.id.password);
    }

    @Override
    protected int getMenuLayout() {
        return R.menu.empty;
    }

    @Override
    protected String getNavTitle() {
        return "Register";
    }

    @Override
    protected void prepareData() {
        registerButton.setOnClickListener(this);
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

    public static void gotoRegisterActivity(Activity start) {
        Intent intent = new Intent(start, RegisterActivity.class);
        start.startActivity(intent);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.ok) {
            if (username.getText().toString().equals("") || password.getText().toString().equals("") || phone.getText().toString().equals("")) {
                ToastUtil.say("data not enough...");
            }
            RestClient.getInstance().registerUser(username.getText().toString(), password.getText().toString(), phone.getText().toString(), new Callback<UserResponse>() {
                @Override
                public void onResponse(Call<UserResponse> call, Response<UserResponse> response) {
                    UserModel userModel = response.body().getUser();
                    if(userModel==null) return;
                    DBManager.getInstance().createUser(NullTransformer.transform(userModel.getId()),
                            NullTransformer.transform(userModel.getUsername()),
                            NullTransformer.transform(userModel.getThumbnail()),
                            NullTransformer.transform(userModel.getPhone()),
                            NullTransformer.transform(userModel.getAddress()),
                            NullTransformer.transform(userModel.getGender()),
                            NullTransformer.transform(userModel.getCreditCardNumber()),
                            NullTransformer.transform(userModel.getDeliveryAddresses()));
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            UserProfileActivity.gotoUserProfileActivity(RegisterActivity.this);
                            finishWithAnimation();
                        }
                    }, 500);
                }

                @Override
                public void onFailure(Call<UserResponse> call, Throwable t) {
                    ToastUtil.say("register failed...");
                }
            });
        }
    }
}
