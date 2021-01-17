package cn.mikylin.litjson.utils;

public class BlankCharUtils {

    private static char[] blanks = new char[] {
            ' ',
            '\n'
    };


    public static boolean isBlank(char c) {
        for (char a : blanks) {
            if (a == c)
                return true;
        }
        return false;
    }

    public static boolean isNotBlank(char c) {
        return !isBlank(c);
    }
}