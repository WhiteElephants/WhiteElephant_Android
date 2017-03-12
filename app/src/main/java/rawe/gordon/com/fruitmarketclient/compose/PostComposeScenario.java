package rawe.gordon.com.fruitmarketclient.compose;

import java.util.List;

import rawe.gordon.com.business.base.BasePresenter;
import rawe.gordon.com.business.base.BaseView;
import rawe.gordon.com.pick.model.MediaInfo;

/**
 * Created by gordon on 3/12/17.
 */

public class PostComposeScenario {

    public static final String POST_COMPOSE_DATA = "POST_COMPOSE_DATA";

    public interface View extends BaseView<Presenter> {
        void findViews();

        void bindActions();

        void displayData(List<MediaInfo> mediaInfos);

        void finishPage();

        void renderHintEmpty();

        void renderHintSave();
    }

    public interface Presenter extends BasePresenter {
        void handleSure();

        void handleCancel();

        void saveDraft();
    }

}
