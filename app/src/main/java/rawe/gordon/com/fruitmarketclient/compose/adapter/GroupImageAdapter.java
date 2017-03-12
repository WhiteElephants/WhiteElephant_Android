package rawe.gordon.com.fruitmarketclient.compose.adapter;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.util.List;

import rawe.gordon.com.business.activities.SitoImageViewActivity;
import rawe.gordon.com.business.application.SharedParameter;
import rawe.gordon.com.business.utils.DimenUtil;
import rawe.gordon.com.business.utils.ToastUtil;
import rawe.gordon.com.fruitmarketclient.R;
import rawe.gordon.com.fruitmarketclient.compose.models.ImageNode;
import rawe.gordon.com.fruitmarketclient.compose.viewholders.GroupImageItemHolder;

/**
 * Created by gordon on 16/8/23.
 */
public class GroupImageAdapter extends RecyclerView.Adapter<GroupImageItemHolder> {

    public static int GROUP_ITEM_COLUMN = 3;
    public static int GROUP_ITEM_WIDTH = (int) ((SharedParameter.getInstance().getScreenWidth() - DimenUtil.dip2pix(40 + (GROUP_ITEM_COLUMN - 1) * 10)) / GROUP_ITEM_COLUMN);

    public static void recalc() {
        GROUP_ITEM_COLUMN = GROUP_ITEM_COLUMN == 3 ? 2 : GROUP_ITEM_COLUMN == 2 ? 1 : 3;
        GROUP_ITEM_WIDTH = (int) ((SharedParameter.getInstance().getScreenWidth() - DimenUtil.dip2pix(40 + (GROUP_ITEM_COLUMN - 1) * 10)) / GROUP_ITEM_COLUMN);
    }

    public static void resetColumn() {
        GROUP_ITEM_COLUMN = 3;
        GROUP_ITEM_WIDTH = (int) ((SharedParameter.getInstance().getScreenWidth() - DimenUtil.dip2pix(40 + (GROUP_ITEM_COLUMN - 1) * 10)) / GROUP_ITEM_COLUMN);
    }

    public List<ImageNode> nodes;
    private LayoutInflater inflater;
    private Context context;

    public GroupImageAdapter(Context context, List<ImageNode> src) {
        this.nodes = src;
        this.context = context;
        this.inflater = LayoutInflater.from(context);

    }

    @Override
    public int getItemCount() {
        return nodes.size();
    }

    @Override
    public GroupImageItemHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new GroupImageItemHolder(inflater.inflate(R.layout.layout_post_compose_item_group_item, parent, false));
    }

    @Override
    public void onBindViewHolder(final GroupImageItemHolder holder, int position) {
        final ImageNode node = nodes.get(position);
        final int[] size = DimenUtil.decodeImageSize(node.getStoragePath());
        ViewGroup.LayoutParams layoutParams = holder.imageContainer.getLayoutParams();
        layoutParams.height = GROUP_ITEM_WIDTH * size[1] / size[0];
        holder.imageContainer.setLayoutParams(layoutParams);
        Glide.with(context).load(node.getStoragePath()).diskCacheStrategy(DiskCacheStrategy.ALL).into(holder.imageView);
        holder.imageContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int[] coord = new int[2];
                holder.imageContainer.getLocationOnScreen(coord);
                SitoImageViewActivity.ImageModel model = new SitoImageViewActivity.ImageModel(node.getStoragePath(), coord[0], coord[1], holder.imageContainer.getWidth(), holder.imageContainer.getHeight());
                SitoImageViewActivity.goToSitoImageBrowsePage((Activity) context, model);
            }
        });
        holder.imageContainer.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                ToastUtil.say("long clicked");
                return true;
            }
        });
    }
}
