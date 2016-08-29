package rawe.gordon.com.fruitmarketclient.views.posts.mock;

import com.iknow.imageselect.fragments.models.ImageMediaEntry;

import java.util.ArrayList;
import java.util.List;

import rawe.gordon.com.fruitmarketclient.views.posts.models.FooterNode;
import rawe.gordon.com.fruitmarketclient.views.posts.models.GroupNode;
import rawe.gordon.com.fruitmarketclient.views.posts.models.HeaderNode;
import rawe.gordon.com.fruitmarketclient.views.posts.models.ImageNode;
import rawe.gordon.com.fruitmarketclient.views.posts.models.Node;

/**
 * Created by gordon on 16/8/23.
 */
public class Mock {
    public static List<Node> getInitialData() {
        List<Node> ret = new ArrayList<>();
        ret.add(new HeaderNode());
        ret.add(new FooterNode());
        return ret;
    }

    public static List<Node> composeData(List<ImageMediaEntry> src) {
        List<Node> ret = new ArrayList<>();
        ret.add(new HeaderNode());
        List<ImageNode> imageNodes = new ArrayList<>();
        for (ImageMediaEntry entry : src) {
            imageNodes.add(new ImageNode(entry.getProtocolPath()));
        }
        ret.add(new GroupNode(imageNodes));
        ret.add(new FooterNode());
        return ret;
    }
}
