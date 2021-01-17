package cn.mikylin.litjson.buffer;

import cn.mikylin.litjson.utils.Constants;

public final class CharBuffer implements CharBuf {

    private final char[] data;
    private final int begin;
    private final int end;
    private int index;

    private String value = null;

    CharBuffer(char[] data,int begin,int end) {
        if (data == null || data.length == 0)
            throw new IllegalArgumentException("string can not be none.");
        if (begin > end)
            throw new IllegalArgumentException("begin should small than end.");
        this.data = data;
        this.begin = begin >= 0 ? begin : 0;
        this.end = end >= 0 ? end : data.length - 1;
        this.index = this.begin;
    }

    protected CharBuffer() {
        this.data = null;
        this.begin = -1;
        this.end = -1;
    }

    @Override
    public boolean offset(int offset) {
        int newIndex = index + offset;
        if (newIndex < begin || newIndex > end + 1)
            return false;
        index = newIndex;
        return true;
    }

    @Override
    public int index() {
        return index;
    }

    @Override
    public char[] data() {
        return data;
    }

    @Override
    public char read() {
        return canRead() ? data[index] : Constants.NON_CHAR;
    }

    @Override
    public boolean canRead() {
        return index <= end;
    }

    @Override
    public String toString() {

        if (value == null) {
            if (CharBuf.isNon(this))
                return "";

            StringBuilder builder = new StringBuilder();

            for (int i = begin ; i <= end ; i ++)
                builder.append(data[i]);

            value = builder.toString();
        }

        return value;
    }

}