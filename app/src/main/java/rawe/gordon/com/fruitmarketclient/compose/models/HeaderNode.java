package rawe.gordon.com.fruitmarketclient.compose.models;

/**
 * Created by gordon on 16/8/23.
 */
public class HeaderNode implements Node {

    private String content;

    @Override
    public int getType() {
        return NodeType.HEADER;
    }

    public String getContent() {
        return content;
    }

    @Override
    public void setContent(String content) {
        this.content = content;
    }
}
