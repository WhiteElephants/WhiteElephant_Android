package rawe.gordon.com.business.utils;

import android.text.TextUtils;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by gordon on 9/5/16.
 */
public class RequestUtil {
    public static <T> Map<String, Object> createRequestMapFromBean(T bean) {
        try {
            Class<? extends Object> class1 = bean.getClass();
            Field[] fields = class1.getFields();
            Map<String, Object> map = new HashMap<>();
            for (Field field : fields) {
                Object value = field.get(bean);
                Class<?> class_ = field.getType();
                if (value == null)
                    continue;
                else if (class_ == List.class) {
                    List<T> list = (List<T>) value;
                    if (list.size() == 0)
                        continue;
                } else if (value.getClass() == Long.class) {
                    Long longValue = (Long) value;
                    if (longValue == 0)
                        continue;
                } else if (value.getClass() == Double.class) {
                    Double doubleValue = (Double) value;
                    if (doubleValue == 0d)
                        continue;
                } else if (value.getClass() == String.class) {
                    String string = (String) value;
                    if (TextUtils.isEmpty(string))
                        continue;
                }
                map.put(field.getName(), value);
            }
            return map;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
