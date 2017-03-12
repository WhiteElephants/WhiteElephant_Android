package rawe.gordon.com.pick.photobrowse;


import java.util.List;

import rawe.gordon.com.pick.model.MediaInfo;

/**
 * Created by gordon on 3/7/17.
 */

public class PhotoBrowseModel {
    List<MediaInfo> mediaInfos;
    int startIndex;
    int maxLimit;
    List<MediaInfo> otherSelected;
    String uuidIdentifier;


    public PhotoBrowseModel(List<MediaInfo> mediaInfos, int startIndex, int maxLimit, List<MediaInfo> otherSelected, String uuidIdentifier) {
        this.mediaInfos = mediaInfos;
        this.startIndex = startIndex;
        this.maxLimit = maxLimit;
        this.otherSelected = otherSelected;
        this.uuidIdentifier = uuidIdentifier;
    }

    public static PhotoBrowseModel buildParams(List<MediaInfo> mediaInfos, int startIndex, int maxLimit, List<MediaInfo> otherSelected, String uuidIdentifier) {
        return new PhotoBrowseModel(mediaInfos, startIndex, maxLimit, otherSelected, uuidIdentifier);
    }
}
