package rawe.gordon.com.fruitmarketclient.views.posts;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import java.util.List;

import rawe.gordon.com.fruitmarketclient.R;
import rawe.gordon.com.fruitmarketclient.views.posts.models.Node;
import rawe.gordon.com.fruitmarketclient.views.posts.models.NodeType;
import rawe.gordon.com.fruitmarketclient.views.posts.viewholders.TextViewHolder;

/**
 * Created by gordon on 16/8/23.
 */
public class PostAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<Node> nodes;
    private LayoutInflater inflater;
    private Context context;

    public PostAdapter(Context context, List<Node> src) {
        this.nodes = src;
        this.context = context;
        this.inflater = LayoutInflater.from(context);

    }

    @Override
    public int getItemViewType(int position) {
        return nodes.get(position).getType();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType) {
            case NodeType.HEADER:
                return new TextViewHolder(inflater.inflate(R.layout.layout_post_compose_item_header, parent, false));
            case NodeType.TEXT:
                return new TextViewHolder(inflater.inflate(R.layout.layout_post_compose_item_text, parent, false));
            case NodeType.IMAGE:
                return new TextViewHolder(inflater.inflate(R.layout.layout_post_compose_item_image, parent, false));
            case NodeType.VIDEO:
                return new TextViewHolder(inflater.inflate(R.layout.layout_post_compose_item_video, parent, false));
            case NodeType.FOOTER:
                return new TextViewHolder(inflater.inflate(R.layout.layout_post_compose_item_footer, parent, false));
            default:
                return new TextViewHolder(inflater.inflate(R.layout.layout_post_compose_item_image, parent, false));
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return nodes.size();
    }
}
