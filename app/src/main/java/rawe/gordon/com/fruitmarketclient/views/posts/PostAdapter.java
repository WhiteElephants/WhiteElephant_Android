package rawe.gordon.com.fruitmarketclient.views.posts;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import java.util.List;

import rawe.gordon.com.fruitmarketclient.R;
import rawe.gordon.com.fruitmarketclient.views.posts.models.ImageNode;
import rawe.gordon.com.fruitmarketclient.views.posts.models.Node;
import rawe.gordon.com.fruitmarketclient.views.posts.models.NodeType;
import rawe.gordon.com.fruitmarketclient.views.posts.models.TextNode;
import rawe.gordon.com.fruitmarketclient.views.posts.models.VideoNode;
import rawe.gordon.com.fruitmarketclient.views.posts.viewholders.FooterViewHolder;
import rawe.gordon.com.fruitmarketclient.views.posts.viewholders.HeaderViewHolder;
import rawe.gordon.com.fruitmarketclient.views.posts.viewholders.ImageViewHolder;
import rawe.gordon.com.fruitmarketclient.views.posts.viewholders.TextViewHolder;
import rawe.gordon.com.fruitmarketclient.views.posts.viewholders.VideoViewHolder;
import rawe.gordon.com.fruitmarketclient.views.posts.watch.EditTextWatcher;

/**
 * Created by gordon on 16/8/23.
 */
public class PostAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements StateChangeListener, PostOperations {

    private List<Node> nodes;
    private LayoutInflater inflater;
    private Context context;
    public static final int INDEX_NOT_FOUND = -1;

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
                return new HeaderViewHolder(inflater.inflate(R.layout.layout_post_compose_item_header, parent, false));
            case NodeType.TEXT:
                return new TextViewHolder(inflater.inflate(R.layout.layout_post_compose_item_text, parent, false), new EditTextWatcher());
            case NodeType.IMAGE:
                return new ImageViewHolder(inflater.inflate(R.layout.layout_post_compose_item_image, parent, false), new EditTextWatcher());
            case NodeType.VIDEO:
                return new VideoViewHolder(inflater.inflate(R.layout.layout_post_compose_item_video, parent, false), new EditTextWatcher());
            case NodeType.FOOTER:
                return new FooterViewHolder(inflater.inflate(R.layout.layout_post_compose_item_footer, parent, false));
            default:
                return new TextViewHolder(inflater.inflate(R.layout.layout_post_compose_item_image, parent, false), new EditTextWatcher());
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        Node node = nodes.get(position);
        switch (node.getType()) {
            case NodeType.HEADER:
                break;
            case NodeType.TEXT:
                TextNode textNode = (TextNode) node;
                TextViewHolder textViewHolder = (TextViewHolder) holder;
                textViewHolder.watcher.setModel(textNode);
                textViewHolder.bindValue(textNode);
                break;
            case NodeType.IMAGE:
                ImageNode imageNode = (ImageNode) node;
                ImageViewHolder imageViewHolder = (ImageViewHolder) holder;
                imageViewHolder.watcher.setModel(imageNode);
                imageViewHolder.bindValue(imageNode);
                imageViewHolder.setStateChangeListener(this);
                break;
            case NodeType.VIDEO:
                VideoNode videoNode = (VideoNode) node;
                VideoViewHolder videoViewHolder = (VideoViewHolder) holder;
                videoViewHolder.watcher.setModel(videoNode);
                videoViewHolder.bindValue(videoNode);
                videoViewHolder.setStateChangeListener(this);
                break;
            case NodeType.FOOTER:
                break;
        }
    }

    @Override
    public int getItemCount() {
        return nodes.size();
    }

    @Override
    public void onRequestAddImageNode(Node node) {
        int position = calcIndex(node);
        if (position == INDEX_NOT_FOUND) return;
        addOneImageNode(position);
    }

    @Override
    public void onRequestAddTextNode(Node node) {
        int position = calcIndex(node);
        if (position == INDEX_NOT_FOUND) return;
        addOneTextNode(position);
    }

    @Override
    public void onRequestAddVideoNode(Node node) {
        int position = calcIndex(node);
        if (position == INDEX_NOT_FOUND) return;
        addOneVideoNode(position);
    }

    private int calcIndex(Node node) {
        for (int i = 0; i < nodes.size(); i++) {
            if (node == nodes.get(i)) return i;
        }
        return INDEX_NOT_FOUND;
    }

    @Override
    public void addOneTextNode(int position) {
        nodes.add(position + 1, new TextNode());
        notifyItemInserted(position + 1);
    }

    @Override
    public void addOneImageNode(int position) {
        nodes.add(position + 1, new ImageNode());
        notifyItemInserted(position + 1);
    }

    @Override
    public void addOneVideoNode(int position) {
        nodes.add(position + 1, new VideoNode());
        notifyItemInserted(position + 1);
    }
}