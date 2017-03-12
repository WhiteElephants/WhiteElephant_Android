package rawe.gordon.com.pick.manage;


import java.util.List;
import java.util.UUID;

import rawe.gordon.com.pick.model.MediaInfo;

/**
 * Created by gordon on 3/9/17.
 */

public abstract class PhotoChooseListener {
    public String uuidIdentifier;

    public PhotoChooseListener() {
        uuidIdentifier = UUID.randomUUID().toString();
    }

    public void getResult(List<MediaInfo> result){

    }
}
