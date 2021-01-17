package cn.mikylin.litjson.utils;

import cn.mikylin.litjson.exception.JSONCharException;

public class Asserts {

    public static void charEqualsAssert(char a,char b,String message) {
        if(a != b) throw new JSONCharException(message);
    }
}
