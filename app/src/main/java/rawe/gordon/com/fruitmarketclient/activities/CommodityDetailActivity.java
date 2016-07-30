package rawe.gordon.com.fruitmarketclient.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;


import com.gordon.rawe.business.models.User;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import rawe.gordon.com.business.db.DBManager;
import rawe.gordon.com.business.utils.CacheBean;
import rawe.gordon.com.business.utils.LoginManager;
import rawe.gordon.com.business.utils.ToastUtil;
import rawe.gordon.com.business.utils.ViewSizeRegulator;
import rawe.gordon.com.fruitmarketclient.R;
import rawe.gordon.com.business.network.RestClient;
import rawe.gordon.com.business.network.responses.CommodityResponse;
import rawe.gordon.com.business.network.responses.OrderResponse;
import rawe.gordon.com.business.network.responses.pojo.CommodityModel;
import rawe.gordon.com.business.network.responses.pojo.OrderModel;
import rawe.gordon.com.business.network.responses.pojo.SlideModel;
import rawe.gordon.com.fruitmarketclient.views.comments.CommentGrids;
import rawe.gordon.com.fruitmarketclient.views.generals.dialogs.addcart.AddToCartChooseDialog;
import rawe.gordon.com.fruitmarketclient.views.generals.slides.SlideView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by gordon on 5/8/16.
 */
public class CommodityDetailActivity extends BaseActivity {

    public final static String UUID = "UUID";

    private SlideView slideView;
    private CommentGrids commentGrids;
    private TextView name, price, description;
    private CommodityModel model;

    private String uuid;
    private List<SlideModel> portfolio = new ArrayList<>();

    @Override
    protected int getContentLayout() {
        return R.layout.layout_commodity_detail_activity;
    }

    @Override
    protected void bindViews(View rootView) {
        slideView = (SlideView) rootView.findViewById(R.id.slides);
        commentGrids = (CommentGrids) rootView.findViewById(R.id.comment_grids);
        name = (TextView) rootView.findViewById(R.id.name);
        price = (TextView) rootView.findViewById(R.id.price);
        description = (TextView) rootView.findViewById(R.id.description);
    }

    @Override
    protected int getMenuLayout() {
        return R.menu.to_cart;
    }

    @Override
    protected String getNavTitle() {
        return "Commodity Detail";
    }

    @Override
    protected void prepareData() {
        ViewSizeRegulator.regulateScreenRatio(slideView, 3 / 2F);
        commentGrids.initialize(Arrays.asList(
                new SlideModel("Sketch. Published in User experience, User interface, Android development • October 27th, 2014 • 6 Comments."),
                new SlideModel("https://www.youtube.com/watch?v=E6c3DGnvef 2014年12月27日 - 上传者：Radiant Silver Labs"),
                new SlideModel("Android development • October 27th, 2014 • 6 Comments."),
                new SlideModel("https://www.youtube.com/watch?v=E6c3DGnvef 2014")
        ), this);
        RestClient.getInstance().getCommodityById(uuid, new Callback<CommodityResponse>() {
            @Override
            public void onResponse(Call<CommodityResponse> call, Response<CommodityResponse> response) {
                model = response.body().getCommodity();
                //幻灯片
                for (String img : model.getPortfolio().split(";")) {
                    portfolio.add(new SlideModel(img));
                }
                slideView.initialize(portfolio);
                //标题
                name.setText(model.getName());
                price.setText("$ " + model.getPrice());
                description.setText(model.getDescriptions());
            }

            @Override
            public void onFailure(Call<CommodityResponse> call, Throwable t) {
                ToastUtil.say("fetch data failed...");
            }
        });
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
        if (bundle != null) uuid = bundle.getString(UUID);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.to_cart:
                CartActivity.goToCartActivity(this);
                break;
            case R.id.add_to_cart:
                AddToCartChooseDialog.newInstance(new AddToCartChooseDialog.StateListener() {
                    @Override
                    public void onStateChanged(String color, String size, int count) {
                        if (LoginManager.getInstance().isLogin()) {
                            User loginUser = LoginManager.getInstance().getLogedInUser();
                            RestClient.getInstance().addCommodityToCart(loginUser.getUuid(), model.getId(), color, size, model.getThumbnail(), count, model.getPrice(), model.getName(), new Callback<OrderResponse>() {
                                @Override
                                public void onResponse(Call<OrderResponse> call, Response<OrderResponse> response) {
                                    OrderModel orderModel = response.body().getOrder();
                                    if (orderModel != null) {
                                        DBManager.getInstance().createOrder(orderModel.getId(), orderModel.getAmount(), orderModel.getColor(), orderModel.getSize(),
                                                orderModel.getThumbnail(), orderModel.getPrice(), orderModel.getName());
                                        finish();
                                        CartActivity.goToCartActivity(CommodityDetailActivity.this);
                                    }
                                }

                                @Override
                                public void onFailure(Call<OrderResponse> call, Throwable t) {
                                    ToastUtil.say(t.toString());
                                }
                            });

                        }

                    }
                }).show(getSupportFragmentManager(), "add to cart");
                break;
        }
        return true;
    }

    public static void gotoCommodityDetailActivity(Activity start, String uuid) {
        Intent intent = new Intent(start, CommodityDetailActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString(UUID, uuid);
        intent.putExtras(bundle);
        start.startActivity(intent);
    }
}
