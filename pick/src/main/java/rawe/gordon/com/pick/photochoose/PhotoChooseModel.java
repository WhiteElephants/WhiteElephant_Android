package rawe.gordon.com.pick.photochoose;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import rawe.gordon.com.pick.R;
import rawe.gordon.com.pick.manage.PhotoPictureManager;
import rawe.gordon.com.pick.model.MediaInfo;
import rawe.gordon.com.pick.utils.PhotoFilter;
import rawe.gordon.com.pick.utils.StringUtil;

/**
 * Created by gordon on 8/26/16.
 */
public class PhotoChooseModel {

    public String ALL_TAG;
    public String VIDEO_TAG;

    public String title;
    public PhotoChooseScenario.Criterion criterion;
    public List<MediaInfo> originalData;
    public boolean showFilter = false;
    public Map<String, List<MediaInfo>> categorizedData;
    public String workingTag = ALL_TAG;

    public List<MediaInfo> getWorkingGroup() {
        return categorizedData.get(workingTag);
    }

    public List<MediaInfo> getDiff() {

        /**
         * 拿到已经选择好的结果和现在的工作组进行排除
         * */
        List<MediaInfo> allSelected = new ArrayList<>(PhotoPictureManager.getInstance().getRecords(criterion.uuidIdentifier));
        List<MediaInfo> workingSelected = new ArrayList<>(PhotoFilter.filterSelected(getWorkingGroup()));
        allSelected.removeAll(workingSelected);
        return allSelected;
    }

    private void buildCategorizedData() {
        categorizedData = new LinkedHashMap<>();
        categorizedData.put(ALL_TAG, new ArrayList<>(originalData));
        if (criterion.allowMix)
            categorizedData.put(VIDEO_TAG, new ArrayList<>(PhotoFilter.filterVideos(originalData)));
        for (MediaInfo mediaInfo : originalData) {
            List<MediaInfo> tmpGroup = categorizedData.get(mediaInfo.folderName);
            if (tmpGroup == null) {
                tmpGroup = new ArrayList<>();
                categorizedData.put(mediaInfo.folderName, tmpGroup);
            }
            tmpGroup.add(mediaInfo);
        }
    }

    private void refreshTagAndTitle() {
        if (criterion.allowMix) {
            VIDEO_TAG = StringUtil.getStringBySourceId(R.string.chat_im_all_videos);
            ALL_TAG = StringUtil.getStringBySourceId(R.string.chat_im_all_mix);
            title = StringUtil.getStringBySourceId(R.string.chat_im_all_mix);
        } else if (criterion.mediaType == MediaInfo.VIDEO) {
            ALL_TAG = StringUtil.getStringBySourceId(R.string.chat_im_all_videos);
            title = StringUtil.getStringBySourceId(R.string.chat_im_all_videos);
            VIDEO_TAG = StringUtil.getStringBySourceId(R.string.chat_im_all_videos);
        } else {
            ALL_TAG = StringUtil.getStringBySourceId(R.string.chat_im_all_images);
            title = StringUtil.getStringBySourceId(R.string.chat_im_all_images);
            VIDEO_TAG = StringUtil.getStringBySourceId(R.string.chat_im_all_videos);
        }
        workingTag = ALL_TAG;
    }

    public PhotoChooseModel(PhotoChooseScenario.Criterion criterion, List<MediaInfo> mediaInfos) {
        this.criterion = criterion;
        this.originalData = mediaInfos;
        refreshTagAndTitle();
        buildCategorizedData();

    }

    public PhotoChooseModel(PhotoChooseScenario.Criterion criterion) {
        this.criterion = criterion;
        refreshTagAndTitle();
    }

    public void passMedias(List<MediaInfo> mediaInfos) {
        this.originalData = mediaInfos;
        buildCategorizedData();
    }

    static String[] projection = {
            MediaStore.Files.FileColumns._ID,
            MediaStore.Files.FileColumns.DATA,
            MediaStore.Files.FileColumns.DATE_ADDED,
            MediaStore.Files.FileColumns.MEDIA_TYPE,
            MediaStore.Files.FileColumns.MIME_TYPE,
            MediaStore.Files.FileColumns.TITLE
    };

    static String selection = MediaStore.Files.FileColumns.MEDIA_TYPE + "="
            + MediaStore.Files.FileColumns.MEDIA_TYPE_IMAGE
            + " OR "
            + MediaStore.Files.FileColumns.MEDIA_TYPE + "="
            + MediaStore.Files.FileColumns.MEDIA_TYPE_VIDEO;

    public static List<MediaInfo> getAllImages(Context context) {
        MediaInfo info;
        List<MediaInfo> retValues = new ArrayList<>();
        try {
            String sortOrder = MediaStore.Images.Media.DATE_MODIFIED + " desc";
            Cursor cursor = context.getContentResolver().query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, null, null, null, sortOrder);
            assert cursor != null;
            while (cursor.moveToNext()) {
                info = new MediaInfo();
                info.setMediaType(MediaInfo.PICTURE);
                info.setPath(cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA)));
                info.setName(cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DISPLAY_NAME)));
                info.setCreateTime(cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATE_ADDED)));
                info.setLat(cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.LATITUDE)));
                info.setLon(cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.LONGITUDE)));
                info.setDescription(cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Video.Media.DESCRIPTION)));
                retValues.add(info);
            }
            cursor.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        Collections.sort(retValues, new Comparator<MediaInfo>() {
            @Override
            public int compare(MediaInfo one, MediaInfo another) {
                return another.createTime.compareTo(one.createTime);
            }
        });
        return retValues;
    }


    public static List<MediaInfo> getAllVideoFiles(Context mContext) {
        MediaInfo videoInfo;
        List<MediaInfo> videos = new ArrayList<>();
        ContentResolver contentResolver = mContext.getContentResolver();

        try {
            Cursor cursor = contentResolver.query(MediaStore.Video.Media.EXTERNAL_CONTENT_URI, null,
                    null, null, MediaStore.Video.Media.DEFAULT_SORT_ORDER);
            assert cursor != null;
            while (cursor.moveToNext()) {
                videoInfo = new MediaInfo();
                videoInfo.setMediaType(MediaInfo.VIDEO);
                videoInfo.setPath(cursor.getString(cursor.getColumnIndex(MediaStore.Video.Media.DATA)));
                videoInfo.setName(cursor.getString(cursor.getColumnIndex(MediaStore.Video.Media.DISPLAY_NAME)));
                videoInfo.setCreateTime(cursor.getString(cursor.getColumnIndex(MediaStore.Video.Media.DATE_ADDED)));
                videoInfo.setDuration(cursor.getLong(cursor.getColumnIndexOrThrow(MediaStore.Video.Media.DURATION)));
                videoInfo.setLat(cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Video.Media.LATITUDE)));
                videoInfo.setLon(cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Video.Media.LONGITUDE)));
                videoInfo.setDescription(cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Video.Media.DESCRIPTION)));
                videos.add(videoInfo);
            }
            cursor.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        Collections.sort(videos, new Comparator<MediaInfo>() {
            @Override
            public int compare(MediaInfo one, MediaInfo another) {
                return another.createTime.compareTo(one.createTime);
            }
        });
        return videos;
    }

    /**
     * 这种方法使用细节查询的办法，查询两次
     */
    public static List<MediaInfo> getAllMediaFilesWithDetail(Context mContext) {
        List<MediaInfo> images = getAllImages(mContext);
        List<MediaInfo> videos = getAllVideoFiles(mContext);
        images.addAll(videos);
        Collections.sort(images, new Comparator<MediaInfo>() {
            @Override
            public int compare(MediaInfo one, MediaInfo another) {
                return another.createTime.compareTo(one.createTime);
            }
        });
        return images;
    }

    /**
     * 这种方法使用文件查询的办法，但是缺点在与查询不到文件更具体的信息
     */
    public static List<MediaInfo> getAllMediaFilesIgnoreDetail(Context mContext) {
        MediaInfo mediaInfo;
        List<MediaInfo> allMediasList = new ArrayList<>();
        ContentResolver contentResolver = mContext.getContentResolver();
        Uri queryUri = MediaStore.Files.getContentUri("external");
        try {
            Cursor cursor = contentResolver.query(queryUri, projection, selection,
                    null, MediaStore.Files.FileColumns.DATE_ADDED + " DESC");
            assert cursor != null;
            while (cursor.moveToNext()) {
                mediaInfo = new MediaInfo();
                mediaInfo.setMediaType(cursor.getInt(cursor.getColumnIndex(MediaStore.Files.FileColumns.MEDIA_TYPE)) == 3 ? MediaInfo.VIDEO : MediaInfo.PICTURE);
                mediaInfo.setPath(cursor.getString(cursor.getColumnIndex(MediaStore.Files.FileColumns.DATA)));
                mediaInfo.setCreateTime(cursor.getString(cursor.getColumnIndex(MediaStore.Files.FileColumns.DATE_ADDED)));
                mediaInfo.setName(cursor.getString(cursor.getColumnIndex(MediaStore.Files.FileColumns.TITLE)));

                /**
                 mediaInfo.setLat(cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Images.Media.LATITUDE)));
                 mediaInfo.setLon(cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Images.Media.LONGITUDE)));
                 mediaInfo.setDescription(cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Video.Media.DESCRIPTION)));
                 mediaInfo.setDuration(cursor.getLong(cursor.getColumnIndexOrThrow(MediaStore.Video.Media.DURATION)));
                 */
                allMediasList.add(mediaInfo);
            }
            cursor.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return allMediasList;
    }
}
