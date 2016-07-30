package rawe.gordon.com.business.utils;


/**
 * Created by gordon on 16/5/22.
 */
public class NullTransformer {
    public static String transform(String src) {
        return src == null ? "" : src;
    }
}