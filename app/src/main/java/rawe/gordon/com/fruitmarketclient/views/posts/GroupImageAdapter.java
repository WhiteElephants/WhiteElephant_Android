package rawe.gordon.com.fruitmarketclient.views.posts;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.List;

import rawe.gordon.com.business.application.SharedParameter;
import rawe.gordon.com.business.imageloader.DisplayOptions;
import rawe.gordon.com.business.utils.DimenUtil;
import rawe.gordon.com.fruitmarketclient.R;
import rawe.gordon.com.fruitmarketclient.views.posts.models.ImageNode;
import rawe.gordon.com.fruitmarketclient.views.posts.viewholders.GroupImageItemHolder;

/**
 * Created by gordon on 16/8/23.
 */
public class GroupImageAdapter extends RecyclerView.Adapter<GroupImageItemHolder> {

    public static final int GROUP_ITEM_WIDTH = (int) ((SharedParameter.getInstance().getScreenWidth() - DimenUtil.dip2pix(60)) / 3);

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
        ImageNode node = nodes.get(position);
        final int[] size = DimenUtil.decodeImageSize(node.getStoragePath());
        ViewGroup.LayoutParams layoutParams = holder.imageContainer.getLayoutParams();
        layoutParams.height = GROUP_ITEM_WIDTH * size[1] / size[0];
        holder.imageContainer.setLayoutParams(layoutParams);
        ImageLoader.getInstance().displayImage(node.getStoragePath(), holder.imageView, DisplayOptions.getCacheNoneFadeOptions());
    }
}
