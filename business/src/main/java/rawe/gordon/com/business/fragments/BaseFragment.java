package rawe.gordon.com.business.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.AppCompatImageView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import rawe.gordon.com.business.R;
import rawe.gordon.com.business.utils.DrawableUtil;

/**
 * Created by gordon on 16/7/31.
 */
public abstract class BaseFragment extends Fragment {

    public static final int NO_DRAWABLE = -1;

    private AppCompatImageView leftIcon, rightIcon;
    private View leftArea, rightArea;
    private TextView title;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.layout_launcher_base_fragment, container, false);
        leftIcon = (AppCompatImageView) rootView.findViewById(R.id.left_logo);
        rightIcon = (AppCompatImageView) rootView.findViewById(R.id.right_logo);
        title = (TextView) rootView.findViewById(R.id.title);
        leftArea = rootView.findViewById(R.id.left_area);
        rightArea = rootView.findViewById(R.id.right_area);
        RelativeLayout fragmentContainer = (RelativeLayout) rootView.findViewById(R.id.fragment_container);
        fragmentContainer.addView(inflater.inflate(getContentLayout(), container, false));
        workFlow();
        bindViews(rootView);
        prepareData();
        return rootView;
    }

    private void workFlow() {
        if (getRightDrawable() != NO_DRAWABLE) {
            rightIcon.setImageResource(getRightDrawable());
            rightArea.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onRightClicked();
                }
            });
        } else {
            rightArea.setVisibility(View.GONE);
        }
        if (getLeftDrawable() != NO_DRAWABLE) {
            leftIcon.setImageResource(getLeftDrawable());
            leftArea.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onLeftClicked();
                }
            });
        } else {
            leftArea.setVisibility(View.GONE);
        }

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

    protected void onLeftClicked() {
    }

    protected void onTitleClicked() {
    }


    protected abstract int getContentLayout();

    protected abstract void bindViews(View rootView);

    protected abstract void prepareData();
}
