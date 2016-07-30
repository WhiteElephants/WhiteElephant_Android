package rawe.gordon.com.fruitmarketclient.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.gordon.rawe.business.models.CartOrder;
import com.gordon.rawe.business.models.User;

import java.util.ArrayList;
import java.util.List;

import rawe.gordon.com.business.utils.CacheBean;
import rawe.gordon.com.business.utils.LoginManager;
import rawe.gordon.com.fruitmarketclient.R;
import rawe.gordon.com.fruitmarketclient.views.orders.OrderView;

/**
 * Created by gordon on 5/8/16.
 */
public class CartActivity extends BaseActivity implements View.OnClickListener {
    private ViewGroup container;
    private TextView priceText;
    private View proceed,noDataMask;
    private float totalPrice;

    private List<OrderView> orderViews = new ArrayList<>();

    @Override
    protected int getContentLayout() {
        return R.layout.layout_cart_activity;
    }

    @Override
    protected void bindViews(View rootView) {
        container = (ViewGroup) rootView.findViewById(R.id.container);
        priceText = (TextView) rootView.findViewById(R.id.price_text);
        proceed = rootView.findViewById(R.id.proceed_button);
        noDataMask = rootView.findViewById(R.id.no_data_mask);
        proceed.setOnClickListener(this);
    }

    @Override
    protected int getMenuLayout() {
        return R.menu.empty;
    }

    @Override
    protected String getNavTitle() {
        return "Cart Orders";
    }

    @Override
    protected void prepareData() {
        User loginUser = LoginManager.getInstance().getLogedInUser();
        if (loginUser != null) {//获取到登陆用户
            List<CartOrder> orders = LoginManager.getInstance().getCartOrders();
            if(orders != null && orders.size()>0) {
                for (CartOrder order : orders) {
                    OrderView orderView = new OrderView(this, null);
                    container.addView(orderView);
                    orderViews.add(orderView);
                    orderView.initialize(order, new OrderView.PriceListener() {
                        @Override
                        public void onPriceChanged() {
                            refreshTotalPrice();
                        }
                    });
                }
                noDataMask.setVisibility(View.GONE);
            }
            refreshTotalPrice();
        }
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

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return true;
    }

    public static void goToCartActivity(Activity start) {
        Intent intent = new Intent(start, CartActivity.class);
        start.startActivity(intent);
    }

    public void refreshTotalPrice() {
        float number = 0f;
        for (OrderView orderView : orderViews) {
            number += orderView.getTotal();
        }
        setTotalPrice(number);
    }

    public void setTotalPrice(float totalPrice) {
        this.totalPrice = totalPrice;
        priceText.setText("$ " + this.totalPrice);
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.proceed_button) {

        }
    }
}
