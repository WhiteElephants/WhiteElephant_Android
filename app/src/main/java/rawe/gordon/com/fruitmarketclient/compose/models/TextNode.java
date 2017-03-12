package rawe.gordon.com.fruitmarketclient.compose.models;

/**
 * Created by gordon on 16/8/23.
 */
public class TextNode implements Node {

    private String content;

    public String getContent() {
        return content;
    }

    public TextNode(String content) {
        this.content = content;
    }

    public TextNode() {
    }

    private boolean isSubTitle = false;

    public boolean isSubTitle() {
        return isSubTitle;
    }

    public void setSubTitle(boolean subTitle) {
        isSubTitle = subTitle;
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
