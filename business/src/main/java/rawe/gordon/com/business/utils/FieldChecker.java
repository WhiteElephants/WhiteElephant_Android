package rawe.gordon.com.business.utils;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by gordon on 16/5/28.
 */
public class FieldChecker {

    public static void replaceNullFieldsWithTarget(Class<?> clazz, Object t, String target) throws Exception {
        Field[] fields = clazz.getDeclaredFields();
        for (Field field : fields) {
            if (field.getType() == String.class) {
                if (field.get(t) == null) field.set(t, target);
            } else if (field.getType() == List.class) {
                List<Object> list = (List<Object>) field.get(t);
                if (list != null && list.size() > 0) {
                    if (list.get(0).getClass() == String.class) {
                        List<String> newList = new ArrayList<>();
                        for (Object obj : list) {
                            newList.add(obj == null ? "" : obj.toString());
                        }
                        field.set(t, newList);
                    } else {
                        for (Object obj : list) {
                            replaceNullFieldsWithTarget(obj.getClass(), obj, target);
                        }
                    }
                }

            } else if (!field.getType().isPrimitive()) {
                Object object = field.get(t);
                replaceNullFieldsWithTarget(field.getType(), object, target);
            }
        }
    }

    public static void inspectAndModify(Class<?> clazz, Object t, String src, String target) throws Exception {
        if (src == null) throw new Exception("source string can not be null");
        Field[] fields = clazz.getDeclaredFields();
        for (Field field : fields) {
            if (field.getType() == String.class) {
                if (RegexUtil.validate(src, field.get(t).toString())) field.set(t, target);
            } else if (field.getType() == List.class) {
                List<Object> list = (List<Object>) field.get(t);
                if (list != null && list.size() > 0) {
                    if (list.get(0).getClass() == String.class) {
                        List<String> newList = new ArrayList<>();
                        for (Object obj : list) {
                            newList.add(RegexUtil.validate(src, obj.toString()) ? target : obj.toString());
                        }
                        field.set(t, newList);
                    } else {
                        for (Object obj : list) {
                            inspectAndModify(obj.getClass(), obj, src, target);
                        }
                    }
                }

            } else if (!field.getType().isPrimitive()) {
                Object object = field.get(t);
                inspectAndModify(field.getType(), object, src, target);
            }
        }
    }
}
