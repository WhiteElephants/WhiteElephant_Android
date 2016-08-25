package rawe.gordon.com.fruitmarketclient.activities.launcher;

import android.content.Context;
import android.graphics.Color;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import rawe.gordon.com.business.utils.DimenUtil;
import rawe.gordon.com.fruitmarketclient.R;

/**
 * Created by gordon on 16/7/31.
 */
public class LauncherTabLayout extends LinearLayout {
    public static final String TAG = LauncherTabLayout.class.getCanonicalName();
    private Tab[] tabsData;
    private ViewPager viewPager;
    private int lastIndex = 0;
    private int initialIndex = 0;
    private static final int defaultColor = 0x99888888, selectedColor = 0x99FFFFFF, stripColor = 0x99EEEEEE, backgroudColor = 0xFF333333;

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
        setBackgroundColor(backgroudColor);
    }

    public LauncherTabLayout configure(Tab[] tabsData, int initialIndex) {
        this.tabsData = tabsData;
        this.initialIndex = initialIndex;
        construct();
        return this;
    }

    public LauncherTabLayout setListener(SwitchListener switchListener) {
        this.listener = switchListener;
        return this;
    }

    private void construct() {
        /***/
        LinearLayout strip = new LinearLayout(getContext());
        strip.setBackgroundColor(stripColor);
        ViewGroup.LayoutParams lp = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 1);
        addView(strip, lp);
        LinearLayout container = new LinearLayout(getContext());
        container.setOrientation(HORIZONTAL);
        ViewGroup.LayoutParams lp_container = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        addView(container, lp_container);
        int pos = 0;
        if (tabsData != null && tabsData.length > 0) {
            for (int counter = 0; counter < tabsData.length; counter++) {
                Tab node = tabsData[counter];
                ViewHolder holder;
                if (!TextUtils.isEmpty(node.text)) {
                    final int finalPos = pos;
                    viewHolders.add(holder = TabInjector.injectLTNode(node.text, node.resId, container, node, new OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            viewPager.setCurrentItem(finalPos);
                        }
                    }));
                    if (initialIndex == counter) holder.renderOnState();
                    pos++;
                } else {
                    TabInjector.injectLNode(node.resId, container, node, new OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if(listener!=null) listener.onCenter();
                        }
                    });
                }
            }
        }
    }

    public static class TabInjector {
        public static LTViewHolder injectLTNode(String title, int logoResId, ViewGroup parent, Tab info, OnClickListener listener) {
            LinearLayout subContainer = new LinearLayout(parent.getContext());
            subContainer.setOrientation(VERTICAL);
            subContainer.setGravity(Gravity.CENTER);
            LayoutParams lp_seg = new LayoutParams(0, ViewGroup.LayoutParams.MATCH_PARENT);
            lp_seg.weight = 1;
            parent.addView(subContainer, lp_seg);
            ImageView imageView = new ImageView(parent.getContext());
            imageView.setImageResource(logoResId);
            ViewGroup.LayoutParams lp_image = new ViewGroup.LayoutParams((int) (DimenUtil.dip2pix(24)), (int) (DimenUtil.dip2pix(24)));
            subContainer.addView(imageView, lp_image);
            TextView textView = new TextView(parent.getContext());
            textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 10);
            textView.setText(title);
            textView.setTextColor(defaultColor);
            ViewGroup.LayoutParams lp_text = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            subContainer.addView(textView, lp_text);
            subContainer.setOnClickListener(listener);
            return new LTViewHolder(imageView, textView, info);
        }

        public static LViewHolder injectLNode(int logoResId, ViewGroup parent, Tab info, OnClickListener listener) {
            LinearLayout subContainer = new LinearLayout(parent.getContext());
            subContainer.setOrientation(VERTICAL);
            subContainer.setGravity(Gravity.CENTER);
            LayoutParams lp_seg = new LayoutParams(0, ViewGroup.LayoutParams.MATCH_PARENT);
            lp_seg.weight = 1;
            parent.addView(subContainer, lp_seg);
            RelativeLayout outRing = new RelativeLayout(parent.getContext());
            ViewGroup.LayoutParams lp_ring = new ViewGroup.LayoutParams((int) (DimenUtil.dip2pix(45)), (int) (DimenUtil.dip2pix(45)));
            outRing.setBackgroundResource(R.drawable.round_bg);
            subContainer.addView(outRing, lp_ring);
            ImageView imageView = new ImageView(parent.getContext());
            imageView.setImageResource(logoResId);
            RelativeLayout.LayoutParams lp_image = new RelativeLayout.LayoutParams((int) (DimenUtil.dip2pix(24)), (int) (DimenUtil.dip2pix(24)));
            lp_image.addRule(RelativeLayout.CENTER_IN_PARENT);
            outRing.addView(imageView, lp_image);
            subContainer.setOnClickListener(listener);
            return new LViewHolder(imageView, info);
        }
    }

    private static class LTViewHolder implements ViewHolder {
        public ImageView imageView;
        public TextView textView;
        public Tab info;

        public LTViewHolder(ImageView imageView, TextView textView, Tab info) {
            this.imageView = imageView;
            this.textView = textView;
            this.info = info;
        }

        @Override
        public void setColor(int color) {

        }

        @Override
        public void renderOnState() {
            textView.setTextColor(selectedColor);
            imageView.setImageResource(info.selectedRedId);
        }

        @Override
        public void renderOffState() {
            textView.setTextColor(defaultColor);
            imageView.setImageResource(info.resId);
        }
    }

    private static class LViewHolder implements ViewHolder {
        public ImageView imageView;
        public Tab info;

        public LViewHolder(ImageView imageView, Tab info) {
            this.imageView = imageView;
            this.info = info;
        }

        @Override
        public void setColor(int color) {

        }

        @Override
        public void renderOnState() {

        }

        @Override
        public void renderOffState() {

        }
    }

    public void hookUpWithViewPager(final ViewPager viewPager) {
        this.viewPager = viewPager;
        this.viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

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

    private void switchPage(int toPage) {
        if (listener != null) listener.onTabs(toPage);
        viewHolders.get(lastIndex).renderOffState();
        viewHolders.get(toPage).renderOnState();
    }

    private List<ViewHolder> viewHolders = new ArrayList<>();

    public interface ViewHolder {
        void setColor(int color);

        void renderOnState();

        void renderOffState();
    }

    public static class Tab {

        public Tab(int resId, String text, int selectedRedId) {
            this.resId = resId;
            this.text = text;
            this.selectedRedId = selectedRedId;
        }

        public int resId;
        public String text;
        public int selectedRedId;
    }

    private SwitchListener listener;

    public interface SwitchListener {
        void onCenter();

        void onTabs(int position);
    }
}
