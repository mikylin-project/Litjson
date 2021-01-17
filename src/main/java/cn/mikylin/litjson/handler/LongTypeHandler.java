package cn.mikylin.litjson.handler;

import cn.mikylin.litjson.buffer.BufAppendable;

public class LongTypeHandler implements TypeHandler<Long> {

    @Override
    public Class[] getTypes() {
        return new Class[] {Long.class,long.class};
    }

    @Override
    public Long read(String value) {
        return Long.valueOf(value);
    }

    @Override
    public void append(Object value, BufAppendable append) {
        append.append(Long.toString((Long)value));
    }
}
