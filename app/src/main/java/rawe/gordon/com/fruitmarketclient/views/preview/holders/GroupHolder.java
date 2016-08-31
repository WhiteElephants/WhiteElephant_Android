package rawe.gordon.com.fruitmarketclient.views.preview.holders;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import android.widget.TextView;

import rawe.gordon.com.fruitmarketclient.R;
import rawe.gordon.com.fruitmarketclient.views.posts.GroupImageAdapter;
import rawe.gordon.com.fruitmarketclient.views.posts.PreviewGroupImageAdapter;
import rawe.gordon.com.fruitmarketclient.views.posts.models.GroupNode;

/**
 * Created by gordon on 16/8/28.
 */
public class GroupHolder extends RecyclerView.ViewHolder {
    public RecyclerView recyclerView;
    private Context context;
    public TextView imageText;


    public GroupHolder(View itemView, Context context) {
        super(itemView);
        this.context = context;
        recyclerView = (RecyclerView) itemView.findViewById(R.id.recycler_view);
        imageText = (TextView) itemView.findViewById(R.id.image_text);
    }

    public void bindValue(GroupNode model) {
        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(PreviewGroupImageAdapter.GROUP_ITEM_COLUMN, StaggeredGridLayoutManager.VERTICAL));
        recyclerView.setAdapter(new PreviewGroupImageAdapter(context, model.getImageNodes()));
        imageText.setText(model.getContent());
    }
}
