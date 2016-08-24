package rawe.gordon.com.fruitmarketclient.views.posts.models;

/**
 * Created by gordon on 16/8/23.
 */
public class ImageNode implements Node {

    private String content;
    private boolean expanded = false;

    @Override
    public int getType() {
        return NodeType.IMAGE;
    }

    @Override
    public void setContent(String content) {
        this.content = content;
    }

    public String getContent() {
        return content;
    }

    public boolean isExpanded() {
        return expanded;
    }

    public void setExpanded(boolean expanded) {
        this.expanded = expanded;
    }
}
