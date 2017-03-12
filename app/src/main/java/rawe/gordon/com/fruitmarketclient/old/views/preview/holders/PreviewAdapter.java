package rawe.gordon.com.fruitmarketclient.old.views.preview.holders;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import java.util.List;

import rawe.gordon.com.fruitmarketclient.R;
import rawe.gordon.com.fruitmarketclient.compose.models.GroupNode;
import rawe.gordon.com.fruitmarketclient.compose.models.HeaderNode;
import rawe.gordon.com.fruitmarketclient.compose.models.Node;
import rawe.gordon.com.fruitmarketclient.compose.models.NodeType;
import rawe.gordon.com.fruitmarketclient.compose.models.TextNode;

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
                return new TextHolder(inflater.inflate(R.layout.layout_preview_text_item, parent, false));
            case NodeType.IMAGE:
                return new ImageHolder(inflater.inflate(R.layout.layout_preview_image_item, parent, false));
            case NodeType.VIDEO:
                return new VideoHolder(inflater.inflate(R.layout.layout_preview_video_item, parent, false));
            case NodeType.GROUP:
                return new GroupHolder(inflater.inflate(R.layout.layout_preview_group_item, parent, false), context);
            case NodeType.FOOTER:
                return new FooterHolder(inflater.inflate(R.layout.layout_preview_footer_item, parent, false));
            default:
                return new HeaderHolder(inflater.inflate(R.layout.layout_preview_text_item, parent, false));
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        Node node = nodes.get(position);
        switch (node.getType()) {
            case NodeType.HEADER:
                HeaderHolder headerHolder = (HeaderHolder) holder;
                HeaderNode headerNode = (HeaderNode) node;
                headerHolder.bindValue(headerNode);
                break;
            case NodeType.TEXT:
                TextHolder textHolder = (TextHolder) holder;
                TextNode textNode = (TextNode) node;
                textHolder.bindValue(textNode);
                break;
            case NodeType.GROUP:
                GroupHolder groupHolder = (GroupHolder) holder;
                GroupNode groupNode = (GroupNode) node;
                groupHolder.bindValue(groupNode);
                break;
        }
    }

    @Override
    public int getItemCount() {
        return nodes.size();
    }
}
