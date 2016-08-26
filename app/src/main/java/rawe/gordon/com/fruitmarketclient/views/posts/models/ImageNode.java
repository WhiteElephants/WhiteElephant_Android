package rawe.gordon.com.fruitmarketclient.views.posts.models;

/**
 * Created by gordon on 16/8/23.
 */
public class ImageNode implements Node {

    private String content;
    private String storagePath;
    private boolean expanded = false;

    public ImageNode() {
    }

    public ImageNode(String storagePath) {
        this.storagePath = storagePath;
    }

    public ImageNode(String storagePath, String content) {
        this.storagePath = storagePath;
        this.content = content;
    }

    public String getStoragePath() {
        return storagePath;
    }

    public void setStoragePath(String storagePath) {
        this.storagePath = storagePath;
    }

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
