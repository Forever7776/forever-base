package util;

import org.apache.commons.lang3.StringUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * @author kz
 */
public class ValidateUtil {

    private static final Pattern MOBILE_PATTERN = Pattern.compile("^((13[0-9])|(147)|(15[0-3])|(15[5-9])|(17[0-3])|(17[5-8])|(186)|(18[0-9]))\\d{8}$");
    private static final Pattern EMAIL_PATTERN = Pattern.compile("^[\\w!#$%&'*+/=?^_`{|}~-]+(?:\\.[\\w!#$%&'*+/=?^_`{|}~-]+)*@(?:[\\w](?:[\\w-]*[\\w])?\\.)+[\\w](?:[\\w-]*[\\w])?$");

    /**
     * 校验手机号码是否正确
     *
     * @param mobile
     * @return
     */
    public static boolean verifyMobileIsPassed(String mobile) {
        if(StringUtils.isBlank(mobile)){
            return false;
        }
        Matcher m = MOBILE_PATTERN.matcher(mobile);
        return m.matches();
    }

    /**
     * 校验邮箱号是否正确
     *
     * @param email
     * @return
     */
    public static boolean verifyEmailIsPassed(String email) {
        if(StringUtils.isBlank(email)){
            return false;
        }
        Matcher m = EMAIL_PATTERN.matcher(email);
        return m.matches();
    }

    public static void main(String[] args) {
        System.out.println(ValidateUtil.verifyMobileIsPassed("18680392089\u202D18680392089\u202C"));
    }
}
