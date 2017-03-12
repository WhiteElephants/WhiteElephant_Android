package rawe.gordon.com.fruitmarketclient.router;

import android.app.Activity;
import android.content.Intent;

import java.util.List;

import rawe.gordon.com.fruitmarketclient.compose.PostComposeScenario;
import rawe.gordon.com.fruitmarketclient.compose.PostComposeActivity;
import rawe.gordon.com.pick.model.MediaInfo;
import rawe.gordon.com.pick.transfer.Transfer;

/**
 * Created by gordon on 3/12/17.
 */

public class ActivityHelper {
    private ActivityHelper() {
    }

    public static void startToCompose(Activity act, List<MediaInfo> mediaInfos) {
        Transfer.getInstance().putObject(PostComposeScenario.POST_COMPOSE_DATA, mediaInfos);
        act.startActivity(new Intent(act, PostComposeActivity.class));
    }
}
