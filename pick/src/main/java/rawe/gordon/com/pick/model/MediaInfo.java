package rawe.gordon.com.pick.model;

import android.graphics.BitmapFactory;
import android.text.TextUtils;


import java.io.Serializable;

import rawe.gordon.com.pick.manage.PhotoPictureManager;
import rawe.gordon.com.pick.utils.BitmapUtil;

/**
 * Created by gordon on 3/6/17.
 */

public class MediaInfo implements Serializable {

    public static final int VIDEO = 0;
    public static final int PICTURE = 1;

    public static final String LOCAL_PROTOCAL = "file://";

    public int mediaType = PICTURE;

    /**
     * 图片显示的名称
     */
    public String name = "";

    /**
     * 作者名称
     */
    public String author = "";

    /**
     * 图片描述信息
     */
    public String description = "";

    /**
     * 图片所在文件夹的路径
     */
    public String path;

    /**
     * 创建时间
     */
    public String createTime = "";

    /**
     * 调优图路径
     */
    public String scaledPath;

    /**
     * 缩略图路径
     */
    public String thumbPath;

    /**
     * 媒体的经度
     */
    public String lat;

    /**
     * 媒体的纬度
     */
    public String lon;

    /**
     * 辅助变量，写在这里不是很好，但是不用的时候可以忽略
     */
    public boolean isSelected = false;

    /**
     * 原图
     */
    public boolean isOriginal;

    /**
     * 如果是视频的时间长度
     */
    public long duration;

    /**
     * 文件夹的绝对路径
     */
    public String absoluteFolder = "";

    /**
     * 文件夹名字
     */
    public String folderName = "";

    /**
     * 原图宽度
     */
    public int width;

    /**
     * 原图宽度
     */
    public int height;

    public void decodePicture() {
        int[] size = BitmapUtil.decodeSize(path);
        this.width = size[0];
        this.height = size[1];
    }

    public void setMediaType(int mediaType) {
        this.mediaType = mediaType;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setPath(String path) {
        this.path = path;
        if (path == null || !path.contains("/")) return;
        absoluteFolder = path.substring(0, path.lastIndexOf('/'));
        folderName = absoluteFolder.substring(absoluteFolder.lastIndexOf('/') + 1);
        decodePicture();
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public void setScaledPath(String scaledPath) {
        this.scaledPath = scaledPath;
    }

    public void setThumbPath(String thumbPath) {
        this.thumbPath = thumbPath;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public void setLon(String lon) {
        this.lon = lon;
    }

    public void setSelected(boolean selected, String sessionId) {
        isSelected = selected;
        if (isSelected) {
            PhotoPictureManager.getInstance().addPickRecord(sessionId, this);
        } else {
            PhotoPictureManager.getInstance().removePickRecord(sessionId, this);
        }
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    public void setOriginal(boolean original) {
        isOriginal = original;
    }

    public void setDuration(long duration) {
        this.duration = duration;
    }

    public String getProtocaledPath() {
        return LOCAL_PROTOCAL + path;
    }

    @Override
    public String toString() {
        return "MediaInfo{" +
                "mediaType=" + mediaType +
                ", name='" + name + '\'' +
                ", author='" + author + '\'' +
                ", description='" + description + '\'' +
                ", path='" + path + '\'' +
                ", createTime='" + createTime + '\'' +
                ", thumbPath='" + thumbPath + '\'' +
                ", lat='" + lat + '\'' +
                ", lon='" + lon + '\'' +
                ", isSelected=" + isSelected +
                ", isOriginal=" + isOriginal +
                ", duration=" + duration +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        return o instanceof MediaInfo && TextUtils.equals(this.path, ((MediaInfo) o).path);
    }

    public void generateMiddleAndThumbnails() {
        if (mediaType == PICTURE) {
            try {
                setScaledPath(BitmapUtil.generateScaledPicture(path));
                setThumbPath(BitmapUtil.generateThumbPicture(scaledPath));
            } catch (Exception e) {
                e.printStackTrace();
                setThumbPath(path);
                setScaledPath(path);
            }
        } else if (mediaType == VIDEO) {
            try {
                setScaledPath(BitmapUtil.generateScaledVideoPicture(path));
                BitmapFactory.Options options = new BitmapFactory.Options();
                options.inJustDecodeBounds = true;
                BitmapFactory.decodeFile(scaledPath, options);
                this.width = options.outWidth;
                this.height = options.outHeight;
                setThumbPath(BitmapUtil.generateThumbPicture(scaledPath));
            } catch (Exception e) {
                e.printStackTrace();
                setScaledPath("");
                setThumbPath("");
            }
        }
    }
}
