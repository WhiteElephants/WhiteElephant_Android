package rawe.gordon.com.fruitmarketclient.views.posts.models;

/**
 * Created by gordon on 16/8/23.
 */
public class TextNode implements Node {

    private String content;


    @Override
    public int getType() {
        return NodeType.TEXT;
    }
}
