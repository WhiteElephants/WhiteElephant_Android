package rawe.gordon.com.pick.pick.photochoose;

import android.app.Activity;
import android.os.AsyncTask;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import rawe.gordon.com.business.bus.EnhancedSubscriber;
import rawe.gordon.com.business.bus.RxBus;
import rawe.gordon.com.pick.pick.event.PhotoChooseEvent;
import rawe.gordon.com.pick.pick.manage.PhotoPictureManager;
import rawe.gordon.com.pick.pick.model.MediaInfo;
import rawe.gordon.com.pick.pick.router.ActivityHelper;
import rawe.gordon.com.pick.pick.utils.PhotoFilter;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by gordon on 3/4/17.
 */

public class PhotoChoosePresenter implements PhotoChooseScenario.Presenter {

    private PhotoChooseScenario.View view;

    private PhotoChooseModel model;

    public PhotoChoosePresenter(PhotoChooseScenario.View view) {
        this.view = view;
        view.setPresenter(this);
    }

    public void passParams(PhotoChooseModel model) {
        this.model = model;
    }

    @Override
    public void start() {
        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                List<MediaInfo> photos = model.criterion.allowMix ? PhotoChooseModel.getAllMediaFilesWithDetail(view.getContext()) :
                        model.criterion.mediaType == MediaInfo.VIDEO ? PhotoChooseModel.getAllVideoFiles(view.getContext()) :
                                PhotoChooseModel.getAllImages(view.getContext());
                view.displayMedias(photos);
            }
        });
    }

    @Override
    public void deliverResultAndFinish(List<MediaInfo> workGroup) {
        RxBus.getDefault().post(new PhotoChooseEvent(PhotoChooseEvent.TO_RESULT,
                PhotoChooseEvent.DONE, PhotoPictureManager.getInstance().getRecords(model.criterion.uuidIdentifier), model.criterion.uuidIdentifier));
        view.finishPage();
    }

    @Override
    public void findViewsAndBinkLinks() {
        view.findViews();
        view.bindViews();
        view.toggleLoading(true);
    }

    @Override
    public void gotoChoosePicturesOrVideos(Activity from, int index) {
        view.browseChoosePicturesOrVideos(from, new ArrayList<>(model.getWorkingGroup()), index, model.criterion.maxLimit, model.getDiff(), model.criterion.uuidIdentifier);
    }

    /**
     * 处理预览页和详情选择页返回的数据，考虑预览页带过去的数据包含全部，那么就全部刷新吧，不然可以只刷新工作组和原数据
     */
    @Override
    public void refreshWithBrowseResult(List<MediaInfo> browseResults) {
        view.refreshAdapter();
        changeNavText();
    }

    @Override
    public void refreshFirstTime() {
        view.refreshFirst(model.title);
    }

    @Override
    public void handleCancel() {
        view.finishPage();
        PhotoPictureManager.getInstance().releaseRecords(model.criterion.uuidIdentifier);
    }

    @Override
    public void handleDone() {
        List<MediaInfo> result = PhotoPictureManager.getInstance().getRecords(model.criterion.uuidIdentifier);
        if (result.size() == 0) return;
        view.toggleLoading(true);
        Observable.from(result)
                .subscribeOn(Schedulers.io())
                .map(new Func1<MediaInfo, MediaInfo>() {
                    @Override
                    public MediaInfo call(MediaInfo mediaInfo) {
                        mediaInfo.generateMiddleAndThumbnails();
                        return mediaInfo;
                    }
                }).toList()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new EnhancedSubscriber<List<MediaInfo>>() {
                    @Override
                    public void onNext(List<MediaInfo> result) {
                        super.onNext(result);
                        RxBus.getDefault().post(new PhotoChooseEvent(PhotoChooseEvent.TO_RESULT,
                                PhotoChooseEvent.DONE, result, model.criterion.uuidIdentifier));
                        view.toggleLoading(false);
                        view.finishPage();
                    }

                    @Override
                    public void onCompleted() {
                        super.onCompleted();
                    }
                });
    }

    @Override
    public void changeNavText() {
        view.changeSendBtnStatus(PhotoFilter.filterSelected(model.getWorkingGroup()).size() + model.getDiff().size(), model.criterion.maxLimit);
    }

    @Override
    public void toggleFilter() {
        model.showFilter = !model.showFilter;
        view.toggleFilter(model.showFilter, model.workingTag);
    }

    @Override
    public void hideFilter() {
        model.showFilter = false;
        view.toggleFilter(false, model.workingTag);
    }


    @Override
    public void handlePreview() {
        /**
         * 预览是预览全部图片被选中的图片而不是某个目录下的被选中的，这里需要使用model.originalData而不是model.getWorkingGroup()
         * */
        List<MediaInfo> selectedInfos = new ArrayList<>(PhotoPictureManager.getInstance().getRecords(model.criterion.uuidIdentifier));
        if (selectedInfos.size() == 0) return;
        ActivityHelper.browseChoosePicturesOrVideos((Activity) view.getContext(), selectedInfos, 0, model.criterion.maxLimit, new ArrayList<MediaInfo>(), model.criterion.uuidIdentifier);
    }

    @Override
    public void switchFilter(String groupName) {
        model.workingTag = groupName;
        view.refreshResources(model);
    }

    @Override
    public void shuffleTag() {
        List<String> keys = new ArrayList<>(model.categorizedData.keySet());
        switchFilter(keys.get(new Random().nextInt(keys.size())));
    }

    @Override
    public void handleIndicator(MediaInfo mediaInfo) {
        /**
         * 更新当前数据
         * */
        mediaInfo.setSelected(!mediaInfo.isSelected, model.criterion.uuidIdentifier);
    }

    @Override
    public void fillFilterList() {
        List<String> retValues = new ArrayList<>(model.categorizedData.keySet());
        view.renderFilterList(retValues);
    }

    @Override
    public void resumeSavedInstance(PhotoChooseModel model) {
        view.renderSavedInstance(model.getWorkingGroup());
    }
}
