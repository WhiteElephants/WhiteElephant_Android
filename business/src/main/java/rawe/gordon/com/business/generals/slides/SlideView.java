package rawe.gordon.com.business.generals.slides;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.viewpagerindicator.LinePageIndicator;

import java.util.ArrayList;
import java.util.List;

import rawe.gordon.com.business.R;
import rawe.gordon.com.business.network.responses.pojo.SlideModel;

/**
 * Created by gordon on 5/6/16.
 */
public class SlideView extends LinearLayout {
    private Context context;
    private ViewGroup rootView;
    private ViewPager viewPager;
    private LinePageIndicator linePageIndicator;
    private List<View> views = new ArrayList<>();
    private int length = 0;

    public SlideView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        rootView = (ViewGroup) LayoutInflater.from(context).inflate(R.layout.layout_slides_view, this);
        viewPager = (ViewPager) rootView.findViewById(R.id.viewpager);
        linePageIndicator = (LinePageIndicator) rootView.findViewById(R.id.indicator);
    }

    public void initialize(List<SlideModel> sourceData) {
        if (sourceData == null || sourceData.size() == 0) setVisibility(GONE);
        else setVisibility(VISIBLE);
        for (SlideModel slide : sourceData) {
            ImageView draweeView;
            views.add(draweeView = (ImageView) LayoutInflater.from(context).inflate(R.layout.layout_slides_sub_view, rootView, false));
            if (slide.getImageUrl() != null)
                Glide.with(getContext()).load(slide.getImageUrl()).diskCacheStrategy(DiskCacheStrategy.ALL).into(draweeView);
        }
        length = sourceData.size();
        viewPager.setAdapter(new SlideViewAdapter());
        linePageIndicator.setViewPager(viewPager);

    }

    public class SlideViewAdapter extends PagerAdapter {

        @Override
        public int getCount() {
            return length;
        }

        // 来判断显示的是否是同一张图片，这里我们将两个参数相比较返回即可
        @Override
        public boolean isViewFromObject(View arg0, Object arg1) {
            return arg0 == arg1;
        }

        // PagerAdapter只缓存三张要显示的图片，如果滑动的图片超出了缓存的范围，就会调用这个方法，将图片销毁
        @Override
        public void destroyItem(ViewGroup view, int position, Object object) {
            view.removeView(views.get(position));
        }

        // 当要显示的图片可以进行缓存的时候，会调用这个方法进行显示图片的初始化，我们将要显示的ImageView加入到ViewGroup中，然后作为返回值返回即可
        @Override
        public Object instantiateItem(ViewGroup view, int position) {
            view.addView(views.get(position));
            return views.get(position);
        }
    }
}
