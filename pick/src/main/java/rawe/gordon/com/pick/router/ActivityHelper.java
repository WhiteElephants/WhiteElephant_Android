package rawe.gordon.com.pick.router;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;

import java.util.List;

import rawe.gordon.com.pick.event.PhotoChooseEvent;
import rawe.gordon.com.pick.model.MediaInfo;
import rawe.gordon.com.pick.photobrowse.PhotoBrowseActivity;
import rawe.gordon.com.pick.photobrowse.PhotoBrowseModel;
import rawe.gordon.com.pick.photobrowse.PhotoBrowseScenario;
import rawe.gordon.com.pick.photochoose.PhotoChooseActivity;
import rawe.gordon.com.pick.photochoose.PhotoChooseScenario;
import rawe.gordon.com.pick.transfer.Transfer;

/**
 * Created by gordon on 3/6/17.
 */

public class ActivityHelper {
    private ActivityHelper() {
    }

    /**
     * 开启图片选择页面，通过RxBus获取选择的结果，结果会以{@link PhotoChooseEvent }的形式回掉,
     * 这里开启了事件公用，接收结果只有当 whichToWhich = PhotoChooseEvent.TO_RESULT &&
     * intentType = PhotoChooseEvent.DONE 时进行
     */
    public static boolean choosePicturesOrVideos(Activity from, PhotoChooseScenario.Criterion criterion) {
        try {
            Transfer.getInstance().putObject(PhotoChooseScenario.SELECT_CRITERION, criterion);
            Intent intent = new Intent(from, PhotoChooseActivity.class);
            from.startActivity(intent);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 在不确定使用方法的情况下，不要使用该路由跳转到图片滑动选择页
     */
    public static boolean browseChoosePicturesOrVideos(Activity from, List<MediaInfo> photos, int startIndex, int maxLimit, List<MediaInfo> otherSelected, String uuidIdentifier) {
        if (startIndex >= photos.size()) return false;
        try {
            Transfer.getInstance().putObject(PhotoBrowseScenario.TOKEN_PHOTOS, PhotoBrowseModel.buildParams(photos, startIndex, maxLimit, otherSelected, uuidIdentifier));
            Intent intent = new Intent(from, PhotoBrowseActivity.class);
            from.startActivity(intent);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public static void playVideo(Activity from, String url) {
        Uri uri = Uri.parse(url);
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setDataAndType(uri, "video/*");
        from.startActivity(intent);
    }
}
