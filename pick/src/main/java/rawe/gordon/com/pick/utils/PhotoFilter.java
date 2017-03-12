package rawe.gordon.com.pick.utils;


import java.util.ArrayList;
import java.util.List;

import rawe.gordon.com.pick.model.MediaInfo;

/**
 * Created by gordon on 3/6/17.
 */

public class PhotoFilter {
    private PhotoFilter() {
    }

    public static List<MediaInfo> filterSelected(List<MediaInfo> src) {
        List<MediaInfo> retValues = new ArrayList<>();
        for (MediaInfo entry : src) {
            if (entry.isSelected) retValues.add(entry);
        }
        return retValues;
    }

    public static List<MediaInfo> filterVideos(List<MediaInfo> src) {
        List<MediaInfo> retValues = new ArrayList<>();
        for (MediaInfo entry : src) {
            if (entry.mediaType == MediaInfo.VIDEO) retValues.add(entry);
        }
        return retValues;
    }
}
