package rawe.gordon.com.fruitmarketclient.views.posts.models;

/**
 * Created by gordon on 16/8/23.
 */
public class HeaderNode implements Node {
    

    @Override
    public int getType() {
        return NodeType.HEADER;
    }

    @Override
    public void setContent(String content) {

    }
}
