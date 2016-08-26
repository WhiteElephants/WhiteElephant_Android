package com.iknow.imageselect.fragments.models;

import android.graphics.Bitmap;
import android.media.ThumbnailUtils;
import android.provider.MediaStore;

/**
 * Created by gordon on 16/8/26.
 */
public abstract class MediaEntry {
    public static final int TYPE_IMAGE = 0;
    public static final int TYPE_VIDEO = 1;

    protected String storagePath;

    public String displayName;

    public String createTime;

    public int rotate;

    public String lat;

    public String lon;

    public Bitmap getThumbnail() {
        return ThumbnailUtils.createVideoThumbnail(storagePath, MediaStore.Images.Thumbnails.MICRO_KIND);
    }

    public int getRotate() {
        return rotate;
    }

    public String getStoragePath() {
        return storagePath;
    }

    public String getProtocaledPath() {
        return "file://" + storagePath;
    }

    public void setStoragePath(String storagePath) {
        this.storagePath = storagePath;
    }

    protected int getMediaType() {
        return TYPE_IMAGE;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getLon() {
        return lon;
    }

    public void setLon(String lon) {
        this.lon = lon;
    }
}
