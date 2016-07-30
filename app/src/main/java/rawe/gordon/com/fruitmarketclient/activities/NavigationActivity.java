package rawe.gordon.com.fruitmarketclient.activities;

import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.facebook.drawee.view.SimpleDraweeView;

import rawe.gordon.com.business.utils.CacheBean;
import rawe.gordon.com.business.utils.LoginManager;
import rawe.gordon.com.business.utils.ToastUtil;
import rawe.gordon.com.business.utils.ViewSizeRegulator;
import rawe.gordon.com.fruitmarketclient.R;
import rawe.gordon.com.business.application.SharedParameter;
import rawe.gordon.com.business.network.RestClient;
import rawe.gordon.com.business.network.responses.PopularResponse;
import rawe.gordon.com.business.network.responses.SlideResponse;
import rawe.gordon.com.fruitmarketclient.views.generals.dialogs.filter.FilterDialog;
import rawe.gordon.com.fruitmarketclient.views.generals.pulls.PullToRefreshLayout;
import rawe.gordon.com.fruitmarketclient.views.generals.slides.SlideView;
import rawe.gordon.com.fruitmarketclient.views.homepage.populars.PopularGrids;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NavigationActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, PullToRefreshLayout.OnRefreshListener, View.OnClickListener {

    private DrawerLayout drawer;
    private NavigationView navigationView;
    private Toolbar toolbar;
    private ActionBarDrawerToggle toggle;
    private SlideView slideView;
    private PopularGrids populars;
    private PullToRefreshLayout refreshView;
    private SimpleDraweeView logo;
    private View searchArea;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        /** some initializing work */
        SharedParameter.getInstance().generateScreenParameters(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_activity_navigation);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView = (NavigationView) findViewById(R.id.nav_view);
        slideView = (SlideView) findViewById(R.id.slides);
        searchArea = findViewById(R.id.search_area);
        refreshView = (PullToRefreshLayout) findViewById(R.id.fresh_view);
        navigationView.setNavigationItemSelectedListener(this);
        searchArea.setOnClickListener(this);
        populars = (PopularGrids) findViewById(R.id.populars);
        logo = (SimpleDraweeView) navigationView.getHeaderView(0).findViewById(R.id.logo);
        initialize();
    }

    private void initialize() {
        getHomeSlides();
        ViewSizeRegulator.regulateScreenRatio(slideView, 16 / 9F);
        getFirstBatchOfPopulars();
        refreshView.setOnRefreshListener(this);
        logo.setImageURI(Uri.parse("http://depot.nipic.com/file/20150605/13378630_23102978350.jpg"));
    }

    private void getFirstBatchOfPopulars() {
        RestClient.getInstance().getPopularCommodities(new Callback<PopularResponse>() {
            @Override
            public void onResponse(Call<PopularResponse> call, Response<PopularResponse> response) {
                populars.initialize(response.body().getCommodities(), NavigationActivity.this);
                refreshView.loadmoreFinish(PullToRefreshLayout.SUCCEED);
            }

            @Override
            public void onFailure(Call<PopularResponse> call, Throwable t) {
                ToastUtil.say("fetch populars failed...");
            }
        });
    }

    private void getHomeSlides() {
        RestClient.getInstance().getHomeSlides(new Callback<SlideResponse>() {
            @Override
            public void onResponse(Call<SlideResponse> call, Response<SlideResponse> response) {
                if (response.body() != null && response.body().getSlides() != null)
                    slideView.initialize(response.body().getSlides());
            }

            @Override
            public void onFailure(Call<SlideResponse> call, Throwable t) {
                ToastUtil.say("fetch populars failed...");
            }
        });
    }

    private void getMorePopulars() {
        RestClient.getInstance().getPopularCommodities(new Callback<PopularResponse>() {
            @Override
            public void onResponse(Call<PopularResponse> call, Response<PopularResponse> response) {
                populars.addBatch(response.body().getCommodities());
                refreshView.loadmoreFinish(PullToRefreshLayout.SUCCEED);
            }

            @Override
            public void onFailure(Call<PopularResponse> call, Throwable t) {
                ToastUtil.say("fetch populars failed...");
            }
        });
    }

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            closeDrawer();
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.homepage, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.filter:
                FilterDialog.newInstance().show(getSupportFragmentManager(), "filter_dialog");
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle homepage view item clicks here.
        int id = item.getItemId();
        if (id == R.id.profile) {
            if (LoginManager.getInstance().isLogin()) {
                UserProfileActivity.gotoUserProfileActivity(this);
            } else {
                LoginActivity.gotoLoginActivity(this, new LoginActivity.LoginListener() {
                    @Override
                    public void onResult(LoginActivity.LoginStatus status) {
                        if (status == LoginActivity.LoginStatus.SUCCESS) {
                            UserProfileActivity.gotoUserProfileActivity(NavigationActivity.this);
                        } else if (status == LoginActivity.LoginStatus.FAILED) {

                        }
                    }
                });
            }
        } else if (id == R.id.cart) {
            if (LoginManager.getInstance().isLogin()) {
                CartActivity.goToCartActivity(this);
            } else {
                LoginActivity.gotoLoginActivity(this, new LoginActivity.LoginListener() {
                    @Override
                    public void onResult(LoginActivity.LoginStatus status) {
                        if (status == LoginActivity.LoginStatus.SUCCESS) {
                            CartActivity.goToCartActivity(NavigationActivity.this);
                        } else if (status == LoginActivity.LoginStatus.FAILED) {

                        }
                    }
                });
            }
        } else if (id == R.id.settings) {
            SettingsActivity.gotoSettingsActivity(this);
        } else if (id == R.id.payment) {
            PaymentActivity.gotoPaymentActivity(this);
        }
        closeDrawer();
        return true;
    }

    private void closeDrawer() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        }
    }

    @Override
    public void onRefresh(PullToRefreshLayout pullToRefreshLayout) {

    }

    @Override
    public void onLoadMore(PullToRefreshLayout pullToRefreshLayout) {
        getMorePopulars();
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.search_area) {
            SearchActivity.gotoSearchActivity(this);
        }
    }
}
