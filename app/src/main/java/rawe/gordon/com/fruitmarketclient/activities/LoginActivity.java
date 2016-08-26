package rawe.gordon.com.fruitmarketclient.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import java.util.List;

import rawe.gordon.com.business.activities.BaseActivity;
import rawe.gordon.com.business.db.DBManager;
import rawe.gordon.com.business.utils.NullTransformer;
import rawe.gordon.com.business.utils.ToastUtil;
import rawe.gordon.com.fruitmarketclient.R;
import rawe.gordon.com.business.network.RestClient;
import rawe.gordon.com.business.network.responses.UserResponse;
import rawe.gordon.com.business.network.responses.pojo.OrderModel;
import rawe.gordon.com.business.network.responses.pojo.UserModel;
import rawe.gordon.com.business.utils.CacheBean;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by gordon on 16/5/22.
 */
public class LoginActivity extends BaseActivity implements View.OnClickListener {
    private View registerButton, okButton;
    private EditText username, password;
    public static final String LOGIN_LISTENER = "LOGIN_LISTENER";
    private LoginListener listener;

    @Override
    protected int getContentLayout() {
        return R.layout.layout_login_activity;
    }

    @Override
    protected void bindViews(View rootView) {
        okButton = rootView.findViewById(R.id.ok);
        username = (EditText) rootView.findViewById(R.id.username);
        password = (EditText) rootView.findViewById(R.id.password);
        registerButton = rootView.findViewById(R.id.register);
    }

//    @Override
//    protected int getMenuLayout() {
//        return R.menu.empty;
//    }
//
//    @Override
//    protected String getNavTitle() {
//        return "Login";
//    }

    @Override
    protected void prepareData() {
        okButton.setOnClickListener(this);
        registerButton.setOnClickListener(this);
    }

//    @Override
//    protected int getIcon() {
//        return R.drawable.ic_arrow_back;
//    }
//
//    @Override
//    protected void onBackAction() {
//        finishWithAnimation();
//    }

//    @Override
//    protected void onGetExtras(Bundle bundle) {
//        listener = (LoginListener) CacheBean.getParam(LOGIN_LISTENER, LOGIN_LISTENER);
//        CacheBean.clean(LOGIN_LISTENER);
//    }

    public static void gotoLoginActivity(Activity start, LoginListener listener) {
        Intent intent = new Intent(start, LoginActivity.class);
        CacheBean.putParam(LOGIN_LISTENER, LOGIN_LISTENER, listener);
        start.startActivity(intent);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.ok) {
            RestClient.getInstance().authenticate(username.getText().toString(), password.getText().toString(), new Callback<UserResponse>() {
                @Override
                public void onResponse(Call<UserResponse> call, Response<UserResponse> response) {
                    UserModel userModel = response.body().getUser();
                    List<OrderModel> orders = response.body().getOrders();
                    if (userModel != null) {
                        DBManager.getInstance().createUser(NullTransformer.transform(userModel.getId()),
                                NullTransformer.transform(userModel.getUsername()),
                                NullTransformer.transform(userModel.getThumbnail()),
                                NullTransformer.transform(userModel.getPhone()),
                                NullTransformer.transform(userModel.getAddress()),
                                NullTransformer.transform(userModel.getGender()),
                                NullTransformer.transform(userModel.getCreditCardNumber()),
                                NullTransformer.transform(userModel.getDeliveryAddresses()));
                        for (OrderModel orderModel : orders) {
                            DBManager.getInstance().createOrder(orderModel.getId(), orderModel.getAmount(), orderModel.getColor(), orderModel.getSize(),
                                    orderModel.getThumbnail(), orderModel.getPrice(), orderModel.getName());
                        }
                        if (listener != null) listener.onResult(LoginStatus.SUCCESS);
                        finish();
                    }
                }

                @Override
                public void onFailure(Call<UserResponse> call, Throwable t) {
                    ToastUtil.say("authenfication error happened");
                    if (listener != null) listener.onResult(LoginStatus.FAILED);
                }
            });
        } else if (v.getId() == R.id.register) {
            RegisterActivity.gotoRegisterActivity(this);
//            finishWithAnimation();
        }
    }

    public interface LoginListener {
        void onResult(LoginStatus status);
    }

    public enum LoginStatus {
        SUCCESS, FAILED
    }
}
