package rawe.gordon.com.pick.pick.manage;

import android.app.Activity;
import android.text.TextUtils;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import rawe.gordon.com.business.bus.EnhancedSubscriber;
import rawe.gordon.com.business.bus.RxBus;
import rawe.gordon.com.pick.pick.event.PhotoChooseEvent;
import rawe.gordon.com.pick.pick.model.MediaInfo;
import rawe.gordon.com.pick.pick.photochoose.PhotoChooseScenario;
import rawe.gordon.com.pick.pick.router.ActivityHelper;

/**
 * Created by gordon on 3/9/17.
 */

public class PhotoPictureManager {
    private PhotoPictureManager() {
        RxBus.getDefault().toObserverable(PhotoChooseEvent.class).subscribe(new EnhancedSubscriber<PhotoChooseEvent>() {
            @Override
            public void onNext(PhotoChooseEvent photoChooseEvent) {
                for (PhotoChooseListener listener : listeners) {
                    if (TextUtils.equals(listener.uuidIdentifier, photoChooseEvent.uuidIdentifier)
                            && photoChooseEvent.whichToWhich == PhotoChooseEvent.TO_RESULT &&
                            photoChooseEvent.intentType == PhotoChooseEvent.DONE) {
                        listener.getResult(photoChooseEvent.mediaInfos);
                        releaseRecords(photoChooseEvent.uuidIdentifier);
                    }
                }
            }
        });
    }

    private Map<String, List<MediaInfo>> resultKeeper = new HashMap<>();

    public void addPickRecord(String sessionId, MediaInfo mediaInfo) {
        List<MediaInfo> records = resultKeeper.get(sessionId);
        if (records == null) {
            records = new ArrayList<>();
            records.add(mediaInfo);
            resultKeeper.put(sessionId, records);
            return;
        }
        if (records.contains(mediaInfo)) {
            records.remove(mediaInfo);
        }
        records.add(mediaInfo);
    }

    public void removePickRecord(String sessionId, MediaInfo mediaInfo) {
        List<MediaInfo> group = resultKeeper.get(sessionId);
        if (group == null) return;
        group.remove(mediaInfo);
    }

    public void releaseRecords(String sessionId) {
        resultKeeper.remove(sessionId);
        for (PhotoChooseListener listener : listeners) {
            if (listener.uuidIdentifier.equals(sessionId)) {
                listeners.remove(listener);
            }
        }
    }

    public List<MediaInfo> getRecords(String sessionId) {
        List<MediaInfo> group = resultKeeper.get(sessionId);
        if (group == null) return new ArrayList<>();
        return group;
    }

    public static class Holder {
        public static PhotoPictureManager instance = new PhotoPictureManager();
    }

    public static PhotoPictureManager getInstance() {
        return Holder.instance;
    }


    private List<PhotoChooseListener> listeners = new ArrayList<>();

    public void startToChooseMedias(Activity from, PhotoChooseListener listener, int max) {
        listeners.add(listener);
        ActivityHelper.choosePicturesOrVideos(from, PhotoChooseScenario.Criterion.buildPicturesCriterion(max, listener.uuidIdentifier));
    }
}
