package rawe.gordon.com.fruitmarketclient.views.navigation;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

import rawe.gordon.com.fruitmarketclient.R;
import rawe.gordon.com.fruitmarketclient.views.ViewFactory;
import rawe.gordon.com.fruitmarketclient.views.homepage.ViewConfigurator;

/**
 * Created by gordon on 5/5/16.
 */
public class ExpandableItemView extends LinearLayout {

    private Context context;
    private View rootView;
    private ViewGroup container;
    private TextView titleView;
    private ValueAnimator shrink;
    public boolean state = true;
    private int container_height = -1;
    private boolean clickable = true;

    public ExpandableItemView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        rootView = LayoutInflater.from(context).inflate(R.layout.layout_expandable_item_view, this);
        container = (ViewGroup) rootView.findViewById(R.id.container);
    }

    public void initialize(String title, List<String> itemTexts, final ViewConfigurator.LeftMenuListener leftMenuListener) {
        (titleView = (TextView) rootView.findViewById(R.id.title)).setText(title);
        for (final String item : itemTexts) {
            TextView textView = (TextView) ViewFactory.createView(LayoutInflater.from(context), R.layout.layout_expabdable_item_view_textview, this, false);
            textView.setText(item);
            textView.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    leftMenuListener.onTextClicked(item);
                }
            });
            container.addView(textView);
        }
        rootView.findViewById(R.id.title_area).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                toggleViews();
                NavigationItemViewManager.getInstance().performStatusChange();
            }
        });
        container.post(new Runnable() {
            @Override
            public void run() {
                container_height = container.getHeight();
                toggleViews();
            }
        });
    }

    public void toggleViews() {
        if (!clickable) return;
        if (state) {
            hide();
        } else {
            show();
        }
        state = !state;
    }

    private void hide() {
        if (shrink != null) shrink.cancel();
        rootView.setBackgroundColor(Color.parseColor("#558855"));
        shrink = ValueAnimator.ofInt(container_height, 0);
        shrink.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                container.getLayoutParams().height = (int) valueAnimator.getAnimatedValue();
                container.requestLayout();
                container.setAlpha(1F - valueAnimator.getAnimatedFraction());
                if ((int) valueAnimator.getAnimatedValue() == 0) clickable = true;
            }
        });
        shrink.setDuration(400);
        shrink.start();
        clickable = false;
    }

    private void show() {
        if (shrink != null) shrink.cancel();
        rootView.setBackgroundColor(Color.parseColor("#449944"));
        shrink = ValueAnimator.ofInt(0, container_height);
        shrink.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                container.getLayoutParams().height = (int) valueAnimator.getAnimatedValue();
                container.requestLayout();
                container.setAlpha(valueAnimator.getAnimatedFraction());
                if ((int) valueAnimator.getAnimatedValue() == container_height) clickable = true;
            }
        });
        shrink.setDuration(400);
        shrink.start();
        clickable = false;
    }
}
