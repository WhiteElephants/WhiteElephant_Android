package rawe.gordon.com.pick.photochoose;


import android.app.Activity;
import android.content.Context;


import java.io.Serializable;
import java.util.List;

import rawe.gordon.com.business.base.BasePresenter;
import rawe.gordon.com.business.base.BaseView;
import rawe.gordon.com.pick.model.MediaInfo;

/**
 * Created by gordon on 3/4/17.
 */

public class PhotoChooseScenario {

    public static final String SELECT_CRITERION = "SELECT_CRITERION";
    public static final String ACTIVITY_RESUME = "ACTIVITY_RESUME";

    interface View extends BaseView<Presenter> {

        void findViews();

        void bindViews();

        void displayMedias(List<MediaInfo> mediaInfos);

        Context getContext();

        void refreshAdapter();

        void finishPage();

        void changeSendBtnStatus(int cur, int total);

        void browseChoosePicturesOrVideos(Activity from, List<MediaInfo> photos, int startIndex, int maxLimit, List<MediaInfo> otherSelected, String uuidIdentifier);

        void refreshFirst(String navText);

        void toggleFilter(boolean filterBlock, String filter);

        void refreshResources(PhotoChooseModel model);

        void renderFilterList(List<String> filters);

        void toggleLoading(boolean showLoading);

        void renderSavedInstance(List<MediaInfo> mediaInfos);
    }

    interface Presenter extends BasePresenter {

        void deliverResultAndFinish(List<MediaInfo> workGroup);

        void findViewsAndBinkLinks();

        void gotoChoosePicturesOrVideos(Activity from, int index);

        void refreshWithBrowseResult(List<MediaInfo> browseResults);

        void refreshFirstTime();

        void handleCancel();

        void handleDone();

        void changeNavText();

        void toggleFilter();

        void hideFilter();

        void handlePreview();

        void switchFilter(String groupName);

        void shuffleTag();

        void handleIndicator(MediaInfo mediaInfo);

        void fillFilterList();

        void resumeSavedInstance(PhotoChooseModel model);
    }

    public static class Criterion implements Serializable {

        public static final int COLUMN_COUNT = 3;
        public static final boolean showMask = false;

        public int mediaType = MediaInfo.PICTURE;
        public int maxLimit = 1;
        public boolean allowMix = false;
        public String uuidIdentifier = "";

        public Criterion(int mediaType, int maxLimit, boolean allowMix, String uuidIdentifier) {
            this.mediaType = mediaType;
            this.maxLimit = maxLimit;
            this.allowMix = allowMix;
            this.uuidIdentifier = uuidIdentifier;
        }

        public static Criterion buildCriterion(int mediaType, int maxLimit, boolean allowMix, String uuidIdentifier) {
            return new Criterion(mediaType, maxLimit, allowMix, uuidIdentifier);
        }

        public static Criterion buildMixCriterion(int maxLimit, String uuidIdentifier) {
            return new Criterion(MediaInfo.VIDEO, maxLimit, true, uuidIdentifier);
        }

        public static Criterion buildVideoCriterion(int maxLimit, String uuidIdentifier) {
            return new Criterion(MediaInfo.VIDEO, maxLimit, false, uuidIdentifier);
        }

        public static Criterion buildPicturesCriterion(int maxLimit, String uuidIdentifier) {
            return new Criterion(MediaInfo.PICTURE, maxLimit, false, uuidIdentifier);
        }

        @Override
        public String toString() {
            return "mediaType -> " + mediaType + '\n' +
                    "maxLimit -> " + maxLimit + '\n' +
                    "allowMix -> " + String.valueOf(allowMix);
        }
    }
}
