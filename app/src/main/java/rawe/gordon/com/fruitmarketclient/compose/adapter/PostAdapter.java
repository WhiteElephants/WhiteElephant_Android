package rawe.gordon.com.fruitmarketclient.compose.adapter;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import rawe.gordon.com.business.activities.SitoImageViewActivity;
import rawe.gordon.com.business.utils.ToastUtil;
import rawe.gordon.com.fruitmarketclient.R;
import rawe.gordon.com.fruitmarketclient.compose.PostCompostModel;
import rawe.gordon.com.fruitmarketclient.compose.models.GroupNode;
import rawe.gordon.com.fruitmarketclient.compose.models.HeaderNode;
import rawe.gordon.com.fruitmarketclient.compose.models.ImageNode;
import rawe.gordon.com.fruitmarketclient.compose.models.Node;
import rawe.gordon.com.fruitmarketclient.compose.models.NodeType;
import rawe.gordon.com.fruitmarketclient.compose.models.TextNode;
import rawe.gordon.com.fruitmarketclient.compose.models.VideoNode;
import rawe.gordon.com.fruitmarketclient.compose.viewholders.FooterViewHolder;
import rawe.gordon.com.fruitmarketclient.compose.viewholders.GroupViewHolder;
import rawe.gordon.com.fruitmarketclient.compose.viewholders.HeaderViewHolder;
import rawe.gordon.com.fruitmarketclient.compose.viewholders.ImageViewHolder;
import rawe.gordon.com.fruitmarketclient.compose.viewholders.TextViewHolder;
import rawe.gordon.com.fruitmarketclient.compose.viewholders.VideoViewHolder;
import rawe.gordon.com.fruitmarketclient.compose.watch.EditTextWatcher;
import rawe.gordon.com.pick.model.MediaInfo;

/**
 * Created by gordon on 16/8/23.
 */
public class PostAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements StateChangeListener, PostOperations {

    public List<Node> nodes;
    private LayoutInflater inflater;
    private Context context;
    public static final int INDEX_NOT_FOUND = -1;
    private Operation outSideOperation;

    public PostAdapter(Context context, PostCompostModel model, Operation outSideOperation) {
        this.nodes = model.nodes;
        this.outSideOperation = outSideOperation;
        this.context = context;
        this.inflater = LayoutInflater.from(context);
    }

    public PostAdapter(Context context, List<Node> model, Operation outSideOperation) {
        this.nodes = model;
        this.outSideOperation = outSideOperation;
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
                return new HeaderViewHolder(inflater.inflate(R.layout.layout_post_compose_item_header, parent, false), new EditTextWatcher());
            case NodeType.TEXT:
                return new TextViewHolder(inflater.inflate(R.layout.layout_post_compose_item_text, parent, false), new EditTextWatcher());
            case NodeType.IMAGE:
                return new ImageViewHolder(inflater.inflate(R.layout.layout_post_compose_item_image, parent, false), new EditTextWatcher());
            case NodeType.VIDEO:
                return new VideoViewHolder(inflater.inflate(R.layout.layout_post_compose_item_video, parent, false), new EditTextWatcher());
            case NodeType.GROUP:
                return new GroupViewHolder(inflater.inflate(R.layout.layout_post_compose_item_group, parent, false), new EditTextWatcher(), (Activity) context);
            case NodeType.FOOTER:
                return new FooterViewHolder(inflater.inflate(R.layout.layout_post_compose_item_footer, parent, false));
            default:
                return new TextViewHolder(inflater.inflate(R.layout.layout_post_compose_item_text, parent, false), new EditTextWatcher());
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        Node node = nodes.get(position);
        switch (node.getType()) {
            case NodeType.HEADER:
                HeaderNode headerNode = (HeaderNode) node;
                HeaderViewHolder headerViewHolder = (HeaderViewHolder) holder;
                headerViewHolder.watcher.setModel(headerNode);
                headerViewHolder.bindValue(headerNode);
                headerViewHolder.setStateChangeListener(this);
                break;
            case NodeType.TEXT:
                TextNode textNode = (TextNode) node;
                TextViewHolder textViewHolder = (TextViewHolder) holder;
                textViewHolder.watcher.setModel(textNode);
                textViewHolder.bindValue(textNode);
                textViewHolder.setStateChangeListener(this);
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
            case NodeType.GROUP:
                GroupNode groupNode = (GroupNode) node;
                GroupViewHolder groupViewHolder = (GroupViewHolder) holder;
                groupViewHolder.watcher.setModel(groupNode);
                groupViewHolder.bindValue(groupNode);
                groupViewHolder.setStateChangeListener(this);
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
        outSideOperation.choosePictures(position);
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

    @Override
    public void onImageClicked(Node node, int fromX, int fromY, int fromWidth, int fromHeight) {
        SitoImageViewActivity.ImageModel model = new SitoImageViewActivity.ImageModel(((ImageNode) node).getStoragePath(), fromX, fromY, fromWidth, fromHeight);
        SitoImageViewActivity.goToSitoImageBrowsePage((Activity) context, model);
    }

    @Override
    public void onVideoClicked(Node node) {
        ToastUtil.say("video clicked");
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

    @Override
    public void addMultipleImageNodes(List<MediaInfo> entries, int position) {
        List<ImageNode> newValues = new ArrayList<>();
        for (MediaInfo entry : entries) {
            newValues.add(new ImageNode(entry.getProtocaledPath()));
        }
//        nodes.addAll(position + 1, newValues);
//        notifyItemRangeChanged(position + 1, newValues.size());
        nodes.add(position + 1, new GroupNode(newValues));
        notifyItemInserted(position + 1);
    }

    public interface Operation {
        void choosePictures(int position);
    }
}
