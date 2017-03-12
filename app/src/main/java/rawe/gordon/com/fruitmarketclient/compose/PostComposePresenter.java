package rawe.gordon.com.fruitmarketclient.compose;

import android.text.TextUtils;

import com.alibaba.fastjson.JSON;

import rawe.gordon.com.business.db.DBManager;
import rawe.gordon.com.business.utils.DateUtil;
import rawe.gordon.com.fruitmarketclient.compose.mock.Mock;
import rawe.gordon.com.fruitmarketclient.compose.models.HeaderNode;
import rawe.gordon.com.fruitmarketclient.compose.models.MergedNode;
import rawe.gordon.com.fruitmarketclient.compose.models.NodeFilter;

/**
 * Created by gordon on 3/12/17.
 */

public class PostComposePresenter implements PostComposeScenario.Presenter {

    PostComposeScenario.View view;
    PostCompostModel model;

    public PostComposePresenter(PostComposeScenario.View view, PostCompostModel model) {
        this.view = view;
        this.model = model;
        view.setPresenter(this);
    }

    @Override
    public void start() {
        view.findViews();
        view.bindActions();
        view.displayData(model.mediaInfos);
    }

    @Override
    public void handleSure() {
        saveDraft();
    }

    @Override
    public void handleCancel() {
        String title = "";
        try {
            title = NodeFilter.getTitle(model.nodes);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (TextUtils.isEmpty(title)) {
            view.renderHintEmpty();
        } else {
            if (model.initialStringData.equals(JSON.toJSONString(model.nodes))) {
                view.finishPage();
                return;
            }
            view.renderHintSave();
        }
    }

    @Override
    public void saveDraft() {
        DBManager.getInstance().savePost(model.postUuid, ((HeaderNode) model.nodes.get(0)).getContent(),
                JSON.toJSONString(MergedNode.toMergedNodes(model.nodes)),
                DateUtil.currentTime(), NodeFilter.extractPostIconFromNodes(model.nodes));
    }
}
