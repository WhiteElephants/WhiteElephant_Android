package rawe.gordon.com.fruitmarketclient.compose.models;

/**
 * Created by gordon on 16/8/23.
 */
public class FooterNode implements Node {


    @Override
    public int getType() {
        return NodeType.FOOTER;
    }

    @Override
    public void setContent(String content) {

    }
}
