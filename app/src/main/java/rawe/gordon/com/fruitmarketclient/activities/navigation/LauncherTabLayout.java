package rawe.gordon.com.fruitmarketclient.activities.navigation;

import android.content.Context;
import android.graphics.Color;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import rawe.gordon.com.business.utils.DimenUtil;

/**
 * Created by gordon on 16/7/31.
 */
public class LauncherTabLayout extends LinearLayout {
    public static final String TAG = LauncherTabLayout.class.getCanonicalName();
    private Tab[] tabsData;
    private ViewPager viewPager;
    private LayoutInflater inflater = LayoutInflater.from(getContext());
    private int lastIndex = 0;

    public LauncherTabLayout(Context context) {
        super(context);
        init();
    }

    public LauncherTabLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public LauncherTabLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        setOrientation(VERTICAL);
    }

    public LauncherTabLayout setupIcons(Tab[] tabsData) {
        this.tabsData = tabsData;
        construct();
        return this;
    }

    private void construct() {
        /***/
        LinearLayout strip = new LinearLayout(getContext());
        strip.setBackgroundColor(Color.RED);
        ViewGroup.LayoutParams lp = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 1);
        addView(strip, lp);
        LinearLayout container = new LinearLayout(getContext());
        container.setBackgroundColor(Color.BLACK);
        container.setOrientation(HORIZONTAL);
        ViewGroup.LayoutParams lp_container = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        addView(container, lp_container);
        if (tabsData != null && tabsData.length > 0) {
            int i;
            for (i = 0; i < tabsData.length; i++) {
                Tab node = tabsData[i];
                LinearLayout subContainer = new LinearLayout(getContext());
                subContainer.setOrientation(VERTICAL);
                subContainer.setGravity(Gravity.CENTER);
                subContainer.setBackgroundColor(Color.GREEN);
                LayoutParams lp_seg = new LayoutParams(0, ViewGroup.LayoutParams.MATCH_PARENT);
                lp_seg.weight = 1;
                container.addView(subContainer, lp_seg);
                ImageView imageView = new ImageView(getContext());
                imageView.setImageResource(node.resId);
                ViewGroup.LayoutParams lp_image = new ViewGroup.LayoutParams((int) (DimenUtil.dip2pix(24)), (int) (DimenUtil.dip2pix(24)));
                subContainer.addView(imageView, lp_image);
                TextView textView = new TextView(getContext());
                textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 10);
                textView.setText(node.text);
                ViewGroup.LayoutParams lp_text = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                subContainer.addView(textView, lp_text);
                viewHolders.add(new ViewHolder(imageView, textView));
                final int finalI = i;
                subContainer.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        viewPager.setCurrentItem(finalI);
                        Log.d("positionxxxx",String.valueOf(finalI));
                    }
                });
            }
        }
    }

    public void hookUpWithViewPager(final ViewPager viewPager) {
        this.viewPager = viewPager;
        this.viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                Log.d("axxx", "position -> " + position + "offset -> " + positionOffset + " pixels ->" + positionOffsetPixels);

            }

            @Override
            public void onPageSelected(int position) {
                switchPage(position);
                lastIndex = position;
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    private void handleProgress(int last, int now, float progress) {

    }

    private void switchPage(int toPage) {
        viewHolders.get(lastIndex).imageView.setBackgroundColor(Color.TRANSPARENT);
        viewHolders.get(lastIndex).textView.setTextColor(Color.GRAY);
        viewHolders.get(toPage).imageView.setBackgroundColor(Color.RED);
        viewHolders.get(toPage).textView.setTextColor(Color.RED);
    }

    private List<ViewHolder> viewHolders = new ArrayList<>();

    public static class ViewHolder {

        public ViewHolder(ImageView imageView, TextView textView) {
            this.imageView = imageView;
            this.textView = textView;
        }

        public ImageView imageView;
        public TextView textView;
    }

    public static class Tab {

        public Tab(int resId, String text) {
            this.resId = resId;
            this.text = text;
        }

        public int resId;
        public String text;
    }
}
