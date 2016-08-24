package rawe.gordon.com.fruitmarketclient.views.posts.models;

/**
 * Created by gordon on 16/8/23.
 */
public class TextNode implements Node {

    private String content = "this is the default text";

    public String getContent() {
        return content;
    }



    @Override
    public int getType() {
        return NodeType.TEXT;
    }

    @Override
    public void setContent(String content) {
        this.content = content;
    }
}
