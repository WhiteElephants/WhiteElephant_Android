package rawe.gordon.com.business.fragments;

import android.animation.ValueAnimator;
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
import rawe.gordon.com.business.activities.BaseActivity;
import rawe.gordon.com.business.utils.DrawableUtil;

/**
 * Created by gordon on 16/7/31.
 */
public abstract class BaseFragment extends Fragment implements ValueAnimator.AnimatorUpdateListener {

    public static final int NO_DRAWABLE = -1;
    public static final String TEXT_NOT_DEFINED = "TEXT_NOT_DEFINED";

    private AppCompatImageView leftIcon, rightIcon;
    private TextView leftText, rightText;
    private View leftIconArea, rightIconArea, leftTextArea, rightTextArea;
    private TextView title;
    private View titleArea;
    private RelativeLayout fragmentContainer;
    private boolean closing = false;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.layout_base_fragment, container, false);
        leftIcon = (AppCompatImageView) rootView.findViewById(R.id.left_logo);
        rightIcon = (AppCompatImageView) rootView.findViewById(R.id.right_logo);
        title = (TextView) rootView.findViewById(R.id.title);
        leftIconArea = rootView.findViewById(R.id.left_area);
        rightIconArea = rootView.findViewById(R.id.right_area);
        leftText = (TextView) rootView.findViewById(R.id.left_text);
        rightText = (TextView) rootView.findViewById(R.id.right_text);
        leftTextArea = rootView.findViewById(R.id.left_text_area);
        rightTextArea = rootView.findViewById(R.id.right_text_area);
        titleArea = rootView.findViewById(R.id.title_area);
        fragmentContainer = (RelativeLayout) rootView.findViewById(R.id.fragment_container);
        fragmentContainer.addView(inflater.inflate(getContentLayout(), container, false));
        workFlow();
        bindViews(rootView);
        prepareData();
        return rootView;
    }

    private void workFlow() {
        if (getRightDrawable() != NO_DRAWABLE) {
            rightIcon.setImageResource(getRightDrawable());
            rightIconArea.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onRightIconClicked();
                }
            });
        } else {
            rightIconArea.setVisibility(View.GONE);
        }
        if (getLeftDrawable() != NO_DRAWABLE) {
            leftIcon.setImageResource(getLeftDrawable());
            leftIconArea.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onLeftIconClicked();
                }
            });
        } else {
            leftIconArea.setVisibility(View.GONE);
        }
        if (getLeftText().equals(TEXT_NOT_DEFINED)) {
            leftTextArea.setVisibility(View.GONE);
        } else {
            leftText.setText(getLeftText());
            leftTextArea.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onLeftTextClicked();
                }
            });
        }
        if (getRightText().equals(TEXT_NOT_DEFINED)) {
            rightTextArea.setVisibility(View.GONE);
        } else {
            rightText.setText(getRightText());
            rightTextArea.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onRightTextClicked();
                }
            });
        }
        if (!enableTitle()) {
            titleArea.setVisibility(View.GONE);
        }
        title.setText(getTitle());
        if (performShutEffect()) performUpDownShutAnimation();
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

    protected String getLeftText() {
        return TEXT_NOT_DEFINED;
    }

    protected String getRightText() {
        return TEXT_NOT_DEFINED;
    }

    protected boolean enableTitle() {
        return true;
    }

    protected boolean performShutEffect() {
        return false;
    }

    protected String getTitle() {
        return "Default";
    }

    protected void onRightIconClicked() {
    }

    protected void onLeftIconClicked() {
    }

    protected void onRightTextClicked() {
    }

    protected void onLeftTextClicked() {
    }

    protected void onTitleClicked() {

    }

    protected abstract int getContentLayout();

    protected abstract void bindViews(View rootView);

    protected abstract void prepareData();

    protected void performUpDownShutAnimation() {
        ValueAnimator valueAnimator = ValueAnimator.ofFloat(1F, 0F);
        valueAnimator.addUpdateListener(this);
        valueAnimator.setDuration(500);
        valueAnimator.start();
    }

    protected void performCloseAnimation() {
        ValueAnimator valueAnimator = ValueAnimator.ofFloat(0F, 1F);
        valueAnimator.addUpdateListener(this);
        valueAnimator.setDuration(500);
        valueAnimator.start();
    }

    protected void closeWithAnimation() {
        closing = true;
        performCloseAnimation();
    }

    @Override
    public void onAnimationUpdate(ValueAnimator animation) {
        float fac = (float) animation.getAnimatedValue();
        titleArea.setTranslationY(-100 * fac);
        fragmentContainer.setTranslationY(2000 * fac);
        if (fac == 1F && closing) {
            if (((BaseActivity) getActivity()).fragments.size() <= 1)
                getActivity().finish();
            else{
                ((BaseActivity)getActivity()).removeFragment(this);
            }
        }
    }

    public void handleBackPress(){
        closeWithAnimation();
    }
}
