package rawe.gordon.com.fruitmarketclient.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.AppCompatImageView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import rawe.gordon.com.business.utils.DrawableUtil;
import rawe.gordon.com.fruitmarketclient.R;

/**
 * Created by gordon on 16/7/31.
 */
public abstract class LauncherBaseFragment extends Fragment {
    protected boolean isViewInitiated = false;
    protected boolean isVisibleToUser = false;
    protected boolean isDataInitiated = false;

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        isViewInitiated = true;
        prepareFetchData();
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        this.isVisibleToUser = isVisibleToUser;
        prepareFetchData();
    }

    public boolean prepareFetchData() {
        return prepareFetchData(false);
    }

    public boolean prepareFetchData(boolean isForced) {
        if (isViewInitiated && isVisibleToUser && (!isDataInitiated || isForced)) {
            fetchNetWorkData();
            isDataInitiated = true;
            return true;
        }
        return false;
    }


    public static final int NO_DRAWABLE = -1;

    private View rootView;
    private RelativeLayout fragmentContainer;
    private AppCompatImageView leftIcon, rightIcon;
    private TextView title;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.layout_launcher_base_fragment, container, false);
        leftIcon = (AppCompatImageView) rootView.findViewById(R.id.left_logo);
        rightIcon = (AppCompatImageView) rootView.findViewById(R.id.right_logo);
        title = (TextView) rootView.findViewById(R.id.title);
        fragmentContainer = (RelativeLayout) rootView.findViewById(R.id.fragment_container);
        fragmentContainer.addView(inflater.inflate(getContentLayout(), container, false));
        workFlow();
        bindViews(rootView);
        prepareData();
        return rootView;
    }

    private void workFlow() {
        if (getRightDrawable() != NO_DRAWABLE)
            rightIcon.setImageDrawable(DrawableUtil.decodeFromVector(getRightDrawable()));
        if (getLeftDrawable() != NO_DRAWABLE)
            leftIcon.setImageDrawable(DrawableUtil.decodeFromVector(getLeftDrawable()));
        title.setText(getTitle());
    }

    public void changeRightIcon(int resId) {
        rightIcon.setImageDrawable(DrawableUtil.decodeFromVector(resId));
    }

    public void changeLeftIcon(int resId) {
        leftIcon.setImageDrawable(DrawableUtil.decodeFromVector(resId));
    }

    public void changeTitle(String titleText) {
        title.setText(titleText);
    }

    protected abstract int getContentLayout();

    protected abstract void bindViews(View rootView);

    protected abstract void prepareData();

    protected int getRightDrawable() {
        return NO_DRAWABLE;
    }

    protected int getLeftDrawable() {
        return NO_DRAWABLE;
    }

    protected String getTitle() {
        return "Default";
    }

    protected void onRightClicked() {

    }

    public abstract void fetchNetWorkData();
}
