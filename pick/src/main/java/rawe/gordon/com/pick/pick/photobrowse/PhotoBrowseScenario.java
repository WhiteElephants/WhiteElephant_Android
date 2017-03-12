package rawe.gordon.com.pick.pick.photobrowse;


import android.content.Context;


import java.util.List;

import rawe.gordon.com.pick.pick.base.BasePresenter;
import rawe.gordon.com.pick.pick.base.BaseView;
import rawe.gordon.com.pick.pick.model.MediaInfo;

/**
 * Created by gordon on 3/4/17.
 */

public class PhotoBrowseScenario {

    public static final String TOKEN_PHOTOS = "TOKEN_PHOTOS";

    interface View extends BaseView<Presenter> {
        void findViews();

        void bindLinks();

        void addIcons();

        void finishPage();

        void renderNavText(String txt);

        void renderChooseIcon(boolean chosen);

        void renderOriginalIcon(boolean isOriginal);

        void navPage(int index);

        Context getContext();

        void fillPager(List<MediaInfo> infos);

        void changeSendBtnStatus(int cur, int total);

        void toggleLoading(boolean showLoading);

    }

    public interface Presenter extends BasePresenter {

        void handleSure();

        void handleCancel();

        void changeNavText();

        void refresh();

        void toggleCurChosen();

        void toggleOriginal();

        void setCurrentIndex(int index);

        void fillPagerData();

    }
}
