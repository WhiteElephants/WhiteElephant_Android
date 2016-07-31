package rawe.gordon.com.fruitmarketclient.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

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
            fetchData();
            isDataInitiated = true;
            return true;
        }
        return false;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.layout_launcher_base_fragment, container, false);
        RelativeLayout fragmentContainer = (RelativeLayout) rootView.findViewById(R.id.fragment_container);
        fragmentContainer.addView(inflater.inflate(getContentLayout(), container, false));
        bindViews(rootView);
        return rootView;
    }

    protected abstract int getContentLayout();

    protected abstract void bindViews(View rootView);

    public abstract void fetchData();
}
