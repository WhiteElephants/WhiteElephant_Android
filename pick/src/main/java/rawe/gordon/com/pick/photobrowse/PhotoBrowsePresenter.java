package rawe.gordon.com.pick.photobrowse;

import android.widget.Toast;

import java.util.List;

import rawe.gordon.com.business.bus.EnhancedSubscriber;
import rawe.gordon.com.business.bus.RxBus;
import rawe.gordon.com.pick.R;
import rawe.gordon.com.pick.event.PhotoChooseEvent;
import rawe.gordon.com.pick.manage.PhotoPictureManager;
import rawe.gordon.com.pick.model.MediaInfo;
import rawe.gordon.com.pick.utils.PhotoFilter;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by gordon on 3/7/17.
 */

public class PhotoBrowsePresenter implements PhotoBrowseScenario.Presenter {

    public PhotoBrowseModel model;

    PhotoBrowseScenario.View view;

    public PhotoBrowsePresenter(PhotoBrowseModel params, PhotoBrowseScenario.View view) {
        this.model = params;
        this.view = view;
        view.setPresenter(this);
    }

    @Override
    public void start() {
        view.findViews();
        view.addIcons();
        view.bindLinks();
    }

    /**
     * 只带选中的信息回去，刷新数据并且回掉上一级
     */
    public void handleSure() {
        /**
         * 获取所有的结果集,生成缩略图，先交给上一个页面，上一个页面将结果直接给下去
         * */

        List<MediaInfo> selected = PhotoPictureManager.getInstance().getRecords(model.uuidIdentifier);
        if (selected.size() == 0) return;
        view.toggleLoading(true);
        Observable.from(selected).
                subscribeOn(Schedulers.io()).
                map(new Func1<MediaInfo, MediaInfo>() {
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
                        RxBus.getDefault().post(new PhotoChooseEvent(PhotoChooseEvent.TO_LAST_PAGE,
                                PhotoChooseEvent.DONE, result, model.uuidIdentifier));
                        view.toggleLoading(false);
                        view.finishPage();
                    }
                });
    }

    @Override
    public void handleCancel() {
        /**
         * 这里不能只返回选中的，因为可能有一部分是选中了变成未选中的，也将信息带回去
         * */
        RxBus.getDefault().post(new PhotoChooseEvent(PhotoChooseEvent.TO_LAST_PAGE,
                PhotoChooseEvent.CANCEL, model.mediaInfos, model.uuidIdentifier));
        view.finishPage();
    }

    @Override
    public void changeNavText() {
        view.renderNavText(String.valueOf((model.startIndex + 1) + "/" + model.mediaInfos.size()));
        view.changeSendBtnStatus(PhotoFilter.filterSelected(model.mediaInfos).size() + model.otherSelected.size(), model.maxLimit);
    }

    @Override
    public void refresh() {
        changeNavText();
        view.renderChooseIcon(model.mediaInfos.get(model.startIndex).isSelected);
        view.renderOriginalIcon(model.mediaInfos.get(model.startIndex).isOriginal);
        view.navPage(model.startIndex);
    }

    @Override
    public void toggleCurChosen() {
        if (PhotoFilter.filterSelected(model.mediaInfos).size() >= (model.maxLimit - model.otherSelected.size()) && !model.mediaInfos.get(model.startIndex).isSelected) {
            Toast.makeText(view.getContext(), view.getContext().getResources().getString(R.string.chat_im_chat_one_time_max_prefix) + model.maxLimit +
                    view.getContext().getResources().getString(R.string.chat_im_chat_one_time_max_suffix), Toast.LENGTH_SHORT).show();
            return;
        }
        MediaInfo mediaInfo = model.mediaInfos.get(model.startIndex);
        mediaInfo.setSelected(!mediaInfo.isSelected, model.uuidIdentifier);
        view.renderChooseIcon(mediaInfo.isSelected);
        changeNavText();
    }

    @Override
    public void toggleOriginal() {
        MediaInfo mediaInfo = model.mediaInfos.get(model.startIndex);
        mediaInfo.setOriginal(!mediaInfo.isOriginal);
        view.renderOriginalIcon(mediaInfo.isOriginal);
        changeNavText();
    }

    @Override
    public void setCurrentIndex(int index) {
        model.startIndex = index;
        refresh();
    }

    @Override
    public void fillPagerData() {
        view.fillPager(model.mediaInfos);
    }
}
