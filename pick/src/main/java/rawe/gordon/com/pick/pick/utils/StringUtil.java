package rawe.gordon.com.pick.pick.utils;

import rawe.gordon.com.business.application.ContextHolder;

/**
 * Created by gordon on 3/8/17.
 */

public class StringUtil {
    private StringUtil() {
    }

    public static String firstCamel(String src) {
        if (src == null || src.equals("")) return src;
        return src.substring(0, 1).toUpperCase() + src.substring(1);
    }

    public static String getStringBySourceId(int id){
        return ContextHolder.getInstance().getContext().getResources().getString(id);
    }
}
