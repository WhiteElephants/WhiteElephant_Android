package rawe.gordon.com.fruitmarketclient.views.posts.models;

import java.util.List;

/**
 * Created by gordon on 16/8/23.
 */
public class GroupNode implements Node {

    private boolean expanded = false;
    private List<ImageNode> imageNodes;
    private String content;

    public boolean isExpanded() {
        return expanded;
    }

    public void setExpanded(boolean expanded) {
        this.expanded = expanded;
    }

    public List<ImageNode> getImageNodes() {
        return imageNodes;
    }

    public void setImageNodes(List<ImageNode> imageNodes) {
        this.imageNodes = imageNodes;
    }

    @Override
    public int getType() {
        return NodeType.GROUP;
    }

    public String getContent() {
        return content;
    }

    @Override
    public void setContent(String content) {
        this.content = content;
    }

    public GroupNode(List<ImageNode> imageNodes) {
        this.imageNodes = imageNodes;
    }

    public GroupNode() {
    }
}
