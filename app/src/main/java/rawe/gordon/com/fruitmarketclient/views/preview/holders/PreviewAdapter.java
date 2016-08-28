package rawe.gordon.com.fruitmarketclient.views.preview.holders;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import java.util.List;

import rawe.gordon.com.fruitmarketclient.R;
import rawe.gordon.com.fruitmarketclient.views.posts.models.Node;
import rawe.gordon.com.fruitmarketclient.views.posts.models.NodeType;

/**
 * Created by gordon on 16/8/28.
 */
public class PreviewAdapter extends RecyclerView.Adapter {

    public List<Node> nodes;
    private Context context;
    private LayoutInflater inflater;

    public PreviewAdapter(List<Node> nodes, Context context) {
        this.nodes = nodes;
        this.context = context;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getItemViewType(int position) {
        return nodes.get(position).getType();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType) {
            case NodeType.HEADER:
                return new HeaderHolder(inflater.inflate(R.layout.layout_preview_header_item, parent, false));
            case NodeType.TEXT:
                return new HeaderHolder(inflater.inflate(R.layout.layout_preview_text_item, parent, false));
            case NodeType.IMAGE:
                return new HeaderHolder(inflater.inflate(R.layout.layout_preview_image_item, parent, false));
            case NodeType.VIDEO:
                return new HeaderHolder(inflater.inflate(R.layout.layout_preview_video_item, parent, false));
            case NodeType.FOOTER:
                return new HeaderHolder(inflater.inflate(R.layout.layout_preview_footer_item, parent, false));
            default:
                return new HeaderHolder(inflater.inflate(R.layout.layout_preview_text_item, parent, false));
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
