package rawe.gordon.com.fruitmarketclient.views.posts.models;

/**
 * Created by gordon on 16/8/23.
 */
public class ImageNode implements Node {

    private String content;

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
}
