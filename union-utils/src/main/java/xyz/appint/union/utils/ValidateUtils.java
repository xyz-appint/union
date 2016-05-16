package xyz.appint.union.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Justin on 2014/11/18.
 */
public class ValidateUtils {
    public static boolean isMobiPhoneNum(String telNum) {
        if (StringUtils.hasText(telNum) == false) {
            return false;
        }
        String regex = "^((13[0-9])|(15[0-9])|(18[0-9])|(17[0-9]))\\d{8}$";
        Pattern p = Pattern.compile(regex, Pattern.CASE_INSENSITIVE);
        Matcher m = p.matcher(telNum);
        return m.matches();
    }

    public static boolean validateEmail(String email) {
        if (StringUtils.hasText(email) == false) {
            return false;
        }
        String regex =
                "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                        + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
        Pattern pattern = Pattern.compile(regex, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }
}
