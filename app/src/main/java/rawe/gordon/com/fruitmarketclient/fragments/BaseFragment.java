package rawe.gordon.com.fruitmarketclient.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.AppCompatImageView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import rawe.gordon.com.business.utils.CacheBean;
import rawe.gordon.com.business.utils.DrawableUtil;
import rawe.gordon.com.fruitmarketclient.R;

/**
 * Created by gordon on 5/24/16.
 */
public abstract class BaseFragment extends Fragment {

    public static final int NO_DRAWABLE = -1;

    private View rootView;
    private RelativeLayout fragmentContainer;
    private View okArea;
    private AppCompatImageView okImage;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.layout_base_fragment, container, false);
        okArea = rootView.findViewById(R.id.ok_area);
        okImage = (AppCompatImageView) rootView.findViewById(R.id.ok_image);
        if (getOkDrawable() != NO_DRAWABLE) {
            okImage.setImageDrawable(DrawableUtil.decodeFromVector(getOkDrawable()));
            okArea.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onOkClicked();
                }
            });
        } else {
            okArea.setVisibility(View.GONE);
        }

        fragmentContainer = (RelativeLayout) rootView.findViewById(R.id.fragment_container);
        fragmentContainer.addView(inflater.inflate(getContentLayout(), container, false));
        bindViews(rootView);
        prepareData();
        return rootView;
    }

    public void changeOKIcon(int resId) {
        okImage.setImageDrawable(DrawableUtil.decodeFromVector(resId));
    }

    protected abstract int getContentLayout();

    protected abstract void bindViews(View rootView);

    protected abstract void prepareData();

    public abstract void emitSignals();

    protected abstract int getOkDrawable();

    protected abstract void onOkClicked();
}
