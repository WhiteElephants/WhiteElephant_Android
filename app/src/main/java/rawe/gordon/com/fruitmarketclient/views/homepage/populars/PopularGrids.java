package rawe.gordon.com.fruitmarketclient.views.homepage.populars;

import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.util.AttributeSet;
import android.view.View;

import com.facebook.drawee.view.SimpleDraweeView;

import rawe.gordon.com.business.utils.CacheBean;
import rawe.gordon.com.business.utils.ViewSizeRegulator;
import rawe.gordon.com.fruitmarketclient.R;
import rawe.gordon.com.fruitmarketclient.activities.CommodityDetailActivity;
import rawe.gordon.com.business.network.responses.pojo.CommodityModel;
import rawe.gordon.com.fruitmarketclient.views.generals.grids.AbstractRecyclerGrid;

/**
 * Created by gordon on 5/13/16.
 */
public class PopularGrids extends AbstractRecyclerGrid<CommodityModel> {
    public PopularGrids(Context context, AttributeSet attrs) {
        super(context, attrs);
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
        SimpleDraweeView draweeView = (SimpleDraweeView) view.findViewById(R.id.image);
        Uri uri = Uri.parse(model.getThumbnail());
        draweeView.setImageURI(uri);
        try {
            ViewSizeRegulator.regulateViewRatio(view, 4 / 3F);
        } catch (Exception e) {
            e.printStackTrace();
        }
        draweeView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CommodityDetailActivity.gotoCommodityDetailActivity(activity,model.getId());
            }
        });
    }
}
