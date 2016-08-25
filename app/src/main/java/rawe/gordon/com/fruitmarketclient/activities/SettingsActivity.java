package rawe.gordon.com.fruitmarketclient.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.Switch;

import rawe.gordon.com.business.activities.BaseActivity;
import rawe.gordon.com.fruitmarketclient.R;
import rawe.gordon.com.business.application.SystemConfig;
import rawe.gordon.com.fruitmarketclient.generals.dialogs.aboutus.AboutUsDialog;

/**
 * Created by gordon on 16/5/12.
 */
public class SettingsActivity extends BaseActivity implements CompoundButton.OnCheckedChangeListener, View.OnClickListener {
    private Switch notification, recommendation;
    private View rootView;
    private View aboutUs;

    @Override
    protected int getContentLayout() {
        return R.layout.layout_settings;
    }

    @Override
    protected void bindViews(View rootView) {
        this.rootView = rootView;
        notification = (Switch) rootView.findViewById(R.id.notification);
        recommendation = (Switch) rootView.findViewById(R.id.recommendation);
        aboutUs = findViewById(R.id.about_us);
    }

    @Override
    protected int getMenuLayout() {
        return R.menu.empty;
    }

    @Override
    protected String getNavTitle() {
        return "Settings";
    }

    @Override
    protected void prepareData() {
        notification.setChecked(SystemConfig.getInstance().isAcceptSystemNotification());
        recommendation.setChecked(SystemConfig.getInstance().isAcceptActivitiesRecommendations());
        notification.setOnCheckedChangeListener(this);
        recommendation.setOnCheckedChangeListener(this);
        aboutUs.setOnClickListener(this);
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

    public static void gotoSettingsActivity(Activity start) {
        Intent intent = new Intent(start, SettingsActivity.class);
        start.startActivity(intent);
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        if (buttonView.getId() == R.id.notification) {
            SystemConfig.getInstance().setAcceptSystemNotification(isChecked);
        } else if (buttonView.getId() == R.id.recommendation) {
            SystemConfig.getInstance().setAcceptActivitiesRecommendations(isChecked);
        }
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.about_us) {
            AboutUsDialog.newInstance().show(getSupportFragmentManager(),"dialog");
        }
    }
}
