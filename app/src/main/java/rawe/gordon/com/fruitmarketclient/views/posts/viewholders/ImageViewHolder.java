package rawe.gordon.com.fruitmarketclient.views.posts.viewholders;

import android.animation.ValueAnimator;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.OvershootInterpolator;

import rawe.gordon.com.business.utils.DimenUtil;
import rawe.gordon.com.fruitmarketclient.R;
import rawe.gordon.com.fruitmarketclient.views.posts.StateChangeListener;
import rawe.gordon.com.fruitmarketclient.views.posts.models.ImageNode;
import rawe.gordon.com.fruitmarketclient.views.posts.watch.EditTextWatcher;

/**
 * Created by gordon on 16/8/23.
 */
public class ImageViewHolder extends RecyclerView.ViewHolder {

    private AppCompatImageView add, addSubImage;
    private View addArea, imageArea, textArea, addSubArea, subInput;
    private float unitExpandDistance = DimenUtil.dip2pix(48), textTopDistance = DimenUtil.dip2pix(150);
    private int maxRotation = 45;
    private boolean menuExpanded = false, menuAnimating, textAreaExpanded, textAreaAnimating;
    ValueAnimator.AnimatorUpdateListener expandListener, textListener;
    ValueAnimator animator;
    private ViewGroup.MarginLayoutParams textAreaMargin;
    private StateChangeListener stateChangeListener;
    ImageNode model;
    public EditTextWatcher watcher;

    public ImageViewHolder(View itemView,EditTextWatcher watcher) {
        super(itemView);
        this.watcher = watcher;
        add = (AppCompatImageView) itemView.findViewById(R.id.add_icon);
        addSubImage = (AppCompatImageView) itemView.findViewById(R.id.add_sub_text_icon);
        addArea = itemView.findViewById(R.id.add_container);
        imageArea = itemView.findViewById(R.id.add_image_container);
        addSubArea = itemView.findViewById(R.id.add_sub_text_container);
        textArea = itemView.findViewById(R.id.add_text_container);
        subInput = itemView.findViewById(R.id.sub_input);
        textAreaMargin = (ViewGroup.MarginLayoutParams) subInput.getLayoutParams();
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
        imageArea.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (stateChangeListener != null) stateChangeListener.onRequestAddImageNode(model);
            }
        });
        textArea.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (stateChangeListener != null) stateChangeListener.onRequestAddTextNode(model);
            }
        });
        expandListener = new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float fac = (float) animation.getAnimatedValue();
                imageArea.setTranslationX(-fac * unitExpandDistance);
                textArea.setTranslationX(-fac * unitExpandDistance * 2);
                add.setRotation(maxRotation * fac);
                if (menuExpanded && fac == 1F) menuAnimating = false;
                if (!menuExpanded && fac == 0F) menuAnimating = false;
            }
        };
        textListener = new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                float fac = (float) valueAnimator.getAnimatedValue();
                addSubImage.setRotation(maxRotation * fac);
                textAreaMargin.topMargin = (int) (textTopDistance * fac);
                textArea.requestLayout();
                if (textAreaExpanded && fac == 1F) textAreaAnimating = false;
                if (!textAreaExpanded && fac == 0F) textAreaAnimating = false;
            }
        };
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
        animator = ValueAnimator.ofFloat(0F, 1F).setDuration(500);
        animator.setInterpolator(new OvershootInterpolator());
        animator.addUpdateListener(textListener);
        animator.start();
        textAreaAnimating = true;
    }

    public void shrinkTextArea() {
        textAreaExpanded = false;
        animator = ValueAnimator.ofFloat(1F, 0F).setDuration(500);
        animator.setInterpolator(new AccelerateDecelerateInterpolator());
        animator.addUpdateListener(textListener);
        animator.start();
        textAreaAnimating = true;
    }

    public void setStateChangeListener(StateChangeListener listener) {
        this.stateChangeListener = listener;
    }

    public void bindValue(ImageNode model) {
        this.model = model;
    }
}
