package cn.mikylin.litjson.buffer;

import cn.mikylin.litjson.exception.JSONCharException;
import cn.mikylin.litjson.utils.BlankCharUtils;
import cn.mikylin.litjson.utils.Constants;

import java.util.Arrays;
import java.util.Objects;

public interface CharBuf {

    /**
     * create char buf.
     */
    static CharBuf createDefault(String str) {
        char[] data = str.toCharArray();
        return createDefault(data,0,data.length - 1);
    }

    static CharBuf createDefault(char[] data,int begin,int end) {
        CharBuffer buffer = new CharBuffer(data, begin, end);
        return buffer;
    }

    /**
     * set offset.
     */
    boolean offset(int offset);

    /**
     * the index of char buf.
     */
    int index();

    /**
     * chars.
     */
    char[] data();

    /**
     * read this index.
     */
    char read();

    /**
     * can read ?
     */
    boolean canRead();

    default CharBuf readTil(boolean isInCludeTil,char... chars) {
        if (chars == null || chars.length == 0)
            throw new IllegalArgumentException("til char can not be non.");

        int oldIndex = index();

        while (canRead()) {
            char c = read();
            for (char ch : chars) {
                if (ch == c) {
                    int thisIndex = index();
                    int endIndex = isInCludeTil ? thisIndex : thisIndex - 1;
                    CharBuf newBuf = createDefault(data(),oldIndex,endIndex);
                    return newBuf;
                }
            }
            next();
        }

        throw new JSONCharException("json exception, can not read til "
                + Arrays.toString(chars) + ".");
    }

    default CharBuf readString() {

        if (read() != '"')
            throw new JSONCharException("json exception, should start with '\"'");

        next();

        int oldIndex = index();

        while (canRead()) {
            char c = read();
            if (c == '"') {
                CharBuf newBuf = createDefault(data(),oldIndex,index() - 1);
                return newBuf;
            }
            next();
        }

        throw new JSONCharException("json exception, can not read til '\"'.");
    }

    default CharBuf readObject() {

        if (read() != '{')
            throw new JSONCharException("json exception, should start with '{'");

        int oldIndex = index();

        int stackNum = 0;

        while (canRead()) {
            char c = read();
            if (c == '{') {
                stackNum ++;
            } else if (c == '}') {
                if ( -- stackNum == 0) {
                    CharBuf newBuf = createDefault(data(),oldIndex,index());
                    return newBuf;
                }
            } else if (c == '"') {
                readString();
            }
            next();
        }

        throw new JSONCharException("json exception, can not read til '}'.");
    }

    default CharBuf readArray() {
        if (read() != '[')
            throw new JSONCharException("json exception, should start with '['");

        int oldIndex = index();

        int stackNum = 0;

        while (canRead()) {
            char c = read();
            if (c == '[') {
                stackNum ++;
            } else if (c == ']') {
                if ( -- stackNum == 0) {
                    CharBuf newBuf = createDefault(data(),oldIndex,index());
                    return newBuf;
                }
            } else if (c == '"') {
                readString();
            }
            next();
        }

        throw new JSONCharException("json exception, can not read til ']'.");
    }

    default boolean next() {
        return offset(1);
    }

    default char readTileNotBlank() {

        while (canRead()) {
            char c = read();
            if (BlankCharUtils.isNotBlank(c))
                return c;
            next();
        }
        return Constants.NON_CHAR;
    }

    default boolean equals(String str) {
        return Objects.equals(toString(),str);
    }

    CharBuf NON = new CharBuffer();

    static boolean isNon(CharBuf c) {
        return c == NON;
    }
}
