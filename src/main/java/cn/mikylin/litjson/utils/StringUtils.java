package cn.mikylin.litjson.utils;

public class StringUtils {

    /**
     * check the string is blank.
     *
     * @param str string
     * @return blank - true , not blank - false
     */
    public static boolean isBlank(String str) {
        return str == null || str.isBlank();
    }

    public static boolean isNotBlank(String str) {
        return !isBlank(str);
    }
}
