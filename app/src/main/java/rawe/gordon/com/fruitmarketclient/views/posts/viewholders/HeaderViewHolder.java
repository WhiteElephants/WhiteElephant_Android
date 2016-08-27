package rawe.gordon.com.fruitmarketclient.views.posts.viewholders;

import android.animation.ValueAnimator;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.animation.OvershootInterpolator;
import android.widget.EditText;

import rawe.gordon.com.business.configs.Config;
import rawe.gordon.com.business.utils.DimenUtil;
import rawe.gordon.com.fruitmarketclient.R;
import rawe.gordon.com.fruitmarketclient.views.posts.StateChangeListener;
import rawe.gordon.com.fruitmarketclient.views.posts.models.HeaderNode;
import rawe.gordon.com.fruitmarketclient.views.posts.watch.EditTextWatcher;

/**
 * Created by gordon on 16/8/23.
 */
public class HeaderViewHolder extends RecyclerView.ViewHolder {

    private AppCompatImageView add;
    private View addArea, threeArea, twoArea, oneArea;
    private boolean menuExpanded = false, menuAnimating;
    ValueAnimator.AnimatorUpdateListener expandListener;
    ValueAnimator animator;
    private EditText editText;
    private HeaderNode model;
    private StateChangeListener stateChangeListener;
    public EditTextWatcher watcher;
    private float unitExpandDistance = DimenUtil.dip2pix(48);
    private int maxRotation = 45;


    public HeaderViewHolder(View itemView, EditTextWatcher watcher) {
        super(itemView);
        this.watcher = watcher;
        add = (AppCompatImageView) itemView.findViewById(R.id.add_icon);
        addArea = itemView.findViewById(R.id.add_container);
        threeArea = itemView.findViewById(R.id.c_three);
        twoArea = itemView.findViewById(R.id.c_two);
        oneArea = itemView.findViewById(R.id.c_one);
        editText = (EditText) itemView.findViewById(R.id.input);
        editText.addTextChangedListener(watcher);
        bindLinks();
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

    public void bindValue(HeaderNode model) {
        this.model = model;
    }

    public void setStateChangeListener(StateChangeListener listener) {
        this.stateChangeListener = listener;
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
        expandListener = new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float fac = (float) animation.getAnimatedValue();
                threeArea.setTranslationX(-fac * unitExpandDistance);
                twoArea.setTranslationX(-fac * unitExpandDistance * 2);
                if (Config.VIDEO) {
                    oneArea.setTranslationX(-fac * unitExpandDistance * 3);
                    oneArea.setAlpha(fac);
                }
                twoArea.setAlpha(fac);
                threeArea.setAlpha(fac);
                add.setRotation(maxRotation * fac);
                if (menuExpanded && fac == 1F) menuAnimating = false;
                if (!menuExpanded && fac == 0F) menuAnimating = false;
            }
        };
    }
}
