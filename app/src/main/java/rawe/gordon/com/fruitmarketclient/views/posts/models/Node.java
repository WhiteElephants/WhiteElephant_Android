package rawe.gordon.com.fruitmarketclient.views.posts.models;

import java.io.Serializable;

/**
 * Created by gordon on 16/8/23.
 */
public interface Node extends Serializable {
    int getType();

    void setContent(String content);
}
