package rawe.gordon.com.business.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.util.Log;

import com.alibaba.fastjson.JSON;

import java.util.ArrayList;
import java.util.List;

import rawe.gordon.com.business.application.ContextHolder;


public class PreferencesHelper {

    private static final String PREFERENCE_FILE_NAME = "COM.GORDON.RAWE.FRUIT.MARKET";

    private static PreferencesHelper instance;

    private SharedPreferences preference;

    private PreferencesHelper() {
        preference = ContextHolder.getInstance().getContext().getSharedPreferences(PREFERENCE_FILE_NAME, Context.MODE_PRIVATE);
    }

    public static synchronized PreferencesHelper getInstance() {
        return instance == null ? new PreferencesHelper() : instance;
    }

    public boolean getBoolean(String key, boolean value) {
        boolean res =  preference.getBoolean(key, value);
        Log.d("gordon", String.valueOf(res));
        return res;
    }

    public boolean putBoolean(String key, boolean value) {
        Editor editor = preference.edit();
        editor.putBoolean(key, value);
        return editor.commit();
    }

    public boolean putString(String key, String value) {
        Editor editor = preference.edit();
        editor.putString(key, value);
        return editor.commit();
    }

    public String getString(String key) {
        return preference.getString(key, "");
    }

    public boolean putInt(String key, int value) {
        Editor editor = preference.edit();
        editor.putInt(key, value);
        return editor.commit();
    }

    public int getInt(String key, int value) {
        return preference.getInt(key, value);
    }

    public boolean putLong(String key, Long value) {
        Editor editor = preference.edit();
        editor.putLong(key, value);
        return editor.commit();
    }

    public Long getLong(String key, Long value) {
        return preference.getLong(key, value);
    }

    public <T> boolean putList(String key, List<T> data) {
        try {
            putString(key, JSON.toJSONString(data));
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public <T> List<T> getList(String key, Class<T> clazz) {
        List<T> res = new ArrayList<>();
        try {
            String json = preference.getString(key, "");
            return JSON.parseArray(json, clazz);
        } catch (Exception e) {
            return null;
        }
    }
}
