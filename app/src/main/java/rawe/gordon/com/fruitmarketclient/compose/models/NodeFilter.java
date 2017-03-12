package rawe.gordon.com.fruitmarketclient.compose.models;

import java.util.List;

/**
 * Created by gordon on 3/12/17.
 */

public class NodeFilter {
    public  static String getTitle(List<Node> nodes) throws Exception {
        if (nodes == null || nodes.size() == 0) throw new Exception("");
        Node firstNode = nodes.get(0);
        if (!(firstNode instanceof HeaderNode)) throw new Exception("");
        return ((HeaderNode) firstNode).getContent();
    }

    public static String extractPostIconFromNodes(List<Node> nodes) {
        for (Node node : nodes) {
            if (node.getType() == NodeType.IMAGE) return ((ImageNode) node).getStoragePath();
            if (node.getType() == NodeType.GROUP)
                return ((GroupNode) node).getImageNodes().get(0).getStoragePath();
        }
        return "";
    }
}
