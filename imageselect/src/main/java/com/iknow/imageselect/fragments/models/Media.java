package com.iknow.imageselect.fragments.models;

import android.graphics.Bitmap;
import android.media.ThumbnailUtils;

/**
 * Created by gordon on 16/8/26.
 */
public abstract class Media {
    protected Bitmap thumbnail;
    protected String storagePath;

    public Bitmap getThumbnail() {
//        return ThumbnailUtils.extractThumbnail(storagePath,0);
        return null;
    }

    public void setThumbnail(Bitmap thumbnail) {
        this.thumbnail = thumbnail;
    }

    public String getStoragePath() {
        return storagePath;
    }

    public void setStoragePath(String storagePath) {
        this.storagePath = storagePath;
    }
}
