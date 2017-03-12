package rawe.gordon.com.pick.pick.event;


import java.io.Serializable;
import java.util.List;

import rawe.gordon.com.pick.pick.model.MediaInfo;

/**
 * Created by gordon on 3/6/17.
 */

public class PhotoChooseEvent implements Serializable {

    public static final int TO_LAST_PAGE = 0x0001;
    public static final int TO_RESULT = 0x0002;

    public static final int DONE = 0x0003;
    public static final int CANCEL = 0x0004;

    public int whichToWhich;
    public int intentType;
    public List<MediaInfo> mediaInfos;

    public String uuidIdentifier;

    public PhotoChooseEvent(int whichToWhich, int intentType, List<MediaInfo> mediaInfos, String uuidIdentifier) {
        this.whichToWhich = whichToWhich;
        this.intentType = intentType;
        this.mediaInfos = mediaInfos;
        this.uuidIdentifier = uuidIdentifier;
    }

    public void setUuidIdentifier(String uuidIdentifier) {
        this.uuidIdentifier = uuidIdentifier;
    }

    @Override
    public String toString() {
        return "PhotoChooseEvent{" +
                "whichToWhich=" + whichToWhich +
                ", intentType=" + intentType +
                ", mediaInfos=" + mediaInfos +
                '}';
    }
}
