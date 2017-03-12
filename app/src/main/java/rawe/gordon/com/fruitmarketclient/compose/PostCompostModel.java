package rawe.gordon.com.fruitmarketclient.compose;

import java.util.List;
import java.util.UUID;

import rawe.gordon.com.fruitmarketclient.compose.mock.Mock;
import rawe.gordon.com.fruitmarketclient.compose.models.Node;
import rawe.gordon.com.pick.model.MediaInfo;

/**
 * Created by gordon on 3/12/17.
 */

public class PostCompostModel {
    public List<MediaInfo> mediaInfos;

    public List<Node> nodes;

    public String postUuid = UUID.randomUUID().toString().replace("-", "");

    public String initialStringData = "";

    public PostCompostModel(List<MediaInfo> mediaInfos) {
        this.mediaInfos = mediaInfos;
        nodes = Mock.composeData(mediaInfos);
    }

    public PostCompostModel() {
    }

    public PostCompostModel passParams(List<Node> nodes) {
        this.nodes = nodes;
        return this;
    }
}
