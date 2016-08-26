package com.iknow.imageselect.fragments.provider;

import android.database.Cursor;
import android.provider.MediaStore;

import com.iknow.imageselect.fragments.models.ImageMediaEntry;

import java.util.ArrayList;
import java.util.List;

import rawe.gordon.com.business.application.ContextHolder;

/**
 * Created by gordon on 8/26/16.
 */
public class SourceProvider {

    public static List<ImageMediaEntry> getAllImages() {
        ImageMediaEntry info;
        List<ImageMediaEntry> retValues = new ArrayList<>();
        try {
            String sortOrder = MediaStore.Images.Media.DATE_MODIFIED + " desc";
            Cursor cursor = ContextHolder.getInstance().getContext().getContentResolver().query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, null, null, null, sortOrder);
            assert cursor != null;
            while (cursor.moveToNext()) {
                info = new ImageMediaEntry();
                info.setStoragePath(cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA)));
                info.setDisplayName(cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DISPLAY_NAME)));
                info.setCreateTime(cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATE_ADDED)));
                info.setLat(cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.LATITUDE)));
                info.setLon(cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.LONGITUDE)));
                retValues.add(info);
            }
            cursor.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return retValues;
    }
}
