package rawe.gordon.com.fruitmarketclient.compose.mock;


import java.util.ArrayList;
import java.util.List;

import rawe.gordon.com.fruitmarketclient.compose.models.FooterNode;
import rawe.gordon.com.fruitmarketclient.compose.models.GroupNode;
import rawe.gordon.com.fruitmarketclient.compose.models.HeaderNode;
import rawe.gordon.com.fruitmarketclient.compose.models.ImageNode;
import rawe.gordon.com.fruitmarketclient.compose.models.Node;
import rawe.gordon.com.pick.model.MediaInfo;

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

    public static List<Node> composeData(List<MediaInfo> src) {
        List<Node> ret = new ArrayList<>();
        ret.add(new HeaderNode());
        List<ImageNode> imageNodes = new ArrayList<>();
        for (MediaInfo entry : src) {
            imageNodes.add(new ImageNode(entry.getProtocaledPath()));
        }
        ret.add(new GroupNode(imageNodes));
        ret.add(new FooterNode());
        return ret;
    }
}
