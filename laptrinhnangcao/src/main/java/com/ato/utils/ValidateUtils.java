package com.ato.utils;

import org.apache.commons.lang3.StringUtils;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ValidateUtils {

    /**
     * private contructor
     */
    private ValidateUtils() {
    }

    /**
     * <P>Check is Integer or not</P>
     *
     * @param str String to check
     * @param str
     * @return @boolean true if valid, false if not valid
     */
    public static boolean isInteger(String str) {
        if (str == null || !str.matches("[0-9]+$")) {
            return false;
        }
        return true;
    }

    public static String validateKeySearch(String str){
        return str.replaceAll("&", "&&").replaceAll("%", "&%").replaceAll("_", "&_");
    }

    public static boolean getSpecialCharacterCount(String s) {
        if (s == null || s.trim().isEmpty()) {
            return false;
        }
        Pattern p = Pattern.compile("[^A-Za-z0-9_]");
        Matcher m = p.matcher(s.replaceAll(" ",""));
        // boolean b = m.matches();
        boolean b = m.find();
        if (b == true)
            return false;
        else
            return true;
    }

    public static boolean getSpecialCharacterCountName(String s) {
        if (s == null || s.trim().isEmpty()) {
            return false;
        }
        Pattern p = Pattern.compile("[^A-Za-z0-9Ạ-ỹáàạảãâấầậẩẫăắằặẳẵÁÀẠẢÃÂẤẦẬẨẪĂẮẰẶẲẴéèẹẻẽêếềệểễÉÈẸẺẼÊẾỀỆỂỄóòọỏõôốồộổỗơớờợởỡÓÒỌỎÕÔỐỒỘỔỖƠỚỜỢỞỠúùụủũưứừựửữÚÙỤỦŨƯỨỪỰỬỮíìịỉĩÍÌỊỈĨđĐýỳỵỷỹÝỲỴỶỸ]");
        Matcher m = p.matcher(s.replaceAll(" ",""));
        // boolean b = m.matches();
        boolean b = m.find();
        if (b == true)
            return false;
        else
            return true;
    }
    public static boolean isStringInt(String s)
    {
        try
        {
            int a=Integer.parseInt(s);
            if(a>0) {
                return true;
            } else {
                return false;
            }
        } catch (NumberFormatException ex) {
            return false;
        }
    }

    public static boolean isEmpty(String obj) {
        return StringUtils.isEmpty(obj);
    }

    public static boolean checkLength(String obj, int min, int max) {
        return obj.length() > min && obj.length() < max;
    }

    public static boolean checkLengthMax(String obj, int min) {
        return obj.length() > min;
    }

    public static boolean checkResetPass(String oldPass, String newPass) {
        return oldPass.equals(newPass);
    }

    public static Boolean changePassword(String encryptedPassword, String unencryptedPassword) {
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        CharSequence s = unencryptedPassword;
        return passwordEncoder.matches(s, encryptedPassword);
    }

    public static boolean checkFormatEmail(String email) {
        return email.matches("^[a-z][a-z0-9_\\.]{5,32}@[a-z0-9]{2,}(\\.[a-z0-9]{2,4}){1,2}$");
    }
}
