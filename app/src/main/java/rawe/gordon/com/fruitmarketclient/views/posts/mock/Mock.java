package rawe.gordon.com.fruitmarketclient.views.posts.mock;

import java.util.ArrayList;
import java.util.List;

import rawe.gordon.com.fruitmarketclient.views.posts.models.FooterNode;
import rawe.gordon.com.fruitmarketclient.views.posts.models.HeaderNode;
import rawe.gordon.com.fruitmarketclient.views.posts.models.ImageNode;
import rawe.gordon.com.fruitmarketclient.views.posts.models.Node;
import rawe.gordon.com.fruitmarketclient.views.posts.models.TextNode;
import rawe.gordon.com.fruitmarketclient.views.posts.models.VideoNode;

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
}
