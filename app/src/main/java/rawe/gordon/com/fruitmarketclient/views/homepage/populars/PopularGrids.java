package rawe.gordon.com.fruitmarketclient.views.homepage.populars;

import android.app.Activity;
import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import rawe.gordon.com.business.generals.grids.AbstractRecyclerGrid;
import rawe.gordon.com.business.network.responses.pojo.CommodityModel;
import rawe.gordon.com.business.utils.ViewSizeRegulator;
import rawe.gordon.com.fruitmarketclient.R;
import rawe.gordon.com.fruitmarketclient.activities.CommodityDetailActivity;

/**
 * Created by gordon on 5/13/16.
 */
public class PopularGrids extends AbstractRecyclerGrid<CommodityModel> {
    private Context context;

    public PopularGrids(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
    }

    @Override
    protected int getItemLayout() {
        return R.layout.layout_recycler_grid_view_item;
    }

    @Override
    protected int getColumnCount() {
        return 2;
    }

    @Override
    protected void bindViews(View view, final CommodityModel model, final Activity activity) {
        ImageView draweeView = (ImageView) view.findViewById(R.id.image);
        Glide.with(context).load(model.getThumbnail()).diskCacheStrategy(DiskCacheStrategy.ALL).into(draweeView);
        try {
            ViewSizeRegulator.regulateViewRatio(view, 4 / 3F);
        } catch (Exception e) {
            e.printStackTrace();
        }
        draweeView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CommodityDetailActivity.gotoCommodityDetailActivity(activity, model.getId());
            }
        });
    }
}
