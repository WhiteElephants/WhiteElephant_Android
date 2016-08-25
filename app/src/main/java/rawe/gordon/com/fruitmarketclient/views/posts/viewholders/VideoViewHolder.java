package rawe.gordon.com.fruitmarketclient.views.posts.viewholders;

import android.animation.ValueAnimator;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.OvershootInterpolator;
import android.widget.EditText;

import rawe.gordon.com.business.utils.DimenUtil;
import rawe.gordon.com.fruitmarketclient.R;
import rawe.gordon.com.fruitmarketclient.views.posts.StateChangeListener;
import rawe.gordon.com.fruitmarketclient.views.posts.models.VideoNode;
import rawe.gordon.com.fruitmarketclient.views.posts.subviews.RatioImageView;
import rawe.gordon.com.fruitmarketclient.views.posts.watch.EditTextWatcher;

/**
 * Created by gordon on 16/8/23.
 */
public class VideoViewHolder extends RecyclerView.ViewHolder implements TextWatcher {

    private AppCompatImageView add, addSubImage;
    private View addArea, threeArea, twoArea, oneArea, addSubArea, subInput;
    private float unitExpandDistance = DimenUtil.dip2pix(48), textTopDistance = DimenUtil.dip2pix(140);
    private int maxRotation = 45;
    private boolean menuExpanded = false, menuAnimating, textAreaExpanded, textAreaAnimating, showArrow = true;
    ValueAnimator.AnimatorUpdateListener expandListener, textListener, arrowListener;
    ValueAnimator animator;
    private ViewGroup.MarginLayoutParams textAreaMargin;
    private StateChangeListener stateChangeListener;
    VideoNode model;
    private EditText editText;
    public EditTextWatcher watcher;
    private RatioImageView bgImage;
    private static final int toAngle = -180;

    public VideoViewHolder(View itemView, EditTextWatcher watcher) {
        super(itemView);
        this.watcher = watcher;
        add = (AppCompatImageView) itemView.findViewById(R.id.add_icon);
        addSubImage = (AppCompatImageView) itemView.findViewById(R.id.add_sub_text_icon);
        addArea = itemView.findViewById(R.id.add_container);
        threeArea = itemView.findViewById(R.id.c_three);
        addSubArea = itemView.findViewById(R.id.add_sub_text_container);
        twoArea = itemView.findViewById(R.id.c_two);
        oneArea = itemView.findViewById(R.id.c_one);
        subInput = itemView.findViewById(R.id.sub_input);
        editText = (EditText) itemView.findViewById(R.id.input);
        bgImage = (RatioImageView) itemView.findViewById(R.id.bg_image);
        textAreaMargin = (ViewGroup.MarginLayoutParams) subInput.getLayoutParams();
        editText.addTextChangedListener(watcher);
        editText.addTextChangedListener(this);
        bindLinks();
    }

    private void bindLinks() {
        addArea.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (menuAnimating) return;
                if (menuExpanded) shrinkMenu();
                else expandMenu();
            }
        });
        addSubArea.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (textAreaAnimating) return;
                if (textAreaExpanded) shrinkTextArea();
                else expandTextArea();
            }
        });
        threeArea.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (stateChangeListener != null) stateChangeListener.onRequestAddTextNode(model);
                resumeMenu();
            }
        });
        twoArea.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (stateChangeListener != null) stateChangeListener.onRequestAddImageNode(model);
                resumeMenu();
            }
        });
        oneArea.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (stateChangeListener != null) stateChangeListener.onRequestAddVideoNode(model);
                resumeMenu();
            }
        });
        bgImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (stateChangeListener != null) stateChangeListener.onVideoClicked(model);
            }
        });
        expandListener = new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float fac = (float) animation.getAnimatedValue();
                threeArea.setTranslationX(-fac * unitExpandDistance);
                twoArea.setTranslationX(-fac * unitExpandDistance * 2);
                oneArea.setTranslationX(-fac * unitExpandDistance * 3);
                oneArea.setAlpha(fac);
                twoArea.setAlpha(fac);
                threeArea.setAlpha(fac);
                add.setRotation(maxRotation * fac);
                if (menuExpanded && fac == 1F) menuAnimating = false;
                if (!menuExpanded && fac == 0F) menuAnimating = false;
            }
        };
        textListener = new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                float fac = (float) valueAnimator.getAnimatedValue();
                addSubImage.setRotation(toAngle * fac);
                textAreaMargin.topMargin = (int) (textTopDistance * fac);
                addSubArea.requestLayout();
                if (textAreaExpanded && fac == 1F) textAreaAnimating = false;
                if (!textAreaExpanded && fac == 0F) textAreaAnimating = false;
            }
        };
        arrowListener = new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float fac = (float) animation.getAnimatedValue();
                addSubArea.setAlpha(fac);
                if (fac == 0F) addSubArea.setVisibility(View.GONE);
                else addSubArea.setVisibility(View.VISIBLE);
            }
        };
    }

    public void resumeMenu() {
        if (menuExpanded) shrinkMenu();
    }

    public void expandMenu() {
        menuExpanded = true;
        animator = ValueAnimator.ofFloat(0F, 1F).setDuration(300);
        animator.setInterpolator(new OvershootInterpolator());
        animator.addUpdateListener(expandListener);
        animator.start();
        menuAnimating = true;
    }

    public void shrinkMenu() {
        menuExpanded = false;
        animator = ValueAnimator.ofFloat(1F, 0F).setDuration(300);
        animator.addUpdateListener(expandListener);
        animator.start();
        menuAnimating = true;
    }

    public void expandTextArea() {
        textAreaExpanded = true;
        model.setExpanded(true);
        animator = ValueAnimator.ofFloat(0F, 1F).setDuration(500);
        animator.setInterpolator(new AccelerateDecelerateInterpolator());
        animator.addUpdateListener(textListener);
        animator.start();
        editText.requestFocus();
        textAreaAnimating = true;
    }

    public void shrinkTextArea() {
        textAreaExpanded = false;
        model.setExpanded(false);
        animator = ValueAnimator.ofFloat(1F, 0F).setDuration(500);
        animator.setInterpolator(new AccelerateDecelerateInterpolator());
        animator.addUpdateListener(textListener);
        animator.start();
        textAreaAnimating = true;
    }

    public void showArrow() {
        if (showArrow) return;
        showArrow = true;
        addSubArea.setVisibility(View.VISIBLE);
//        animator = ValueAnimator.ofFloat(0F, 1F).setDuration(500);
//        animator.setInterpolator(new AccelerateDecelerateInterpolator());
//        animator.addUpdateListener(arrowListener);
//        animator.start();
    }

    public void hideArrow() {
        if (!showArrow) return;
        showArrow = false;
        addSubArea.setVisibility(View.GONE);
//        animator = ValueAnimator.ofFloat(1F, 0F).setDuration(500);
//        animator.setInterpolator(new AccelerateDecelerateInterpolator());
//        animator.addUpdateListener(arrowListener);
//        animator.start();
    }

    public void setStateChangeListener(StateChangeListener listener) {
        this.stateChangeListener = listener;
    }

    public void bindValue(VideoNode model) {
        this.model = model;
        editText.setText(model.getContent());
        setExpanded(model.isExpanded());
        textAreaExpanded = model.isExpanded();
    }

    private void setExpanded(boolean expanded) {
        textAreaMargin.topMargin = expanded ? (int) (textTopDistance) : 0;
        addSubArea.requestLayout();
        addSubImage.setRotation(expanded ? toAngle : 0);
    }


    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {
        if (TextUtils.isEmpty(s.toString())) showArrow();
        else hideArrow();
    }
}
