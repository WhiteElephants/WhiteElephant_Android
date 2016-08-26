package rawe.gordon.com.fruitmarketclient.views.posts;

import com.iknow.imageselect.fragments.models.ImageMediaEntry;

import java.util.List;

/**
 * Created by gordon on 8/24/16.
 */
public interface PostOperations {
    void addOneTextNode(int position);

    void addOneImageNode(int position);

    void addOneVideoNode(int position);

    void addMultipleImageNodes(List<ImageMediaEntry> entries,int position);
}
