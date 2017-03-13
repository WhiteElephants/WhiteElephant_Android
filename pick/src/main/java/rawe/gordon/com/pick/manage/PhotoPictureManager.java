package rawe.gordon.com.pick.manage;

import android.app.Activity;
import android.text.TextUtils;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import rawe.gordon.com.business.bus.EnhancedSubscriber;
import rawe.gordon.com.business.bus.RxBus;
import rawe.gordon.com.pick.event.PhotoChooseEvent;
import rawe.gordon.com.pick.model.MediaInfo;
import rawe.gordon.com.pick.photochoose.PhotoChooseScenario;
import rawe.gordon.com.pick.router.ActivityHelper;

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

    private WeakReference<List<MediaInfo>> videos;
    private WeakReference<List<MediaInfo>> images;
    private WeakReference<List<MediaInfo>> medias;

    public void storeVideos(List<MediaInfo> videos) {
        this.videos = new WeakReference<>(videos);
    }

    public void storeImages(List<MediaInfo> images) {
        this.images = new WeakReference<>(images);
    }

    public void storeMedias(List<MediaInfo> medias) {
        this.medias = new WeakReference<>(medias);
    }

    public List<MediaInfo> getVideos() {
        if(videos == null) return null;
        List<MediaInfo> values = videos.get();
        for (MediaInfo value : values) {
            value.setSelected(false);
        }

        return values;
    }

    public List<MediaInfo> getImages() {
        if(images == null) return null;
        List<MediaInfo> values = images.get();
        for (MediaInfo value : values) {
            value.setSelected(false);
        }

        return values;
    }

    public List<MediaInfo> getMedias() {
        if(medias == null) return null;
        List<MediaInfo> values = medias.get();
        for (MediaInfo value : values) {
            value.setSelected(false);
        }

        return values;
    }
}
