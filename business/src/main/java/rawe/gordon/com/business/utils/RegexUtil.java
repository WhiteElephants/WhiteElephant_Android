package rawe.gordon.com.business.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by gordon on 5/26/16.
 */
public class RegexUtil {
    public static final Pattern VALID_EMAIL_ADDRESS_REGEX =
            Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);

    public static boolean validateEmail(String emailStr) {
        Matcher matcher = VALID_EMAIL_ADDRESS_REGEX.matcher(emailStr);
        return matcher.find();
    }

    public static boolean validate(String pattern,String srcString) {
        Matcher matcher = Pattern.compile(pattern,Pattern.CASE_INSENSITIVE).matcher(srcString);
        return matcher.find();
    }
}
