package rawe.gordon.com.business.utils;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.OvershootInterpolator;
import android.widget.Toast;

import com.gordon.rawe.business.models.CartOrder;
import com.gordon.rawe.business.models.User;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import rawe.gordon.com.business.application.ContextHolder;
import rawe.gordon.com.business.application.SharedParameter;
import rawe.gordon.com.business.db.DBManager;
import rawe.gordon.com.business.network.responses.pojo.CommodityModel;

public class CacheBean {

	private static Map<String, CacheBean> cacheBeanMap = new HashMap<String, CacheBean>();

	private static Map<String, Map<String, Object>> paramMap = new HashMap<String, Map<String, Object>>();

	public static void putParam(String token, String key, Object value) {
		Map<String, Object> params = paramMap.get(token);
		if (params == null) {
			params = new HashMap<String, Object>();
		}
		params.put(key, value);
		paramMap.put(token, params);
	}

	public static Object getParam(String token, String key) {
		Map<String, Object> params = paramMap.get(token);
		if (params == null) {
			return null;
		}
		return params.get(key);
	}

	public static void clean(String token) {
		cacheBeanMap.remove(token);
		paramMap.remove(token);
	}

	public static CacheBean get(String token) {
		CacheBean commonCacheBean = cacheBeanMap.get(token);
		if (commonCacheBean == null) {
			commonCacheBean = new CacheBean();
			cacheBeanMap.put(token, commonCacheBean);
		}
		return commonCacheBean;
	}















}
