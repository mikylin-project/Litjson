package cn.mikylin.litjson.handler;

import cn.mikylin.litjson.buffer.BufAppendable;

public class VoidTypeHandler implements TypeHandler<Void> {

    private static final String NULL = "null";

    @Override
    public Class[] getTypes() {
        return new Class[]{Void.class};
    }

    @Override
    public Void read(String value) {
        return null;
    }

    @Override
    public void append(Object t, BufAppendable append) {
        append.append(NULL);
    }
}
