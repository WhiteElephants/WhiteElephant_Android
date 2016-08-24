package rawe.gordon.com.fruitmarketclient.views.posts.viewholders;

import android.animation.ValueAnimator;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.animation.OvershootInterpolator;

import rawe.gordon.com.fruitmarketclient.R;

/**
 * Created by gordon on 16/8/23.
 */
public class ImageViewHolder extends RecyclerView.ViewHolder {

    private AppCompatImageView add;
    private View addArea, imageArea, textArea;
    private int unitExpandDistance = 150;
    private int maxRotation = 45;
    private boolean expanding, animating;
    ValueAnimator.AnimatorUpdateListener listener;
    ValueAnimator animator;

    public ImageViewHolder(View itemView) {
        super(itemView);
        add = (AppCompatImageView) itemView.findViewById(R.id.add_icon);
        addArea = itemView.findViewById(R.id.add_container);
        imageArea = itemView.findViewById(R.id.add_image_container);
        textArea = itemView.findViewById(R.id.add_text_container);
        bindLinks();
    }

    private void bindLinks() {
        addArea.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(animating) return;
                if(expanding) shrink();else expand();
            }
        });
        listener = new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float fac = (float) animation.getAnimatedValue();
                imageArea.setTranslationX(-fac * unitExpandDistance);
                textArea.setTranslationX(-fac * unitExpandDistance * 2);
                add.setRotation(maxRotation * fac);
                if (expanding && fac == 1F) animating = false;
                if(!expanding && fac == 0F) animating = false;
            }
        };
    }

    public void expand() {
        expanding = true;
        animator = ValueAnimator.ofFloat(0F, 1F).setDuration(300);
        animator.setInterpolator(new OvershootInterpolator());
        animator.addUpdateListener(listener);
        animator.start();
        animating = true;
    }

    public void shrink() {
        expanding = false;
        animator = ValueAnimator.ofFloat(1F, 0F).setDuration(300);
        animator.addUpdateListener(listener);
        animator.start();
        animating = true;
    }
}
