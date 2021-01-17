package cn.mikylin.litjson.handler;

import cn.mikylin.litjson.buffer.BufAppendable;

public class ShortTypeHandler implements TypeHandler<Short> {

    @Override
    public Class[] getTypes() {
        return new Class[] {Short.class,short.class};
    }

    @Override
    public Short read(String value) {
        return Short.valueOf(value);
    }

    @Override
    public void append(Object t, BufAppendable append) {
        writeString(t.toString(),append);
    }
}
