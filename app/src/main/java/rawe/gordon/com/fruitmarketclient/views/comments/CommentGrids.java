package rawe.gordon.com.fruitmarketclient.views.comments;

import android.app.Activity;
import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.TextView;

import rawe.gordon.com.fruitmarketclient.R;
import rawe.gordon.com.business.network.responses.pojo.SlideModel;
import rawe.gordon.com.business.generals.grids.AbstractRecyclerGrid;

/**
 * Created by gordon on 16/5/12.
 */
public class CommentGrids extends AbstractRecyclerGrid<SlideModel> {
    public CommentGrids(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected int getItemLayout() {
        return R.layout.layout_comment_item;
    }

    @Override
    protected int getColumnCount() {
        return 2;
    }

    @Override
    protected void bindViews(View view, SlideModel model, Activity activity) {
        ((TextView) view.findViewById(R.id.content)).setText(model.getImageUrl());
    }
}
