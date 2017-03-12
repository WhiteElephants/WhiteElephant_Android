package rawe.gordon.com.fruitmarketclient.views.posts;

import java.util.List;

import rawe.gordon.com.pick.pick.model.MediaInfo;

/**
 * Created by gordon on 8/24/16.
 */
public interface PostOperations {
    void addOneTextNode(int position);

    void addOneImageNode(int position);

    void addOneVideoNode(int position);

    void addMultipleImageNodes(List<MediaInfo> entries, int position);
}
