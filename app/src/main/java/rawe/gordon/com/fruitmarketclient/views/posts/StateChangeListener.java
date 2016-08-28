package rawe.gordon.com.fruitmarketclient.views.posts;

import rawe.gordon.com.fruitmarketclient.views.posts.models.Node;

/**
 * Created by gordon on 16/8/23.
 */
public interface StateChangeListener {
    void onRequestAddImageNode(Node node);

    void onRequestAddTextNode(Node node);

    void onRequestAddVideoNode(Node node);

    void onImageClicked(Node node, int fromX, int fromY, int fromWidth, int fromHeight);

    void onVideoClicked(Node node);
}
