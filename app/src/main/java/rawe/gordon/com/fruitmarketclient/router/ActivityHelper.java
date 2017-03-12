package rawe.gordon.com.fruitmarketclient.router;

import android.app.Activity;
import android.content.Intent;

import com.alibaba.fastjson.JSON;

import java.util.List;

import rawe.gordon.com.fruitmarketclient.compose.PostComposeActivity;
import rawe.gordon.com.fruitmarketclient.compose.PostComposeScenario;
import rawe.gordon.com.fruitmarketclient.compose.models.MergedNode;
import rawe.gordon.com.fruitmarketclient.old.fragments.posts.PostComposeFragment;
import rawe.gordon.com.pick.model.MediaInfo;
import rawe.gordon.com.pick.transfer.Transfer;

/**
 * Created by gordon on 3/12/17.
 */

public class ActivityHelper {
    private ActivityHelper() {
    }

    public static void startToCompose(Activity act, List<MediaInfo> mediaInfos) {
        if (act == null || act.isFinishing()) return;
        Transfer.getInstance().putObject(PostComposeScenario.POST_COMPOSE_DATA, mediaInfos);
        act.startActivity(new Intent(act, PostComposeActivity.class));
    }

    public static void getDBPost(Activity act, String postData, String uuid) {
        if (act == null || act.isFinishing()) return;
        List<MergedNode> nodes = JSON.parseArray(postData, MergedNode.class);
        PostComposeFragment.ResumeModel resumeModel = new PostComposeFragment.ResumeModel(uuid, MergedNode.toNodes(nodes));
        Transfer.getInstance().putObject(PostComposeScenario.POST_COMPOSE_DATA, resumeModel);
        act.startActivity(new Intent(act, PostComposeActivity.class));
    }
}
