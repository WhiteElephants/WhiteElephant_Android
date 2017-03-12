package rawe.gordon.com.fruitmarketclient.old.activities;

import android.app.Activity;
import android.content.Intent;
import android.view.View;

import rawe.gordon.com.business.activities.BaseActivity;
import rawe.gordon.com.fruitmarketclient.R;

/**
 * Created by gordon on 16/5/12.
 */
public class PaymentActivity extends BaseActivity {
    @Override
    protected int getContentLayout() {
        return R.layout.layout_payment;
    }

    @Override
    protected void bindViews(View rootView) {

    }
//
//    @Override
//    protected int getMenuLayout() {
//        return R.menu.empty;
//    }
//
//    @Override
//    protected String getNavTitle() {
//        return "Payment";
//    }

    @Override
    protected void prepareData() {

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
//
//    @Override
//    protected void onGetExtras(Bundle bundle) {
//
//    }

    public static void gotoPaymentActivity(Activity start) {
        Intent intent = new Intent(start, PaymentActivity.class);
        start.startActivity(intent);
    }
}
